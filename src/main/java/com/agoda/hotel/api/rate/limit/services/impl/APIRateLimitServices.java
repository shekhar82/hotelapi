package com.agoda.hotel.api.rate.limit.services.impl;

import com.agoda.hotel.api.rate.limit.services.IAPIRateLimitServices;
import com.agoda.hotel.api.rate.limit.strategies.TokenReissueStrategy;
import com.agoda.hotel.api.rate.limit.strategies.impl.FixedRateTokenReissueStrategy;
import com.agoda.hotel.api.rate.limit.tokens.RequestTokenBucket;
import com.agoda.hotel.api.rate.limit.tokens.impl.RequestTokenBucketImpl;
import com.agoda.hotel.model.ApiKeyName;
import com.google.common.base.Ticker;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by sgupt13 on 25/12/16.
 */
@Service("apiRateLimitServices")
@PropertySource("classpath:application.properties")
public class APIRateLimitServices implements IAPIRateLimitServices, InitializingBean {


    @Autowired
    private Environment env;

    private Map<String, RequestTokenBucket> apiRequestTokenBucket = new HashMap<>();
    ;
    private final Ticker ticker = Ticker.systemTicker();

    @Override
    public void requestToken(String apiKey) throws Exception {
        RequestTokenBucket requestTokenBucket = apiRequestTokenBucket.get(apiKey);
        requestTokenBucket.requestToken();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ApiKeyName[] apiKeyNames = ApiKeyName.values();

        for (ApiKeyName apiKeyName : apiKeyNames) {
            RateLimit rateLimit = new RateLimit(env.getProperty(apiKeyName.name()));
            TokenReissueStrategy tokenReissueStrategy = new FixedRateTokenReissueStrategy(ticker, rateLimit.getRequests(), rateLimit.getDurations(), TimeUnit.SECONDS);
            RequestTokenBucket requestTokenBucket = new RequestTokenBucketImpl(rateLimit.getRequests(), tokenReissueStrategy, rateLimit.getRequests());

            apiRequestTokenBucket.put(apiKeyName.name(), requestTokenBucket);
        }
    }

    class RateLimit {
        private int requests;
        private int durations;

        RateLimit(int requests, int durations) {
            this.requests = requests;
            this.durations = durations;
        }


        RateLimit(String input) throws IllegalArgumentException {
            String[] rateLimitValue = input.split(",");

            if (rateLimitValue.length != 2)
                throw new IllegalArgumentException("Only two values are permitted.");

            try {
                this.requests = Integer.parseInt(rateLimitValue[0].trim());
                this.durations = Integer.parseInt(rateLimitValue[1].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Either or both parameters is/are non integer");
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new IllegalArgumentException("Something is wrong with exception");
            }
        }

        public int getRequests() {
            return requests;
        }

        public void setRequests(int requests) {
            this.requests = requests;
        }

        public int getDurations() {
            return durations;
        }

        public void setDurations(int durations) {
            this.durations = durations;
        }
    }


}
