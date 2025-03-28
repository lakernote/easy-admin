package com.laker.admin.framework.aop.duplicate;

import java.lang.annotation.*;

/**
 * <pre>
 * 仅用于防止重复提交，不保证幂等性
 * 1.防止重复提交，是指用户在提交表单时，由于网络延迟、用户重复点击等原因，导致多次提交表单的情况。是短期内重复提交的问题。
 * 2.幂等性，是指同一个请求，无论调用多少次，结果都是一样的。是长期内重复提交的问题。
 * 3.防止重复提交，不保证幂等性。但是幂等性一定保证了防止重复提交。
 * </pre>
 *
 * @author laker
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyDuplicateRequestLimiter {
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
     * 防重复提交的超时时间，单位: 秒
     */
    int timeout() default 2;
}