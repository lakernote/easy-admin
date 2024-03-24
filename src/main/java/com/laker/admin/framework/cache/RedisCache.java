package com.laker.admin.framework.cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author laker
 */
public class RedisCache implements ICache {
    
    RedisTemplate<String, Object> cacheRedisTemplate;

    public RedisCache(RedisTemplate<String, Object> cacheRedisTemplate) {
        this.cacheRedisTemplate = cacheRedisTemplate;
    }

    @Override
    public void put(String key, Object value) {
        cacheRedisTemplate.opsForValue().set(key, value, 1, TimeUnit.DAYS);
    }

    @Override
    public void put(String key, Object value, long timeout) {
        cacheRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        cacheRedisTemplate.delete(key);
    }

    @Override
    public <T> T get(String key) {
        return (T) cacheRedisTemplate.opsForValue().get(key);
    }

}
