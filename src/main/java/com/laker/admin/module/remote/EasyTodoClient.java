package com.laker.admin.module.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;

@FeignClient(name = "todoClient", url = "EMPTY")
public interface EasyTodoClient {

    /**
     * 动态endpoint示例
     */
    @GetMapping(value = "/todos/{id}")
    String getById(URI endpoint, @PathVariable(value = "id") Integer id);
}