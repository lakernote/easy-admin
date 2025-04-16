package com.laker.admin.framework.aop.idempotent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisIdempotentHandler implements IdempotentHandler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean checkAndSet(String key, long expireTime) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, "1", expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }
}    