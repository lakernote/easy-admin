package com.laker.admin.framework.aop.trace;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Comparator;
import java.util.List;

@Slf4j(topic = "tracing")
public class TraceContext {
    private static final String BAR = "+";
    private static ThreadLocal<Trace> traceThreadLocal = new ThreadLocal<>();

    public static void addSpan(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = ((MethodSignature) pjp.getSignature());
        String className = methodSignature.getMethod().getDeclaringClass().getName();
        String methodName = methodSignature.getMethod().getName();
        addSpan(className + "." + methodName);
    }

    public static void addSpan(String spanName) {

        Trace trace = null;
        if (null == traceThreadLocal.get()) {
            trace = new Trace();
            traceThreadLocal.set(trace);
        } else {
            trace = traceThreadLocal.get();
        }
        Span span = new Span();
        span.setId(spanName);
        span.setStartTime(System.currentTimeMillis());
        trace.addSpan(span);
    }


    public static void stopSpan(long time) {
        Trace trace = traceThreadLocal.get();
        Span current = trace.current();
        current.setEndTime(System.currentTimeMillis());
        current.setCost(current.getEndTime() - current.getStartTime());
        if (trace.stopSpan(current)) {
            if (current.getCost() > time) {
                logSpan(trace.getSpans(), StringUtils.SPACE);
            }
            traceThreadLocal.remove();
        }
    }

    private static void logSpan(List<Span> spans, String append) {
        if (CollectionUtils.isEmpty(spans)) {
            return;
        }
        spans.sort(Comparator.comparing(Span::getOrder));
        for (Span span : spans) {
            log.warn("{}({})[{}]-{}", append + BAR, span.getOrder(), span.getCost(), span.getId());
            logSpan(span.getChilds(), append + BAR);
        }

    }
}
