package com.laker.admin.framework.aop.trace;

import java.lang.annotation.*;

/**
 * @author laker
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyTrace {
    SpanType spanType() default SpanType.Others;
}
