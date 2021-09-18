package com.laker.admin.framework.handler;

import cn.hutool.system.SystemUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OpenBrowserHandler implements CommandLineRunner {
    @Value("${server.port}")
    private int serverPort;

    @Override
    public void run(String... args) {
        try {
            //可以指定自己的路径
            if (SystemUtil.getOsInfo().isWindows()) {
                Runtime.getRuntime().exec("cmd  /c  start   http://localhost:" + serverPort + "/admin/login.html");
            } else {
                String name = SystemUtil.getOsInfo().getName();
                System.out.println("==================================================注意====================================================");
                System.out.println("当前操作系统为：" + name + ",不支持自动打开浏览器，请自行在浏览器访问：" + "http://localhost:" + serverPort + "/admin");
                System.out.println("==================================================注意====================================================");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}