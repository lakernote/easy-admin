package com.laker.admin.framework.aop.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRateLimiter implements EasyRateLimiter {

    private final ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();

    @Override
    public boolean tryAcquire(String key, int limit, long time) {
        // 初始化或获取限流器
        RateLimiter rateLimiter = limiters.computeIfAbsent(key, k -> {
            // 将 limit 转换为每秒生成的令牌数
            double permitsPerSecond = (double) limit / time;
            return RateLimiter.create(permitsPerSecond);
        });
        // 尝试获取令牌
        return rateLimiter.tryAcquire(1);
    }
}
