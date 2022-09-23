package com.laker.admin.module.sys.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // 告诉 Spring Boot 寻找一个主配置类（例如，一个带有的@SpringBootApplication）并使用它来启动 Spring 应用程序上下文
public class TestingWebApplicationTests {
    /**
     * 这个不会走 filter，只会走controller
     */
    @Autowired
    SysUserController sysUserController;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(sysUserController.getAll());
    }

}