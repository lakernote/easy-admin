package com.laker.admin.framework.aop.ratelimit;

public interface EasyRateLimiter {
    boolean tryAcquire(String key, int limit, long timeout);
}
