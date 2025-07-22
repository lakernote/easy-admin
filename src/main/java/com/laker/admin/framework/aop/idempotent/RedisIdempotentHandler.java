package com.laker.admin.framework.aop.idempotent;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Redis 实现的幂等性处理器
 * 使用 Redis 的 SETNX 命令实现幂等性，确保同一操作在指定时间内只能执行一次
 * 过期时间可以通过参数设置
 */
public class RedisIdempotentHandler implements IdempotentHandler {

    private static final String KEY_PREFIX = "idempotent:";

    private final StringRedisTemplate stringRedisTemplate;

    public RedisIdempotentHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean checkAndSet(String key, long expireTime) {
        if (key == null || key.trim().isEmpty()) {
            return false;
        }

        String redisKey = KEY_PREFIX + key;
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(redisKey, "1", expireTime, TimeUnit.SECONDS));
    }

    @Override
    public void remove(String key) {
        if (key == null || key.trim().isEmpty()) {
            return;
        }

        String redisKey = KEY_PREFIX + key;
        stringRedisTemplate.delete(redisKey);
    }
}