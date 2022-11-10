package com.laker.admin.framework.aop.trace;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author laker
 */
@Slf4j(topic = "trace")
public class TraceContext {
    private static ThreadLocal<Trace> traceThreadLocal = new ThreadLocal<Trace>();
    // 但是下面这个并没有解决 触发时机 只在创建线程问题，线程池复用有问题。
//    private static ThreadLocal<Trace> traceThreadLocal = new InheritableThreadLocal<Trace>() {
//        @Override
//        protected Trace initialValue() {
//            log.trace("ThreadLocal Trace is initialized");
//            return new Trace();
//        }
//
//        /**
//         * <pre>
//         *     默认是这个实现，是使用父对象引用，会导致线程不安全
//         *       protected T childValue(T parentValue) {
//         *         return parentValue;
//         *     }
//         * </pre>
//         * @param parentValue
//         * @return
//         */
//        @Override
//        protected Trace childValue(Trace parentValue) {
//            log.trace("ThreadLocal childValue is initialized, parent: {}", parentValue);
//            Trace trace = initialValue();
//            trace.setDepth(parentValue.getDepth());
//            trace.setSpans(parentValue.getSpans());
//            trace.setTreeView(parentValue.getTreeView());
//            trace.setActiveSpanStack(parentValue.getActiveSpanStack());
//            return trace;
//        }
//    };

    private TraceContext() {
        // do nothing
    }

    public static void addSpan(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = ((MethodSignature) pjp.getSignature());
        String className = methodSignature.getMethod().getDeclaringClass().getSimpleName();
        String methodName = methodSignature.getMethod().getName();
        addSpan(className + "." + methodName, TraceUtils.getSpanType(pjp));
    }

    public static void addSpan(String spanName) {
        addSpan(spanName, SpanType.Others);
    }

    public static void addSpan(String spanName, SpanType spanType) {
        Trace trace = null;
        if (null == traceThreadLocal.get()) {
            trace = new Trace();
            traceThreadLocal.set(trace);
        } else {
            trace = traceThreadLocal.get();
        }
        trace.addSpan(spanName, spanType);
    }

    public static void stopSpan() {
        stopSpan(1000);
    }

    public static void stopSpan(long time) {
        Trace trace = traceThreadLocal.get();
        if (trace.stopSpan(time)) {
            traceThreadLocal.remove();
        }
    }

    public static Trace trace() {
        return traceThreadLocal.get();
    }


    public static void setTrace(Trace trace) {
        traceThreadLocal.set(trace);
    }

    public static void clear() {
        traceThreadLocal.remove();
    }
}
