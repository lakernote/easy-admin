package com.laker.admin.module.task.core;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author laker
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface EasyJob {

    @AliasFor(annotation = Component.class)
    String value() default "";

    /**
     * cron表达式,默认"-",代表不执行
     */
    String cron() default "-";

    /**
     * 任务编码 必须唯一
     */
    String taskCode();

    /**
     * 任务名称
     */
    String taskName();

}
