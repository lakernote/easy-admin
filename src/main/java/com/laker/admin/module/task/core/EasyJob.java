package com.laker.admin.module.task.core;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * <pre>
 * EasyJob注解
 * 用于标记任务类，支持分布式任务调度
 * 任务分类：
 *  1.简单周期性任务 支持cron表达式、固定速率、固定延迟(参考@Scheduled) 比如定时清理系统日志、定时生成报表等。
 *  2.分片任务 支持按照数据范围、类型、时间等进行分片，每个节点执行一部分数据，从而提高任务的执行效率
 *  3.子任务 支持任务之间的依赖关系，可以定义子任务。当父任务执行成功后，会自动触发子任务的执行，适合处理具有先后顺序的业务流程。
 * </pre>
 *
 * @author laker
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
//让使用 @EasyJob 注解标记的类也能被 Spring 框架识别为组件。也就是说，只要某个类使用了 @EasyJob 注解，Spring 就会把这个类当作一个组件来处理，会自动创建该类的实例并将其纳入 Spring 的管理范围。
public @interface EasyJob {

    /**
     * 任务编码，必须唯一，用于标识每个任务
     */
    String taskCode();

    /**
     * 任务名称，用于描述任务的功能
     */
    String taskName();

    /**
     * 任务超时时间(单位: 秒)
     */
    long timeout() default 60;

    @AliasFor(annotation = Component.class)
    String value() default "";

    /**
     * cron表达式，默认""，代表不使用 cron 表达式进行任务调度
     */
    String cron() default "";

    /**
     * 任务执行的固定速率，单位为秒，默认值为 -1，表示不使用固定速率调度
     */
    long fixedRate() default -1;

    /**
     * 任务执行的固定延迟，单位为秒，默认值为 -1，表示不使用固定延迟调度
     */
    long fixedDelay() default -1;


    /**
     * 分片总数，用于分片任务，默认值为 1，表示不进行分片
     */
    int shardTotal() default 1;

    /**
     * 父任务编码，用于子任务，指定该任务依赖的父任务编码，默认值为空字符串，表示无父任务
     */
    String parentTaskCode() default "";

}