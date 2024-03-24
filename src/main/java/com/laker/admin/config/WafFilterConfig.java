package com.laker.admin.config;

import com.laker.admin.framework.ext.filter.waf.WafFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * 防火墙过滤器配置
 *
 * @author laker
 */
@Configuration
public class WafFilterConfig {
    /**
     * 要在 Cachefilter后边
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<WafFilter> xssFilterRegistration(EasyConfig easyConfig) {
        FilterRegistrationBean<WafFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new WafFilter());
        registration.addUrlPatterns("/*");
        registration.setName("wafFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", easyConfig.getWaf().getExcludes());
        initParameters.put("xssEnabled", easyConfig.getWaf().isXssEnabled() + "");
        initParameters.put("sqlEnabled", easyConfig.getWaf().isSqlEnabled() + "");
        registration.setInitParameters(initParameters);
        return registration;
    }
}
