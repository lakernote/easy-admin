package com.laker.admin.module.remote;

import com.laker.admin.module.remote.dto.IpifyVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ipifyClient", url = "https://api64.ipify.org")
public interface IpifyClient {

    @GetMapping("?format=json")
    IpifyVo getIpAddress();
}