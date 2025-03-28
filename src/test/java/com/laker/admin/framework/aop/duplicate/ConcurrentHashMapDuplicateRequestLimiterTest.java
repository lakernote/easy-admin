package com.laker.admin.framework.aop.duplicate;

import org.junit.jupiter.api.Test;

class ConcurrentHashMapDuplicateRequestLimiterTest {
    ConcurrentHashMapDuplicateRequestLimiter concurrentHashMapDuplicateRequestLimiter = new ConcurrentHashMapDuplicateRequestLimiter();

    @Test
    void tryRequest() {
        concurrentHashMapDuplicateRequestLimiter.tryRequest("key", 1);
    }

    @Test
    void cleanUp() {
        concurrentHashMapDuplicateRequestLimiter.cleanUp();
    }

    @Test
        // 并发测试 tryRequest 和 cleanUp
    void concurrentTest() throws InterruptedException {
        final Thread thread1 = new Thread(() -> {
            concurrentHashMapDuplicateRequestLimiter.tryRequest("key", 1);
        });
        final Thread thread2 = new Thread(() -> {
            concurrentHashMapDuplicateRequestLimiter.cleanUp();
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }

    @Test
        // 并发测试 tryRequest 和 tryRequest
    void concurrentTest2() throws InterruptedException {
        final Thread thread1 = new Thread(() -> {
            concurrentHashMapDuplicateRequestLimiter.tryRequest("key", 1);
        });
        final Thread thread2 = new Thread(() -> {
            concurrentHashMapDuplicateRequestLimiter.tryRequest("key", 1);
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}