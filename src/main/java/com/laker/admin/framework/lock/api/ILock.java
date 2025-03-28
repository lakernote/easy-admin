package com.laker.admin.framework.lock.api;

import java.time.Duration;

/**
 * @author laker
 */
public interface ILock {
    /**
     * 尝试获取锁，
     *
     * @param key        锁定的key
     * @param expiration 锁过期时间
     * @return 锁定失败返回null
     */
    Locker acquire(String key, Duration expiration);

    /**
     * 释放锁
     *
     * @return 是否成功
     */
    boolean release(Locker lock);
}