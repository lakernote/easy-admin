//package com.laker.admin.config;
//
//import com.giffing.bucket4j.spring.boot.starter.config.cache.SyncCacheResolver;
//import com.giffing.bucket4j.spring.boot.starter.config.cache.jcache.JCacheCacheResolver;
//import io.github.bucket4j.distributed.proxy.ProxyManager;
//import io.github.bucket4j.grid.jcache.JCacheProxyManager;
//import org.redisson.config.Config;
//import org.redisson.jcache.configuration.RedissonConfiguration;
//import org.redisson.spring.starter.RedissonAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.cache.CacheManager;
//import javax.cache.Caching;
//import javax.cache.spi.CachingProvider;
//
//@Configuration
//public class RedisConfig
//{
//    @Bean
//    public Config config()
//    {
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://localhost:6379");
//        return config;
//    }
//
//    @Bean
//    public CacheManager cacheManager(Config config) {
//        CacheManager manager = Caching.getCachingProvider().getCacheManager();
//        manager.createCache("cache", RedissonConfiguration.fromConfig(config));
//        return manager;
//    }
//
//    @Bean
//    ProxyManager<String> proxyManager(CacheManager cacheManager) {
//        return new JCacheProxyManager<>(cacheManager.getCache("cache"));
//    }
//
//    /**
//     * reference:
//     * https://github.com/MarcGiffing/bucket4j-spring-boot-starter/issues/73
//     * */
//    @Bean
//    @Primary
//    public SyncCacheResolver bucket4jCacheResolver(CacheManager cacheManager) {
//        return new JCacheCacheResolver(cacheManager);
//    }
//
//
//}