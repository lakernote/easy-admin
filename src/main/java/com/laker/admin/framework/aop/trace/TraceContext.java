package com.laker.admin.framework.aop.trace;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Comparator;
import java.util.List;

/**
 * @author laker
 */
@Slf4j(topic = "trace")
public class TraceContext {
    private static final String BAR = "+";
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
        Span span = new Span();
        span.setId(spanName);
        span.setSpanType(spanType);
        span.setStartTime(System.currentTimeMillis());
        trace.addSpan(span);
    }


    public static void stopSpan(long time) {
        Trace trace = traceThreadLocal.get();
        Span current = trace.current();
        current.setEndTime(System.currentTimeMillis());
        current.setCost(current.getEndTime() - current.getStartTime());
        if (trace.stopSpan()) {
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
        spans.stream().filter(span -> span.getLevel() != 0).max(Comparator.comparing(Span::getCost)).ifPresent(span -> span.setMax(true));
        for (Span span : spans) {
            log.warn("{}{}{}ms{}:[{}]-{}", append + BAR, span.isMax() ? "【" : "[", span.getCost(), span.isMax() ? "】" : "]", span.getSpanType(), span.getId());
            logSpan(span.getChilds(), append + BAR);
        }

    }
}
