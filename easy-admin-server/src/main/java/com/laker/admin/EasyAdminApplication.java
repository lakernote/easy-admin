package com.laker.admin;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author laker
 */
@SpringBootApplication
public class EasyAdminApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EasyAdminApplication.class, args);
        String port = run.getEnvironment().getProperty("server.port");
        System.out.println(StrUtil.format(
                """
                        =======================================
                        接口文档：http://localhost:{}/doc.html
                        =======================================
                        """, port));
    }
}
