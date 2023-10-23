package com.laker.admin.framework.fegin;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LakerFeignClient {
    String url() default "";
}