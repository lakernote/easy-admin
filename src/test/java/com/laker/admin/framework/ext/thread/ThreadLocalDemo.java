package com.laker.admin.framework.ext.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  ThreadLocal 子线程无法获取父线程属性
 *  结果：
 * pool-1-thread-2:1
 * pool-1-thread-1:0
 * pool-1-thread-5:4
 * pool-1-thread-6:5
 * pool-1-thread-9:8
 * pool-1-thread-10:9
 * pool-1-thread-3:2
 * pool-1-thread-4:3
 * pool-1-thread-7:6
 * pool-1-thread-8:7
 * parentThreadName:pool-1-thread-7:null  pool-2-thread-2
 * parentThreadName:pool-1-thread-2:null  pool-2-thread-1
 * parentThreadName:pool-1-thread-8:null  pool-2-thread-5
 * parentThreadName:pool-1-thread-10:null  pool-2-thread-2
 * parentThreadName:pool-1-thread-5:null  pool-2-thread-2
 * parentThreadName:pool-1-thread-9:null  pool-2-thread-1
 * parentThreadName:pool-1-thread-1:null  pool-2-thread-2
 * parentThreadName:pool-1-thread-6:null  pool-2-thread-5
 * parentThreadName:pool-1-thread-4:null  pool-2-thread-3
 * parentThreadName:pool-1-thread-3:null  pool-2-thread-4
 * </pre>
 */
public class ThreadLocalDemo {
    /**
     * 模拟tomcat线程池
     */
    private static ExecutorService tomcatExecutors = Executors.newFixedThreadPool(10);

    /**
     * 业务线程池,默认Control中异步任务执行线程池
     */
    private static ExecutorService businessExecutors = Executors.newFixedThreadPool(5);

    /**
     * 线程上下文环境,模拟在Control这一层,设置环境变量,然后在这里提交一个异步任务,模拟在子线程中,是否可以访问到刚设置的环境变量值
     */
    private static ThreadLocal<Integer> requestIdThreadLocal = new ThreadLocal<>();

    /**
     * 模式10个请求,每个请求执行ControlThread的逻辑,其具体实现就是,先输出父线程的名称,
     * 然后设置本地环境变量,并将父线程名称传入到子线程中,在子线程中尝试获取在父线程中的设置的环境变量
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; ++i) {
            tomcatExecutors.submit(new ControlThread(i));
        }

        TimeUnit.SECONDS.sleep(10);
        businessExecutors.shutdown();
        tomcatExecutors.shutdown();
    }

    /**
     * 模拟Control任务
     */
    static class ControlThread implements Runnable {
        private int i;

        public ControlThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":" + i);
            requestIdThreadLocal.set(i);
            //使用线程池异步处理任务
            businessExecutors.submit(new BusinessTask(Thread.currentThread().getName()));

        }
    }

    /**
     * 业务任务,主要是模拟在Control控制层,提交任务到线程池执行
     */
    static class BusinessTask implements Runnable {
        private String parentThreadName;

        public BusinessTask(String parentThreadName) {
            this.parentThreadName = parentThreadName;
        }

        @Override
        public void run() {
            //如果与上面的能对应上来,则说明正确,否则失败
            System.out.println("parentThreadName:" + parentThreadName + ":" + requestIdThreadLocal.get() + "  " + Thread.currentThread().getName());
        }
    }
}
