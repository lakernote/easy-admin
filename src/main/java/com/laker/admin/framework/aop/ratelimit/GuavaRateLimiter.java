package com.laker.admin.framework.aop.ratelimit;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GuavaRateLimiter implements IRateLimit {
    private final Map<String, RateLimiter> limiterMap = Maps.newConcurrentMap();

    @Override
    public boolean tryAcquire(Limiter limiter) {
        // get rate limiter
        RateLimiter rateLimiter = limiterMap.computeIfAbsent(limiter.getKey(),
                k -> RateLimiter.create((double) limiter.getLimitNum() / limiter.getSeconds()));
        boolean access = rateLimiter.tryAcquire();
        log.info("tryAcquire key:{} access :{}", limiter.getKey(), access);
        return access;
    }
}