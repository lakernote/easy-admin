package com.laker.admin.config;

import com.laker.admin.framework.waf.WafFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.HashMap;
import java.util.Map;

/**
 * 防火墙过滤器配置
 *
 * @author laker
 */
@Configuration
public class EasyWafConfig {
    /**
     * 要在 cachefilter后边
     */
    @Bean
    public FilterRegistrationBean<WafFilter> xssFilterRegistration(EasyAdminConfig easyAdminConfig) {
        FilterRegistrationBean<WafFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new WafFilter());
        registration.addUrlPatterns("/*");
        registration.setName("wafFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", easyAdminConfig.getWaf().getExcludes());
        initParameters.put("xssEnabled", easyAdminConfig.getWaf().isXssEnabled() + "");
        initParameters.put("sqlEnabled", easyAdminConfig.getWaf().isSqlEnabled() + "");
        registration.setInitParameters(initParameters);
        return registration;
    }
}
