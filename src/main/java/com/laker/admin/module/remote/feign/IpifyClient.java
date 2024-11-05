package com.laker.admin.module.remote.feign;

import com.laker.admin.module.remote.feign.configration.IpifyFeignConfig;
import com.laker.admin.module.remote.feign.dto.IpifyVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ipifyClient", url = "https://api64.ipify.org",
        configuration = IpifyFeignConfig.class)
public interface IpifyClient {

    @GetMapping("?format=json")
    IpifyVo getIpAddress();
}