package com.laker.admin.framework.lock.core;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.lock.api.LLock;
import com.laker.admin.framework.lock.api.Lock;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

/**
 * @author laker
 */
public abstract class AbstractSimpleLock implements Lock {
    private TaskScheduler taskScheduler;

    protected AbstractSimpleLock(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public LLock acquire(final String key, final Duration expiration) {
        final String token = IdUtil.fastSimpleUUID();
        String acquire = acquire(key, token, expiration);
        if (StrUtil.isBlank(acquire)) {
            return null;
        }
        // 后台线程定时续租 一个锁一个后台线程续约
        ScheduledFuture<?> scheduledFuture = scheduleLockRefresh(key, acquire, expiration);
        return LLock.builder().key(key).token(token).scheduledFuture(scheduledFuture).build();
    }

    @Override
    public boolean release(LLock lock) {
        cancelSchedule(lock);
        return release0(lock);
    }

    private ScheduledFuture<?> scheduleLockRefresh(final String key, final String token, final Duration expiration) {
        return taskScheduler.scheduleAtFixedRate(() ->
                refresh(key, token, expiration), expiration.toMillis() / 3);

    }

    private void cancelSchedule(LLock lock) {
        final ScheduledFuture<?> scheduledFuture = lock.getScheduledFuture();
        if (scheduledFuture != null && !scheduledFuture.isCancelled() && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(true);
        }
    }

    protected abstract String acquire(String key, String token, Duration expiration);

    protected abstract boolean release0(LLock lock);

    protected abstract boolean refresh(String key, String token, Duration expiration);
}