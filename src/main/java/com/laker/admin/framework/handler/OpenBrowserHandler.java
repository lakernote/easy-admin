package com.laker.admin.framework.handler;

import com.laker.admin.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OpenBrowserHandler implements CommandLineRunner {
    @Autowired
    AdminConfig adminConfig;
    @Value("${server.port}")
    private int serverPort;

    @Override
    public void run(String... args) {
        try {
            //可以指定自己的路径
            Runtime.getRuntime().exec("cmd  /c  start   http://localhost:" + serverPort + "/admin");
            System.out.println(adminConfig);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}