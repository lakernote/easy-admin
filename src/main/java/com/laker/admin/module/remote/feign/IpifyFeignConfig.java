package com.laker.admin.module.remote.feign.configration;

import cn.hutool.core.util.IdUtil;
import com.laker.admin.config.EasyFeignConfig;
import feign.Request;
import feign.RequestInterceptor;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import java.util.concurrent.TimeUnit;

/**
 * feign配置
 * 注意使用全局的还是这里定义的，以及一些定义规则
 *
 * @see EasyFeignConfig
 * 这里的配置会覆盖全局的配置
 * @see org.springframework.cloud.openfeign.FeignClientFactoryBean
 * 设置的原理 请查看 getTarget() 方法
 */
@Slf4j
public class IpifyFeignConfig {
    /**
     * 拦截器可以有多个
     * 这里会配合全局的拦截器 一起使用
     */
    @Order(1)
    @Bean
    RequestInterceptor ipifyTokenRequestInterceptor() {
        return requestTemplate -> {
            String simpleUUID = IdUtil.simpleUUID();
            log.warn("Ipify-token:{}", simpleUUID);
            requestTemplate.header("Ipify-token", simpleUUID);
        };
    }

    /**
     * 配置连接超时时间和读取超时时间,会覆盖全局的
     */
    @Bean
    Request.Options feign1Options() {
        return new Request.Options(10, TimeUnit.SECONDS, 12, TimeUnit.SECONDS, true);
    }

    /**
     * 会覆盖全局的，但是 bean名称必须是 feignRetryer，不能修改 擦。。。
     */
    @Bean
    Retryer feignRetryer() {
        //最大请求次数为3，初始间隔时间为100ms，下次间隔时间1.5倍递增，重试间最大间隔时间为1s，
        return new Retryer.Default(200, 1000, 2);
    }


    /**
     * 自定义错误解码器 会覆盖全局的
     */
    @Bean
    public ErrorDecoder feign1Error() {
        // ErrorDecoder的默认实现
        // 响应包含“Retry-After”标头时创建RetryableException实例。
        // 最常见的是，我们可以在 503 服务不可用响应中找到这个标头。
        final ErrorDecoder errorDecoder = new ErrorDecoder.Default();
        return (methodKey, response) -> {

            if (response.status() == 400) {
                log.error("请求xxx服务400参数错误,返回:{}", response.body());
            }

            if (response.status() == 404) {
                log.error("请求xxx服务404异常,返回:{}", response.body());
            }

            Exception defaultException = errorDecoder.decode(methodKey, response);
            if (defaultException instanceof RetryableException) {
                // Requirement 3: retry when Retry-After header is set
                // 默认的重试逻辑
                return defaultException;
            }

            // 扩展的重试逻辑
            if (HttpStatus.valueOf(response.status()).is5xxServerError()) {
                return new RetryableException(
                        response.status(),
                        defaultException.getMessage(),
                        response.request().httpMethod(),
                        defaultException,
                        (Long) null,
                        response.request());
            }

            // 默认处理
            return defaultException;
        };
    }

}