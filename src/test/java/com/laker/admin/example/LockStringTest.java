package com.laker.admin.example;

import cn.hutool.core.util.RandomUtil;
import com.laker.admin.framework.aop.duplicate.ConcurrentHashMapDuplicateRequestLimiter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LockStringTest {

    @SneakyThrows
    @Test
    public void testLockString() {
        LockStringTest lockStringTest = new LockStringTest();
        Thread thread1 = new Thread(() -> lockStringTest.lockString(LockStringTest.getKey("1", "1")));
        Thread thread2 = new Thread(() -> lockStringTest.lockString(LockStringTest.getKey("1", "1")));

        thread1.start();
        thread2.start();
        Thread.sleep(7000);
    }

    private static String getKey(String siteID, String nbrRecordID) {
        return siteID + "_" + nbrRecordID;
    }

    @SneakyThrows
    private void lockString(String str) {
        System.out.println(str.hashCode());
        synchronized (str) { // 这样锁不住的 其实是2个对象 改成str.intern()就可以锁住
            Thread.sleep(3000);
            System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + "lock string: " + str);
        }
    }


    @Test
    public void test2() throws InterruptedException {
        ConcurrentHashMapDuplicateRequestLimiter concurrentHashMapDuplicateRequestLimiter = new ConcurrentHashMapDuplicateRequestLimiter();
        // 用 CountDownLatch 来等待所有线程完成
        final int count = 40;
        CountDownLatch latch = new CountDownLatch(count);
        // 使用 AtomicInteger 来确保最多只有一个请求通过
        AtomicInteger allowedCount = new AtomicInteger(0);
        // 启动 10 个并发线程请求
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    boolean result = concurrentHashMapDuplicateRequestLimiter.tryRequest(RandomUtil.randomChar("ab") + "", 10);
                    // 你可以通过日志或者断言来验证结果
                    if (result) {
                        allowedCount.incrementAndGet();  // 如果返回 true，增加计数
                    }
                    // 模拟业务处理
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();  // 每个线程完成后减少计数
                }
            }).start();
        }

        // 等待所有线程完成
        latch.await();

        // 验证结果
        log.info("Allowed request count: {}", allowedCount.get());
        Assertions.assertThat(allowedCount.get()).isEqualTo(2);
    }
}
