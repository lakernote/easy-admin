package com.laker.admin.framework.ext.step;

import io.micrometer.core.instrument.util.NamedThreadFactory;
import io.micrometer.core.instrument.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.*;

/**
 * 按步长 定期存储到任意位置，例如db
 * @author laker
 */
@Slf4j
public class LakerMeterRegistry {
    private final Object meterMapLock = new Object();
    private Duration step;
    private ScheduledExecutorService scheduledExecutorService;

    private final ConcurrentMap<String, StepCounterTuple> meterMap = new ConcurrentHashMap<>();

    public LakerMeterRegistry() {
        this(Duration.ofMillis(1));
    }


    public LakerMeterRegistry(Duration step) {
        this(step, new NamedThreadFactory("laker-metrics-publisher"));
    }

    private LakerMeterRegistry(Duration step, ThreadFactory threadFactory) {
        this.step = step;
        start(threadFactory);
    }


    public StepCounterTuple counter(String videoId) {
        String id = "laker:" + videoId;
        StepCounterTuple multiStepCounter = meterMap.get(id);
        if (multiStepCounter == null) {
            synchronized (meterMapLock) {
                multiStepCounter = meterMap.get(id);
                if (multiStepCounter == null) {
                    multiStepCounter = new StepCounterTuple(id);
                    StepCounterTuple old = meterMap.putIfAbsent(id, multiStepCounter);
                    if (old != null) {
                        log.warn("有并发问题 拉了裤了 " + Thread.currentThread().getName() + id);
                    }
                }
            }
        }

        return multiStepCounter;
    }

    protected void publish() {
        Collection<StepCounterTuple> values = meterMap.values();
        log.info("---- start 当前map 容量" + values.size());
        values.stream().forEach(multiStepCounter -> {
            long count1 = multiStepCounter.count1();
            long count2 = multiStepCounter.count2();
            // if 存在无效的数据则删除掉 防止占内存，或者 这个另起一个定时任务去删除 定时时长 更久些。
            if (count1 + count2 == 0) {
                String id = multiStepCounter.getId();
                StepCounterTuple m = meterMap.get(id);

                if (m != null) {
                    synchronized (meterMapLock) {
                        m = meterMap.remove(id);
                        if (m != null) {
                            log.info(" remove " + id);
                        }
                    }
                }

            }
            // 不要重复调用   multiStepCounter.count() 这个方法会重置 的。模拟写入db
            log.info(multiStepCounter.getId() + " count1: " + count1 + " count2: " + count2);

        });

        log.info("---- end 结束后 map 容量 " + values.size());


    }


    private void publishSafely() {
        try {
            publish();
        } catch (Throwable e) {
            log.warn("Unexpected exception thrown", e);
        }
    }


    public void start(ThreadFactory threadFactory) {
        if (scheduledExecutorService != null) {
            stop();
        }

        log.info("publishing metrics for " + this.getClass().getSimpleName() + " every " + TimeUtils.format(step));

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(threadFactory);
        scheduledExecutorService.scheduleAtFixedRate(this::publishSafely, step
                .toMillis(), step.toMillis(), TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    public void close() {
        publishSafely();
        stop();
    }

}
