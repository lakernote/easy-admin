package com.laker.admin.framework.redis;

import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonCacheMeterBinderProvider;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching // 开启缓存功能
@ConditionalOnBean(EasyRedisProperties.class)
public class EasyRedisCacheConfig {
    public static final String CACHE_NAME_1H = "CACHE_NAME_1H";
    public static final String CACHE_NAME_12H = "CACHE_NAME_12H";
    public static final String CACHE_NAME_24H = "CACHE_NAME_24H";

    @Bean
    public RedissonSpringCacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> cacheConfigMap = new HashMap<>();
        // 参数ttl表示缓存条目的存活时间（以毫秒为单位），如果设为0，则缓存条目不会因为时间到期而被移除。
        // 参数maxIdleTime表示缓存条目的最大空闲时间（以毫秒为单位）。如果maxIdleTime和ttl参数都设置为0，则表示缓存条目永久存储。
        CacheConfig cacheConfig = new CacheConfig(TimeUnit.HOURS.toMillis(24), TimeUnit.HOURS.toMillis(12));
        cacheConfig.setMaxSize(100000);
        cacheConfigMap.put(CACHE_NAME_12H, cacheConfig);
        cacheConfigMap.put(CACHE_NAME_1H, new CacheConfig(TimeUnit.HOURS.toMillis(1), TimeUnit.HOURS.toMillis(1)));
        cacheConfigMap.put(CACHE_NAME_24H, new CacheConfig(TimeUnit.HOURS.toMillis(24), TimeUnit.HOURS.toMillis(24)));
        return getRedissonSpringCacheManager(redissonClient, cacheConfigMap);
    }

    @Bean
    public RedissonCacheMeterBinderProvider redissonCacheMeterBinderProvider(){
        return new RedissonCacheMeterBinderProvider();
    }

    private static RedissonSpringCacheManager getRedissonSpringCacheManager(RedissonClient redissonClient, Map<String, CacheConfig> cacheConfigMap) {
        RedissonSpringCacheManager redissonSpringCacheManager = new RedissonSpringCacheManager(redissonClient);
        // 设置是否允许缓存的值为null。默认值为false，即缓存的值不允许为null。
        redissonSpringCacheManager.setAllowNullValues(true);
        // 设置缓存是否感知Spring管理的事务。如果设置为true，则缓存操作只会在事务成功提交后的after-commit阶段执行，
        // 即在事务成功完成后才会执行put/evict操作。默认值为false，即缓存不感知Spring管理的事务。
        redissonSpringCacheManager.setTransactionAware(false);
        // 设置缓存的配置信息
        redissonSpringCacheManager.setConfig(cacheConfigMap);
        // 设置缓存的编码方式
        redissonSpringCacheManager.setCodec(new JsonJacksonCodec());
        return redissonSpringCacheManager;
    }
}