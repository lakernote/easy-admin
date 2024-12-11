package com.laker.admin.framework.aop.ratelimit;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EasyRateLimiterFactory {

    private final EasyRateLimiter rateLimiter;

    public EasyRateLimiterFactory(@Autowired(required = false) RedissonClient redissonClient) {
        if (redissonClient == null) {
            log.warn("RedissonClient is not available, falling back to InMemoryRateLimiter.");
            this.rateLimiter = new InMemoryRateLimiter();
        } else {
            log.info("RedissonClient is available, using RedissonRateLimiter.");
            this.rateLimiter = new RedissonRateLimiter(redissonClient);
        }
    }

    public EasyRateLimiter getRateLimiter() {
        return rateLimiter;
    }
}
