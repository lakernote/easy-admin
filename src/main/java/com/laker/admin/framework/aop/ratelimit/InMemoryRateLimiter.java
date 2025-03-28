package com.laker.admin.framework.aop.ratelimit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class InMemoryRateLimiter implements EasyRateLimiter {
    // 不能用ConcurrentHashMap，随着不同 key 的不断加入，存在内存溢出的风险,添加定时清理机制或者使用 Caffeine
//    private final ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();

    private final Cache<String, RateLimiter> limiters = Caffeine.newBuilder()
            // 设置缓存初始容量为10,在创建缓存时，会预先分配一定的内存空间来存储元素，避免在缓存元素数量逐渐增加时频繁进行扩容操作，从而提高性能。
            .initialCapacity(10)
            // 设置缓存的最大条目数为 1000，当缓存中的条目数超过 1000 时，会根据 LRU（最近最少使用）算法淘汰部分条目。
            .maximumSize(1000)
            // maximumWeight 用于设置缓存的最大权重，weigher 用于定义每个缓存元素的权重计算方式。当缓存元素的总权重超过 maximumWeight 时，会进行元素淘汰。
//            .maximumWeight(1000)
//            .weigher((String key, RateLimiter value) -> 1)
            // 设置缓存元素在写入后经过指定的时间自动过期。
            // 设置缓存项在写入（创建或更新）之后，经过指定的时间自动过期。只要缓存项被写入，就会开始计时，无论后续是否有访问操作。
            .expireAfterWrite(10, TimeUnit.MINUTES)
            // 设置缓存元素在最后一次访问后经过指定的时间自动过期。
            // 设置缓存项在最后一次访问之后，经过指定的时间自动过期。每次访问缓存项都会重置过期时间。
            // 会以这两个过期策略中先到期的那个为准来决定缓存项是否过期。
            .expireAfterAccess(10, TimeUnit.MINUTES)
            // 自定义缓存元素的过期策略，允许根据元素的创建、更新和访问时间动态计算过期时间。
//            .expireAfter(new Expiry<String, RateLimiter>() {
            // 该方法在缓存项被创建时调用，返回值表示从当前时间（currentTime，单位为纳秒）开始，缓存项在多少纳秒后过期。例如，返回 10 * 60 * 1000 * 1000 * 1000 表示缓存项在创建后的 10 分钟过期。
//                @Override
//                public long expireAfterCreate(String key, RateLimiter value, long currentTime) {
//                    return 10 * 60 * 1000; // 10 minutes
//                }
// 该方法在缓存项被更新时调用，currentDuration 表示当前缓存项剩余的过期时间（单位为纳秒）。返回值表示更新后缓存项的新过期时间，从当前时间（currentTime）开始计算，单位同样是纳秒。如果返回 currentDuration，则表示更新操作不改变缓存项的过期时间。
//                @Override
//                public long expireAfterUpdate(String key, RateLimiter value, long currentTime, long currentDuration) {
//                    return currentDuration;
//                }
//该方法在缓存项被读取时调用，currentDuration 表示当前缓存项剩余的过期时间（单位为纳秒）。返回值表示读取操作后缓存项的新过期时间，从当前时间（currentTime）开始计算，单位为纳秒。如果返回 currentDuration，则表示读取操作不改变缓存项的过期时间。
//                @Override
//                public long expireAfterRead(String key, RateLimiter value, long currentTime, long currentDuration) {
//                    return currentDuration;
//                }
//            })
            // 设置缓存元素在写入后经过指定的时间，在下一次访问时会异步刷新缓存。在刷新过程中，旧的缓存值仍然会被返回，直到新的值被加载完成。
//            .refreshAfterWrite(1, TimeUnit.MINUTES)
            // 设置缓存的键为弱引用。当键所引用的对象被垃圾回收时，对应的缓存条目会自动被移除。
            // 不可控的缓存失效：由于弱引用的特性，缓存条目可能会在开发者意想不到的时候被移除。如果缓存的键或值在其他地方没有强引用，那么它们可能会被过早地垃圾回收，导致缓存失效，影响系统的稳定性和性能。
            //难以调试：当缓存条目因弱引用被回收而失效时，排查问题会比较困难，因为很难确定具体是哪个对象被回收导致的缓存失效。
//            .weakKeys()
            // 设置缓存的值为弱引用。当值所引用的对象被垃圾回收时，对应的缓存条目会自动被移除。
//            .weakValues()
            // 设置缓存的值为软引用。当内存不足时，JVM 会尝试回收软引用对象，从而释放内存。
//            .softValues()
            // 开启缓存的统计信息记录功能。可以通过 cache.stats() 方法获取缓存的命中率、加载成功率、加载平均时间等统计信息。
            // 获取统计信息会对性能产生一定的影响，因此建议仅在调试或监控时使用。 cache.stats()
//            .recordStats()
            // 设置缓存元素被移除时的监听器。当缓存元素因过期、淘汰等原因被移除时，会触发监听器的回调方法。
            .removalListener((key, value, cause) -> log.info("key: {} removed, value: {}, cause: {}", key, value, cause))
            // 设置用于异步操作（如刷新缓存）的执行器。默认情况下，Caffeine 使用 ForkJoinPool.commonPool() 作为执行器。
//            .executor(executor)
            .build();

    @Override
    public boolean tryAcquire(String key, int limit, long timeWindow) {
        // 初始化或获取限流器
        // get(key, mappingFunction) 方法会先尝试获取 key 对应的 value，如果不存在则调用 mappingFunction 生成 value 并返回
        // 如果多个线程同时调用 get 方法，只有一个线程会调用 mappingFunction，其他线程会等待调用结果
        try {
            RateLimiter rateLimiter = limiters.get(key, k -> {
                // 将 limit 转换为每秒生成的令牌数
                double permitsPerSecond = (double) limit / timeWindow;
                return RateLimiter.create(permitsPerSecond);
            });
            // 尝试获取令牌
            return rateLimiter.tryAcquire(1);
        } catch (Exception e) {
            log.error("Failed to get or create RateLimiter for key: {}", key, e);
            return false;
        }
    }
}
