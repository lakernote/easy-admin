package com.laker.admin.module.remote.feign;

import com.laker.admin.module.remote.feign.configration.IpifyFeignConfig;
import com.laker.admin.module.remote.feign.dto.IpifyVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 获取ip地址
 * 断路器 FeignCircuitBreaker
 * FeignCircuitBreakerTargeter
 * 只能走yaml配置文件
 * 如何设置了group模式，name为yaml中的 ipifyClient 示例
 * 如果想每个feign方法都有自己的配置，默认读的是 类名方法名
 * IpifyClientgetIpAddress 所以配置名称为 IpifyClient_getIpAddress
 * https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#spring-cloud-feign-circuitbreaker
 */
@FeignClient(name = "ipifyClient", url = "https://api64.ipify.org",
        configuration = IpifyFeignConfig.class, fallback = Fallback.class)
public interface IpifyClient {

    @GetMapping("?format=json")
    IpifyVo getIpAddress();
}