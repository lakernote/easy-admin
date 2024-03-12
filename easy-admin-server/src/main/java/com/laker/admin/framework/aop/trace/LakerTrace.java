package com.laker.admin.framework.aop.trace;

import java.lang.annotation.*;

/**
 * @author laker
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LakerTrace {
    SpanType spanType() default SpanType.Others;
}
