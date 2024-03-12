package com.laker.admin.framework.ext.thread;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import net.bull.javamelody.internal.common.LOG;

import java.util.concurrent.*;

/**
 * 自定义扩展线程池用于捕获执行中异常，防止异常被吞
 * <p>
 * 强烈建议使用EasyAdminMDCThreadPoolExecutor
 * </p>
 *
 * @author laker
 */
@Slf4j
public class EasyAdminThreadPoolExecutor extends ThreadPoolExecutor {
    /**
     * 简易线程池构造器
     *
     * @param poolSize  线程池大小,e.g:60
     * @param queueSize 等待队列,e.g:1000
     * @param prefix    线程名前缀,e.g:MCP-POOL
     */
    public EasyAdminThreadPoolExecutor(int poolSize, int queueSize, String prefix) {
        super(poolSize, poolSize, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize), ThreadUtil
                        .newNamedThreadFactory(prefix + "-", false),
                new EasyAdminRejectPolicy());
        // 非核心线程如果处于闲置状态超过该值，就会被销毁。如果设置allowCoreThreadTimeOut(true)，则会也作用于核心线程。
        this.allowCoreThreadTimeOut(true);
    }

    public EasyAdminThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
                                       long keepAliveTime, TimeUnit unit,
                                       BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory, new EasyAdminRejectPolicy());
        // 非核心线程如果处于闲置状态超过该值，就会被销毁。如果设置allowCoreThreadTimeOut(true)，则会也作用于核心线程。
        this.allowCoreThreadTimeOut(true);
    }


    @Override
    public void execute(Runnable command) {
        super.execute(command);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(task);
    }

    @Override
    public Future<?> submit(Callable task) {
        return super.submit(task);
    }

    /**
     * <pre>
     *
     * 这个实现什么也不做，但可以在子类中定制。
     * 注意：为了正确地嵌套多个重写，子类通常应该在该方法结束时调用super.beforeexecute。
     * 在给定线程中执行给定的可运行项之前调用的方法。
     * 此方法由将执行任务r的线程t调用，并可用于重新初始化线程局部变量或执行日志记录。
     *
     *
     * 如果beforeExecute抛出一个RuntimeException，那么任务将不被执行，并且afterExecute也不会被调用
     * 参数 t:t将运行任务r的线程
     * 参数 r:r将要执行的任务
     * </pre>
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.debug("beforeExecute-线程：{}，任务：{}", t, r);
    }

    /**
     * <pre>
     * XXX 这里虽然有兜底的抓取异常，但是也会导致线程的销毁重建，表现为 线程大小2 A-1,A-2 会一直递增 。。A-100
     * 所以最好还是在 POOL.execute（）中加入try catch包裹
     * 这个实现什么也不做，但可以在子类中定制。
     * 注意：为了正确地嵌套多个重写，子类通常应该在这个方法的开头调用super.afterexecute。
     * 在执行给定的可运行文件完成时调用的方法.
     *
     * 无论任务是从run中正常返回，还是抛出一个异常而返回，afterExecute都会被调用
     * 如果任务在完成后带有一个Error，那么就不会调用afterExecute
     *
     * 参数 r 如果是 submit提交的 任务，或者是FutureTask任务，则异常不在参数t中，而是如下方式获取
     * </pre>
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future) {
            try {
//                Object result = ((Future<?>) r).get();
                ((Future<?>) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null) {
            log.error("线程池中的任务执行异常！！！", t);
        }
        log.debug("afterExecute-任务：{}，异常：{}", r, t);
    }

    /**
     * <pre>
     * exec.shutdown();
     * 当执行器终止时调用的方法。默认实现什么也不做。
     * 注意：为了正确地嵌套多个重写，子类通常应该在这个方法中调用super.terminated。
     * 在线程池完成关闭时调用terminated
     * 也就是在所有任务都已经完成并且所有工作者线程也已经关闭后
     * ，terminated可以用来释放Executor在其生命周期里分配的各种资源，此外还可以执行发送通知、记录日志或者手机finalize统计等操作
     * </pre>
     */
    @Override
    protected void terminated() {
        super.terminated();
        LOG.debug("线程池关闭鸟！！");
    }
}