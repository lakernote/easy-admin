package com.laker.admin.framework.ext.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  ThreadLocal 子线程无法获取父线程属性 线程安全问题。
 *  结果：
 *  8569
 *  预期 10000
 * </pre>
 */
public class ThreadLocalDemoThreadSafe2 {
    /**
     * 模拟tomcat线程池
     */
    private static ExecutorService tomcatExecutors = Executors.newFixedThreadPool(10);
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
        // 模拟单例 controller
        ControlThread controlThread = new ControlThread();
        // 模拟 tomcat
        for (int i = 0; i < 100; ++i) {
            tomcatExecutors.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    controlThread.add();
                }
            });
        }
        TimeUnit.SECONDS.sleep(10);
        tomcatExecutors.shutdown();
        System.out.println("------------------------");
        System.out.println(controlThread.get());
    }

    /**
     * 模拟Control任务
     */
    static class ControlThread {
        private int i = 0;

        public int add() {
            return ++i;
        }

        public int get() {
            return i;
        }
    }

}
