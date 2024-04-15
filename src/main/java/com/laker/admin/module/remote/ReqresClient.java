package com.laker.admin.module.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "reqresClient", url = "${reqres.url:https://reqres.in}")
public interface ReqresClient {

    @GetMapping("/api/users/{id}")
    String getUser(@PathVariable("id") String id);

    @PostMapping("/api/users")
    String createUser(@RequestBody String user);

    @PutMapping("/api/users/{id}")
    String updateUser(@PathVariable("id") String id, @RequestBody String user);

    @DeleteMapping("/api/users/{id}")
    String deleteUser(@PathVariable("id") String id);
}