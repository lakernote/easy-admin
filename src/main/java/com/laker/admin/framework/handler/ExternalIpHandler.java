package com.laker.admin.framework.handler;

import com.laker.admin.module.remote.IpifyClient;
import com.laker.admin.module.remote.dto.IpifyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExternalIpHandler implements CommandLineRunner {
    @Autowired
    IpifyClient ipifyClient;

    @Override
    public void run(String... args) {
        IpifyVo address = ipifyClient.getIpAddress();
        System.out.println("External IP Address: " + address);
//        String user = reqresClient.getUser("2");
//        System.out.println("User: " + user);
//        String todo = easyTodoClient.getById(URI.create("https://jsonplaceholder.typicode.com/"), 1);
//        System.out.println(todo);
    }
}