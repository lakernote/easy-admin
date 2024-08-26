package com.laker.admin.example;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalMemoryLeakExample {
    // 创建一个ThreadLocal变量
    private static final ThreadLocal<LargeObject> threadLocal = new ThreadLocal<>();

    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                // 为每个线程创建一个大的对象，并通过弱引用存储在ThreadLocal中
                LargeObject largeObject = new LargeObject();
                threadLocal.set(largeObject);
            });
        }

        // 等待所有任务执行完成
        TimeUnit.SECONDS.sleep(5);
        // 获取当前程序的内存使用量
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Used memory: " + usedMemory / 1024 / 1024 + "MB");
        // 执行垃圾回收
        System.gc();
        // 再次获取当前程序的内存使用量
        usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        // 这里预期应该很低，因为ThreadLocal中的对象应该被回收
        // 但是由于没有调用remove方法，导致ThreadLocalMap中的Entry对象无法被回收 约等于5个线程 * 10MB 的泄露
        System.out.println("Used memory after GC: " + usedMemory / 1024 / 1024 + "MB");
    }

    // 模拟占用较大内存的对象
    static class LargeObject {
        private final byte[] data = new byte[10 * 1024 * 1024]; // 10MB

        @Override
        public String toString() {
            return "LargeObject@" + hashCode();
        }
    }
}
