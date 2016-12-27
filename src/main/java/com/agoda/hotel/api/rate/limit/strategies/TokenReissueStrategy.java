package com.agoda.hotel.api.rate.limit.strategies;

import java.util.concurrent.TimeUnit;

/**
 * Created by sgupt13 on 24/12/16.
 */
public interface TokenReissueStrategy
{
    long reissue();
    long getDurationUntilNextReissue(TimeUnit unit) throws UnsupportedOperationException;
}
