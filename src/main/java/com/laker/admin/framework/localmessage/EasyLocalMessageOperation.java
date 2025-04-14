package com.laker.admin.framework.localmessage;


import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) // 运行时注解
@Documented // 用于生成文档
@Component
public @interface EasyLocalMessageOperation {

    /**
     * 业务名称
     */
    String name();

    /**
     * 业务描述
     */
    String description() default "";

    /**
     * 最大重试次数
     */
    int maxRetryCount() default 3;

}
