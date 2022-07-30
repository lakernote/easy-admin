package com.laker.admin.framework.aop.trace;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LakerIgnoreTrace {

}
