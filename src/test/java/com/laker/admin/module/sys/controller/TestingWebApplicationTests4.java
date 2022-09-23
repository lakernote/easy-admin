package com.laker.admin.module.sys.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

/**
 * 启动一个真实的tomcat容器
 * WebEnvironment.DEFINED_PORT：用程序定义的端口
 * WebEnvironment.RANDOM_PORT：使用随机端口，配合@LocalServerPort注解使用
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestingWebApplicationTests4 {
    /**
     * 这个不会走 filter，只会走controller
     */
    @Autowired
    SysUserController sysUserController;

    /**
     * 这个会走 filter，会走controller
     */
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(sysUserController);
        Assert.assertTrue(this.restTemplate.getForObject("http://localhost:8080" + "/",
                String.class) != null);
    }

}