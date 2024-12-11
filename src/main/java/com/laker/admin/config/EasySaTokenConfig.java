package com.laker.admin.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.dao.SaTokenDaoRedisJackson;
import com.laker.admin.framework.redis.EasyRedisProperties;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 排除SaTokenDaoRedisJackson自动配置类
@ImportAutoConfiguration(exclude = {SaTokenDaoRedisJackson.class})
@ConditionalOnBean(EasyRedisProperties.class)
public class EasySaTokenConfig {

    @Bean
    public SaTokenDao saTokenDaoRedisJackson() {
        return new SaTokenDaoRedisJackson();
    }
}
