package com.laker.admin.framework.aop.duplicate;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisDuplicateRequestLimiter implements DuplicateRequestLimiter {

    private final StringRedisTemplate redisTemplate;

    public RedisDuplicateRequestLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryRequest(String key, long time) {
        // 使用 Redis 锁防止重复提交
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, "LOCK", time, TimeUnit.SECONDS));

    }
}
