package com.laker.admin.framework.aop.trace;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author laker
 */
@Slf4j(topic = "trace")
public class TraceContext {
    private static ThreadLocal<Trace> traceThreadLocal = new ThreadLocal<>();

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


    public static void setTrace(Trace trace) {
        traceThreadLocal.set(trace);
    }

    public static void clear() {
        traceThreadLocal.remove();
    }

    public static Trace getTrace() {
        return traceThreadLocal.get();
    }
}
