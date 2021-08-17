
package com.laker.admin.framework.aop;

import java.lang.annotation.*;

/**
 * @author laker
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Metrics {

}
