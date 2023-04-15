//package com.laker.admin.framework.ext.thread;
//
//import com.laker.admin.framework.aop.trace.Trace;
//import com.laker.admin.framework.aop.trace.TraceContext;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.concurrent.Future;
//
///**
// * 自定义扩展线程池用于捕获执行中异常，防止异常被吞 + 解决MDC参数问题。
// *
// * @author laker
// */
//@Slf4j
//public class TraceDefaultThreadPoolExecutor extends EasyAdminThreadPoolExecutor {
//    /**
//     * 简易线程池构造器
//     *
//     * @param poolSize  线程池大小,e.g:60
//     * @param queueSize 等待队列,e.g:1000
//     * @param prefix    线程名前缀,e.g:MCP-POOL
//     */
//    public TraceDefaultThreadPoolExecutor(int poolSize, int queueSize, String prefix) {
//        super(poolSize, queueSize, prefix);
//        // 非核心线程如果处于闲置状态超过该值，就会被销毁。如果设置allowCoreThreadTimeOut(true)，则会也作用于核心线程。
//        this.allowCoreThreadTimeOut(true);
//    }
//
//
//    @Override
//    public void execute(Runnable command) {
//        super.execute(wrap(command));
//    }
//
//    @Override
//    public Future<?> submit(Runnable task) {
//        return super.submit(wrap(task));
//    }
//
//
//    /**
//     * 封装任务，加入TraceId，无返回值
//     *
//     * @param runnable
//     * @return
//     */
//    private Runnable wrap(final Runnable runnable) {
//        Trace parentValue = TraceContext.trace();
//        return () -> {
//            if (parentValue == null) {
//                log.info("ThreadLocal GToadContext is initialized");
//                TraceContext.setTrace(new Trace());
//            } else {
//                // 引用
//                log.info("ThreadLocal childValue is initialized, parent: {}", parentValue);
////                Trace trace = new Trace();
////                trace.setDepth(parentValue.getDepth());
////                trace.setSpans(parentValue.getSpans());
////                trace.setTreeView(parentValue.getTreeView());
////                trace.setActiveSpanStack(parentValue.getActiveSpanStack());
//                TraceContext.setTrace(parentValue);
//            }
//            try {
//                runnable.run();
//            } finally {
//                TraceContext.clear();
//            }
//        };
//    }
//
//}