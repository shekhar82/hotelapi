package com.agoda.hotel.api.rate.limit.tokens.impl;

import com.agoda.hotel.api.rate.limit.strategies.TokenReissueStrategy;
import com.agoda.hotel.api.rate.limit.tokens.RequestTokenBucket;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by sgupt13 on 24/12/16.
 */
public class RequestTokenBucketImpl implements RequestTokenBucket {

    private final long capacity;
    private final TokenReissueStrategy reissueStrategy;
    private long size;
    private boolean haltReuqestToken;


    public RequestTokenBucketImpl(long capacity, TokenReissueStrategy reissueStrategy, long size) {
        this.capacity = capacity;
        this.reissueStrategy = reissueStrategy;
        this.size = size;
        this.haltReuqestToken = false;
    }

    @Override
    public long getCapacity() {
        return capacity;
    }

    @Override
    public long getNumRequestTokens() {
        reissue(reissueStrategy.reissue());
        return size;
    }

    @Override
    public long getDurationUntilNextReissue(TimeUnit unit) throws UnsupportedOperationException {
        return 0;
    }

    @Override
    public void requestToken() throws Exception {

        if (haltReuqestToken)
        {
            throw new Exception ("API rate limit has been exceeded. Please wait till 5 minutes get over from your last successful try.");
        }
        else if (!tryRequestToken(1))
        {
            haltReuqestToken = true;
            ReissueTimerTask reissueTimerTask = new ReissueTimerTask();
            Timer timer = new Timer(true);
            timer.schedule(reissueTimerTask,5*60*1000);
            throw new Exception ("API rate limit exceeded. Please try after 5 minutes");
        }
    }

    private synchronized boolean tryRequestToken(int numRequestTokens) {
        reissue(reissueStrategy.reissue());
        if (numRequestTokens <= size) {
            size -= numRequestTokens;
            return true;
        }

        return false;

    }

    @Override
    public synchronized  void reissue(long numRequestTokens) {
        long newTokens = Math.min(capacity, Math.max(0, numRequestTokens));
        size = Math.max(0, Math.min(size + newTokens, capacity));
    }

    private class ReissueTimerTask extends TimerTask
    {

        @Override
        public void run() {
            System.out.println("Timer task started");
            RequestTokenBucketImpl.this.haltReuqestToken = false;
        }
    }

}
