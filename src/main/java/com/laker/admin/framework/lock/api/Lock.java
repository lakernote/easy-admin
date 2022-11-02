package com.laker.admin.framework.lock.api;

import java.time.Duration;

/**
 * @author laker
 */
public interface Lock {
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
     * @param key   锁定的key
     * @param token 用于检查是否是这个锁，防止误删
     * @return 是否成功
     */
    boolean release(LLock lock);
}