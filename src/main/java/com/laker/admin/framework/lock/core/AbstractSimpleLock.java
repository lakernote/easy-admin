package com.laker.admin.framework.lock.core;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.lock.api.Lock;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author laker
 */
public abstract class AbstractSimpleLock implements Lock {
    private TaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> currentThreadLock = new ConcurrentHashMap<>();

    protected AbstractSimpleLock(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public String acquire(final String key, final Duration expiration) {
        final String token = IdUtil.fastSimpleUUID();
        String acquire = acquire(key, token, expiration);
        if (StrUtil.isNotBlank(acquire)) {
            // 后台线程定时续租
            scheduleLockRefresh(key, acquire, expiration);
        }
        return acquire;
    }

    @Override
    public boolean release(final String key, final String token) {
        cancelSchedule(key, token);
        return release0(key, token);
    }

    private void scheduleLockRefresh(final String key, final String token, final Duration expiration) {
        if (expiration != null) {
            currentThreadLock.put(key + ":" + token, taskScheduler.scheduleAtFixedRate(() -> {
                refresh(key, token, expiration);
            }, expiration.toMillis() / 3));

        }
    }

    private void cancelSchedule(final String key, final String token) {
        final ScheduledFuture<?> scheduledFuture = currentThreadLock.get(key + ":" + token);
        if (scheduledFuture != null && !scheduledFuture.isCancelled() && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(true);
        }
    }

    protected abstract String acquire(String key, String token, Duration expiration);

    protected abstract boolean release0(String key, String token);

    protected abstract boolean refresh(String key, String token, Duration expiration);
}