package com.laker.admin.framework.aop.repeatedsubmit;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 基于 ConcurrentHashMap 实现的重复请求限制器
 * <pre>
 *    1.支持自动过期时间设置、缓存淘汰
 *    2.支持并发请求，线程安全 (ConcurrentHashMap) uuid 保证唯一性
 *    3.支持内存淘汰策略 TODO 用弱引用 / 增加缓存大小限制
 *    4.高效的并发访问，提供原子操作（compute）
 * </pre>
 */
@Slf4j
public class ConcurrentHashMapDuplicateRequestLimiter implements DuplicateRequestLimiter {

    // 存储 key 及其最近请求时间和超时时间
    private final ConcurrentHashMap<String, ExpiringEntry> requestMap = new ConcurrentHashMap<>();

    public ConcurrentHashMapDuplicateRequestLimiter() {
        // 初始化定时任务清理过期key
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::cleanUp, 1, 1, TimeUnit.MINUTES);
        log.info("Scheduled cleanup task started, running every minute.");
    }

    @Override
    public boolean tryRequest(String key, long timeout) {
        // 使用 nanoTime() 获取更高精度的时间戳
        long now = System.nanoTime(); // 返回纳秒级别的时间戳
        String uuid = UUID.randomUUID().toString(); // 生成唯一标识符
        log.info("Attempting request for key: {} with timeout: {}s", key, timeout);
        // 计算并更新 key 对应的 ExpiringEntry
        //compute(key, (k, v) -> newValue)用于在并发环境下安全地更新 key 对应的值。
        ExpiringEntry entry = requestMap.compute(key, (k, v) -> handleEntry(k, v, now, timeout, uuid));
        // 如果返回的Entry uuid 与当前请求的 uuid 相同，则允许请求
        boolean isRequestAllowed = entry.uuid.equals(uuid);
        if (isRequestAllowed) {
            log.info("Request for key: {} is allowed. entry:{}", key, entry);
        }
        log.info("Request for key: {} is {}", key, isRequestAllowed ? "allowed" : "blocked");
        // 返回是否允许请求
        return isRequestAllowed;
    }

    /**
     * 处理并返回更新后的 ExpiringEntry
     */
    private ExpiringEntry handleEntry(String key, ExpiringEntry currentEntry, long now, long timeout, String uuid) {
        if (currentEntry == null || isExpired(currentEntry, now)) {
            log.info("Key: {} is expired or does not exist. Creating new entry.", key);
            return new ExpiringEntry(now, TimeUnit.SECONDS.toNanos(timeout), uuid);
        }
        log.info("Key: {} is not expired.", key);
        return currentEntry; // 维持原数据，拦截请求
    }

    /**
     * 判断条目是否过期
     */
    private boolean isExpired(ExpiringEntry entry, long now) {
        return now - entry.timestamp >= entry.timeout;
    }

    /**
     * 按 key 的超时时间清理过期 key
     */
    private void cleanUp() {
        // 使用 nanoTime() 获取更高精度的时间戳
        long now = System.nanoTime(); // 返回纳秒级别的时间戳
        log.info("Cleaning up expired entries.");
        // 记录清理前的 map 大小
        int beforeSize = requestMap.size();
        log.info("Request map size before cleanup: {}", beforeSize);

        // 遍历 requestMap 中的每一个条目，并检查它们的过期时间
        requestMap.entrySet().removeIf(entry -> {
            ExpiringEntry value = entry.getValue();
            if (isExpired(value, now)) {
                log.info("Entry for key: {} has expired and will be removed.", entry.getKey());
                return true; // 删除过期条目
            }
            return false; // 保留未过期的条目
        });
        // 记录清理后的 map 大小
        int afterSize = requestMap.size();
        log.info("Request map size after cleanup: {}", afterSize);
        log.info("Cleanup completed.");
    }

    /**
     * 存储 key 对应的上次访问时间 & 超时时间
     */
    private static class ExpiringEntry {
        long timestamp; // 上次访问时间 (纳秒)
        long timeout; // 该 key 的超时时间（纳秒）
        String uuid;  // 请求的唯一标识符

        ExpiringEntry(long timestamp, long timeout, String uuid) {
            this.timestamp = timestamp;
            this.timeout = timeout;
            this.uuid = uuid;
        }

        @Override
        public String toString() {
            return "ExpiringEntry{" +
                    "timestamp=" + timestamp +
                    ", timeout=" + timeout +
                    ", uuid='" + uuid +
                    '}';
        }
    }
}
