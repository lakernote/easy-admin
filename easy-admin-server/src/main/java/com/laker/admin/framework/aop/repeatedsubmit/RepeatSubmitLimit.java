package com.laker.admin.framework.aop.repeatedsubmit;

import java.lang.annotation.*;

/**
 * @author laker
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RepeatSubmitLimit {
    /**
     * 业务key，例如下单业务 order
     */
    String businessKey();

    /**
     * 业务参数，用于做更细粒度锁，例如锁到具体 订单id #orderId
     */
    String businessParam() default "";

    /**
     * 是否用户隔离，默认启用
     */
    boolean userLimit() default true;

    /**
     * 锁时间 默认10s
     */
    int time() default 10;
}