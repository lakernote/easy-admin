package com.laker.admin.example;

import org.junit.jupiter.api.Test;

public class ThreadCounter {
    private int count = 0;

    @Test
    public void testThreadCounter() throws InterruptedException {
        Runnable task = new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; ++i) {
                    count += 1;
                }
            }
        };

        Thread t1 = new Thread(task);
        t1.start();

        Thread t2 = new Thread(task);
        t2.start();

        t1.join();
        t2.join();

//        Assertions.assertThat(count).isEqualTo(20000);

    }
}