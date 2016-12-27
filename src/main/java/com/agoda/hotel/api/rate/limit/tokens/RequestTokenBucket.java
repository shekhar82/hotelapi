package com.agoda.hotel.api.rate.limit.tokens;

import java.util.concurrent.TimeUnit;

/**
 * Created by sgupt13 on 24/12/16.
 */
public interface RequestTokenBucket {

    long getCapacity();

    long getNumRequestTokens();

    long getDurationUntilNextReissue(TimeUnit unit) throws UnsupportedOperationException;

    void requestToken() throws Exception;

    void reissue(long numRequestTokens);

}
