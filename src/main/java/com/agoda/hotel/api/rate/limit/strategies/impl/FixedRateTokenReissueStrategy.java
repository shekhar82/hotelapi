package com.agoda.hotel.api.rate.limit.strategies.impl;

import com.agoda.hotel.api.rate.limit.strategies.TokenReissueStrategy;
import com.google.common.base.Ticker;

import java.util.concurrent.TimeUnit;

/**
 * Created by sgupt13 on 24/12/16.
 */
public class FixedRateTokenReissueStrategy implements TokenReissueStrategy {

    private final Ticker ticker;
    private final long numRequestTokenPerPeriod;
    private final long periodDurationInNanos;
    private long lastTokenReissueTime;
    private long nextTokenReissueTime;


    public FixedRateTokenReissueStrategy(Ticker ticker, long numRequestTokenPerPeriod, long period, TimeUnit unit) {
        this.ticker = ticker;
        this.numRequestTokenPerPeriod = numRequestTokenPerPeriod;
        this.periodDurationInNanos = unit.toNanos(period);
        this.lastTokenReissueTime = -periodDurationInNanos;
        this.nextTokenReissueTime = -periodDurationInNanos;
    }



    @Override
    public synchronized long reissue() {
        long now = ticker.read();
        if (now < nextTokenReissueTime) {
            return 0;
        }

        long numPeriods = Math.max(0, (now - lastTokenReissueTime) / periodDurationInNanos);

        lastTokenReissueTime += numPeriods * periodDurationInNanos;

        nextTokenReissueTime = lastTokenReissueTime + periodDurationInNanos;

        return numPeriods * numRequestTokenPerPeriod;
    }

    @Override
    public long getDurationUntilNextReissue(TimeUnit unit) throws UnsupportedOperationException {
        long now = ticker.read();
        return unit.convert(Math.max(0, nextTokenReissueTime - now), TimeUnit.NANOSECONDS);
    }
}
