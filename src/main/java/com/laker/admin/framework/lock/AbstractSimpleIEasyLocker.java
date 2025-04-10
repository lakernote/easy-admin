package com.laker.admin.framework.lock;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.laker.admin.framework.EasyAdminConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

/**
 * 分布式锁
 *
 * @author laker
 */
@Slf4j
public abstract class AbstractSimpleIEasyLocker implements IEasyLocker {
    private final TaskScheduler taskScheduler;

    protected AbstractSimpleIEasyLocker(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public EasyLocker tryAcquire(final String key, final Duration expiration) {
        final String token = IdUtil.fastSimpleUUID();
        String acquire = acquire(key, token, expiration);
        if (CharSequenceUtil.isBlank(acquire)) {
            // 获取锁失败
            log.info("Failed to acquire lock for key {} with token {}", key, token);
            return null;
        }
        // 后台线程定时续租 一个锁一个后台线程续约
        ScheduledFuture<?> scheduledFuture = scheduleLockRefresh(key, acquire, expiration);
        return EasyLocker.builder()
                .key(key)
                .token(token)
                .scheduledFuture(scheduledFuture).build();
    }

    @Override
    public boolean release(EasyLocker lock) {
        cancelSchedule(lock);
        return release0(lock);
    }

    /**
     * 续租锁
     *
     * @param key        锁定的key
     * @param token      锁的token
     * @param expiration 锁过期时间
     */
    private ScheduledFuture<?> scheduleLockRefresh(final String key, final String token, final Duration expiration) {
        // 获取当前线程的traceId
        final String traceId = MDC.get(EasyAdminConstants.TRACE_ID);
        return taskScheduler.scheduleAtFixedRate(() ->
        {
            // 设置线程的traceId，来自锁的traceId
            MDC.put(EasyAdminConstants.TRACE_ID, traceId);
            try {
                refresh(key, token, expiration);
            } catch (Exception e) {
                // 续租失败，可能是锁已经被释放了
                log.error("Failed to refresh lock for key {} with token {}", key, token, e);
            } finally {
                // 清除线程的traceId
                MDC.remove(EasyAdminConstants.TRACE_ID);
            }
        }, Duration.ofMillis(expiration.toMillis() / 3));     // 续租时间为锁的三分之一

    }

    /**
     * 取消续租
     *
     * @param lock 锁
     */
    private void cancelSchedule(EasyLocker lock) {
        final ScheduledFuture<?> scheduledFuture = lock.getScheduledFuture();
        if (scheduledFuture != null && !scheduledFuture.isCancelled() && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(true);
        }
    }

    /**
     * 获取锁
     *
     * @param key        锁定的key
     * @param token      锁的token
     * @param expiration 锁过期时间
     * @return 锁定失败返回null
     */
    protected abstract String acquire(String key, String token, Duration expiration);

    /**
     * 释放锁
     *
     * @param lock 锁
     * @return 是否成功
     */
    protected abstract boolean release0(EasyLocker lock);

    /**
     * 刷新锁过期时间
     *
     * @param key        锁定的key
     * @param token      锁的token
     * @param expiration 锁过期时间
     * @return 是否成功
     */
    protected abstract boolean refresh(String key, String token, Duration expiration);
}