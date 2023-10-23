package com.laker.admin.framework.fegin;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(LakerFeignClientsRegistrar.class)
public @interface EnableLakerFeignClients {
    /**
     * Base packages to scan for annotated components.
     * @return base packages
     */
    String[] basePackages() default {};
}