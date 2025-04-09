package com.laker.admin.framework.lock;

import java.time.Duration;

/**
 * 分布式锁
 *
 * @author laker
 */
public interface IEasyLock {
    /**
     * 尝试获取锁，
     *
     * @param key        锁定的key
     * @param expiration 锁过期时间
     * @return 锁定失败返回null
     */
    LLock acquire(String key, Duration expiration);

    /**
     * 释放锁
     *
     * @return 是否成功
     */
    boolean release(LLock lock);
}