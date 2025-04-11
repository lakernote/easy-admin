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
     *
     * @return 业务名称
     */
    String name();
}
