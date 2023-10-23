package com.laker.admin;

import com.laker.admin.framework.fegin.EnableLakerFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author laker
 */
@SpringBootApplication
@EnableLakerFeignClients(basePackages = "com.laker.admin")
public class EasyAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyAdminApplication.class, args);
        System.out.println("Gitee：https://gitee.com/lakernote/easy-admin");
        System.out.println("GitHub：https://github.com/lakernote/easy-admin");
    }

}
