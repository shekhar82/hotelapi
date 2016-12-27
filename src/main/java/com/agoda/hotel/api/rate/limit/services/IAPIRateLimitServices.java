package com.agoda.hotel.api.rate.limit.services;

/**
 * Created by sgupt13 on 25/12/16.
 */
public interface IAPIRateLimitServices {

    public void requestToken(String apiKey) throws Exception;
}
