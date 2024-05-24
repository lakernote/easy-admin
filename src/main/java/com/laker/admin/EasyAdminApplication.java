package com.laker.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author laker
 */
@SpringBootApplication
public class EasyAdminApplication {
    public static void main(String[] args) {
        System.setProperty("druid.mysql.usePingMethod", "false");
        SpringApplication.run(EasyAdminApplication.class, args);
        System.out.println("Gitee: https://gitee.com/lakernote/easy-admin");
        System.out.println("GitHub: https://github.com/lakernote/easy-admin");
    }
}
