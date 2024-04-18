package com.laker.admin.config;

import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching // 开启缓存功能
public class EasyCacheConfig {

    @Bean
    public RedissonSpringCacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> cacheConfigMap = new HashMap<>();
        CacheConfig cacheConfig = new CacheConfig(24 * 60 * 60 * 1000, 12 * 60 * 60 * 1000);
        cacheConfig.setMaxSize(100000);
        cacheConfigMap.put("loginCache", cacheConfig);
        cacheConfigMap.put("cacheName2", new CacheConfig(24 * 60 * 60 * 1000, 12 * 60 * 60 * 1000));
        cacheConfigMap.put("cacheName3", new CacheConfig(24 * 60 * 60 * 1000, 12 * 60 * 60 * 1000));
        return new RedissonSpringCacheManager(redissonClient, cacheConfigMap,new JsonJacksonCodec());
    }

}