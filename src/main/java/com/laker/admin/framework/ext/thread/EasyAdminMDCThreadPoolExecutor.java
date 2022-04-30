package com.laker.admin.framework.ext.thread;

import cn.hutool.core.util.IdUtil;
import com.laker.admin.framework.EasyAdminConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 自定义扩展线程池用于捕获执行中异常，防止异常被吞 + 解决MDC参数问题。
 *
 * @author laker
 */
@Slf4j
public class EasyAdminMDCThreadPoolExecutor extends EasyAdminThreadPoolExecutor {
    /**
     * 简易线程池构造器
     *
     * @param poolSize  线程池大小,e.g:60
     * @param queueSize 等待队列,e.g:1000
     * @param prefix    线程名前缀,e.g:MCP-POOL
     */
    public EasyAdminMDCThreadPoolExecutor(int poolSize, int queueSize, String prefix) {
        super(poolSize, poolSize, prefix);
        // 非核心线程如果处于闲置状态超过该值，就会被销毁。如果设置allowCoreThreadTimeOut(true)，则会也作用于核心线程。
        this.allowCoreThreadTimeOut(true);
    }


    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Callable task) {
        return super.submit(wrap(task, MDC.getCopyOfContextMap()));
    }


    /**
     * 封装任务，加入TraceId，无返回值
     *
     * @param runnable
     * @param threadContext
     * @return
     */
    private Runnable wrap(final Runnable runnable, final Map<String, String> threadContext) {
        return new Runnable() {
            @Override
            public void run() {
                if (threadContext == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(threadContext);
                }
                setTraceIdIfAbsent();
                try {
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            }
        };
    }

    /**
     * 封装任务，加入TraceId，有返回值
     *
     * @param callable
     * @param threadContext
     * @param <T>
     * @return
     */
    private <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> threadContext) {
        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                if (threadContext == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(threadContext);
                }
                setTraceIdIfAbsent();
                try {
                    return callable.call();
                } finally {
                    MDC.clear();
                }
            }
        };
    }

    /**
     * 如果traceId不存在，则设置一个随机的traceId
     * 这种场景主要用于后台定时任务类，没有前端生成traceID的情况。
     */
    private void setTraceIdIfAbsent() {
        if (MDC.get(EasyAdminConstants.TRACE_ID) == null) {
            MDC.put(EasyAdminConstants.TRACE_ID, IdUtil.simpleUUID());
        }
    }
}