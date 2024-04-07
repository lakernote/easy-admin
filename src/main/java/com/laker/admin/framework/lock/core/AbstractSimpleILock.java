package com.laker.admin.framework.lock.core;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.exception.BusinessException;
import com.laker.admin.framework.lock.api.ILock;
import com.laker.admin.framework.lock.api.Locker;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

/**
 * @author laker
 */
public abstract class AbstractSimpleILock implements ILock {
    private final TaskScheduler taskScheduler;

    protected AbstractSimpleILock(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public Locker acquire(final String key, final Duration expiration) {
        final String token = IdUtil.fastSimpleUUID();
        String acquire = acquire(key, token, expiration);
        if (StrUtil.isBlank(acquire)) {
            throw new BusinessException("其他人正在处理中，请稍后重试");
        }
        // 后台线程定时续租 一个锁一个后台线程续约
        ScheduledFuture<?> scheduledFuture = scheduleLockRefresh(key, acquire, expiration);
        return Locker.builder().key(key).token(token).scheduledFuture(scheduledFuture).build();
    }

    @Override
    public boolean release(Locker lock) {
        cancelSchedule(lock);
        return release0(lock);
    }

    private ScheduledFuture<?> scheduleLockRefresh(final String key, final String token, final Duration expiration) {
        return taskScheduler.scheduleAtFixedRate(() ->
                refresh(key, token, expiration), expiration.toMillis() / 3);

    }

    private void cancelSchedule(Locker lock) {
        final ScheduledFuture<?> scheduledFuture = lock.getScheduledFuture();
        if (scheduledFuture != null && !scheduledFuture.isCancelled() && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(true);
        }
    }

    public abstract String acquire(String key, String token, Duration expiration);

    public abstract boolean release0(Locker lock);

    public abstract boolean refresh(String key, String token, Duration expiration);
}