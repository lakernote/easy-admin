package com.laker.admin.framework.aop.ratelimit;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EasyRateLimiterFactory {

    private final EasyRateLimiter rateLimiter;

    public EasyRateLimiterFactory(@Autowired(required = false) RedissonClient redissonClient) {
        if (redissonClient == null) {
            this.rateLimiter = new InMemoryRateLimiter();
            return;
        }
        this.rateLimiter = new RedissonRateLimiter(redissonClient);
    }


    public EasyRateLimiter getRateLimiter() {
        return rateLimiter; // 默认使用内存限流
    }
}
