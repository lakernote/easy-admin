package com.laker.admin.framework.handler;

import com.laker.admin.module.remote.EasyTodoClient;
import com.laker.admin.module.remote.IpifyClient;
import com.laker.admin.module.remote.dto.IpifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URI;

@Slf4j
@Component
public class ExternalIpHandler implements CommandLineRunner {
    @Autowired
    IpifyClient ipifyClient;
    @Autowired
    EasyTodoClient easyTodoClient;

    @Override
    public void run(String... args) {
        IpifyVo address = ipifyClient.getIpAddress();
        log.info("External IP Address: {}", address);
        String todo = easyTodoClient.getById(URI.create("https://jsonplaceholder.typicode.com/"), 1);
        log.info("Todo: {}", todo);
    }
}