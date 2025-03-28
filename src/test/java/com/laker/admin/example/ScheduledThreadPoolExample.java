package com.laker.admin.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 如果定时任务抛出未捕获的异常，该任务会立即终止执行，并且后续的调度也会停止。
 */
public class ScheduledThreadPoolExample {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("定时任务开始执行");
            throw new RuntimeException("模拟异常");
        }, 0, 1, TimeUnit.SECONDS);
    }
}