package com.laker.admin.framework.aop.repeatedsubmit;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class MemoryRepeatSubmiter implements EasyRepeatSubmiter {

    // 使用 ConcurrentHashMap 来存储提交标识和过期时间
    private final ConcurrentHashMap<String, Long> lockMap = new ConcurrentHashMap<>();

    @Override
    public boolean tryAcquire(String key, long time) {
        // 当前时间
        long currentTime = System.currentTimeMillis();
        // 过期时间
        long expireTime = currentTime + TimeUnit.SECONDS.toMillis(time);

        // 使用原子操作检查和设置过期时间
        return lockMap.compute(key, (k, existingExpireTime) -> {
            if (existingExpireTime == null || existingExpireTime <= currentTime) {
                // 锁不存在或锁已过期，设置新的过期时间
                return expireTime;
            }
            // 锁未过期，返回当前的过期时间，表示锁已经存在
            return existingExpireTime;
        }) == expireTime; // 如果设置成功，返回 true，否则返回 false
    }
}
