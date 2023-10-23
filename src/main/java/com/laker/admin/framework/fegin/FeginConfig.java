package com.laker.admin.framework.fegin;

import feign.Client;
import feign.Feign;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableLakerFeignClients(basePackages = "com.laker.admin")
public class FeginConfig {
    @Bean
    @ConditionalOnProperty(name = "feign.hystrix.enabled", havingValue = "false", matchIfMissing = true)
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .options(new Request.Options(Duration.ofSeconds(2), Duration.ofSeconds(5), true));
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "feign.hystrix.enabled", havingValue = "true")
    public Feign.Builder feignHystrixBuilder() {
        return HystrixFeign
                .builder();
    }

    @Bean
    @ConditionalOnProperty(value = "feign.okhttp.enabled", matchIfMissing = true)
    public Client feignClient() {
        return new OkHttpClient();
    }

    @Bean
    public Encoder encoder() {
        return new JacksonEncoder();
    }

    @Bean
    public Decoder decoder() {
        return new JacksonDecoder();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        Logger logger = LoggerFactory.getLogger(ErrorDecoder.class);
        return (methodKey, response) -> {
            logger.error("接口调用异常:{} status:{}, request:{}", methodKey, response.status(), response.request().toString());
//            if (response.status() >= 400 && response.status() <= 499) {
//                return new HystrixBadRequestException(methodKey + response.status());
//            }
            return new ErrorDecoder.Default().decode(methodKey, response);
        };
    }

}
