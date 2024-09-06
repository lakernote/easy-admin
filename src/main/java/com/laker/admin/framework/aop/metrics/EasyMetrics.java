
package com.laker.admin.framework.aop.metrics;

import java.lang.annotation.*;

/**
 * 自定义注解
 *
 * @author laker
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyMetrics {

}
