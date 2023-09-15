package com.laker.admin.module.sys.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * 启动一个真实的tomcat容器
 * WebEnvironment.DEFINED_PORT：用程序定义的端口
 * WebEnvironment.RANDOM_PORT：使用随机端口，配合@LocalServerPort注解使用
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingWebApplicationTests3 {
    @Autowired
    SysUserController sysUserController;
    @LocalServerPort
    int port;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(sysUserController);
        System.out.println("单元测试tomcat端口为：" + port);
    }

}