package com.laker.admin.framework.lock;

import com.laker.admin.framework.lock.base.EasyLocker;

import java.time.Duration;

/**
 * 分布式锁
 *
 * @author laker
 */
public interface IEasyLocker {
    /**
     * 尝试获取锁，非阻塞式
     *
     * @param key        锁定的key
     * @param expiration 锁过期时间
     * @return 锁定失败返回null
     */
    EasyLocker tryAcquire(String key, Duration expiration);

    /**
     * 释放锁
     *
     * @return 是否成功
     */
    boolean release(EasyLocker lock);
}