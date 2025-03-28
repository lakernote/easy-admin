package com.laker.admin.framework.aop.ratelimit;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

public class RedissonRateLimiter implements EasyRateLimiter {

    private final RedissonClient redissonClient;

    public RedissonRateLimiter(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean tryAcquire(String key, int limit, long timeWindow) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL, limit, timeWindow, RateIntervalUnit.SECONDS);
        return rateLimiter.tryAcquire();
    }
}
