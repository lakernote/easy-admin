package com.laker.admin.framework.aop.ratelimit;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryRateLimiter implements EasyRateLimiter {

    private final ConcurrentHashMap<String, RateLimitInfo> limiters = new ConcurrentHashMap<>();

    @Override
    public boolean tryAcquire(String key, int limit, long timeout) {
        RateLimitInfo rateLimitInfo = limiters.computeIfAbsent(key, k -> new RateLimitInfo(limit, timeout));
        return rateLimitInfo.tryAcquire();
    }

    private static class RateLimitInfo {
        private final int limit;
        private final long interval;
        private final AtomicInteger count = new AtomicInteger();
        private long lastResetTime = System.currentTimeMillis();

        RateLimitInfo(int limit, long intervalMinutes) {
            this.limit = limit;
            this.interval = intervalMinutes * 60 * 1000;
        }

        synchronized boolean tryAcquire() {
            long now = System.currentTimeMillis();
            if (now - lastResetTime > interval) {
                count.set(0);
                lastResetTime = now;
            }
            return count.incrementAndGet() <= limit;
        }
    }
}
