package com.laker.admin.framework.aop.idempotent;

import java.lang.annotation.*;

/**
 * <pre>
 *     幂等注解
 *     1.幂等表
 *       Redis: 使用 String 类型存储，Key 是幂等 Key, Value 默认为 1。
 *       Mysql: 需要创建一张记录表。（过期的数据需要定时清理，也可以永久存储）
 *       CREATE TABLE `idempotent_record` (
 *          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
 *          `key` varchar(255) NOT NULL COMMENT '幂等键',
 *          `expireTime` timestamp NOT NULL COMMENT '过期时间',
 *          `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *           PRIMARY KEY (`id`),
 *           UNIQUE KEY `unique_key` (`key`)
 *       ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='幂等记录';
 *      2.token令牌
 *        为每次请求生成请求唯一键,服务端对每个唯一键进行生命周期管控。规定时间内只允许一次请求,非第一次请求都属于重复提交。
 *        但是前后端改造大,后端要给出单独生成token令牌接口,前端要在每次调用时候先获取token令牌。
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    /**
     * 幂等Key 支持 SpEL（Spring Expression Language）表达式 #param.id
     */
    String key();

    /**
     * 触发幂等失败逻辑时，返回的错误提示信息
     */
    String message() default "已经处理过，请勿重复提交！";

    /**
     * 设置防重令牌 Key 过期时间，单位秒，默认 1 小时
     */
    long expireTime() default 3600L;
}    