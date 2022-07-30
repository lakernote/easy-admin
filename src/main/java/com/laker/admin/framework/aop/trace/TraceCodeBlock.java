package com.laker.admin.framework.aop.trace;

import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * @author laker
 */
public class TraceCodeBlock {
    /**
     * 有返回值
     *
     * @param spanName
     * @param supplier
     * @param <T>
     * @return
     */
    public static <T> T trace(String spanName, Supplier<T> supplier) {
        try {
            TraceContext.addSpan(spanName);
            return supplier.get();
        } finally {
            TraceContext.stopSpan(200);
        }
    }

    /**
     * 无返回值调用
     */
    public static void trace(String spanName, IntConsumer function) {

        try {
            TraceContext.addSpan(spanName);
            function.accept(0);
        } finally {
            TraceContext.stopSpan(200);
        }
    }
}
