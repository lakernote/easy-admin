package com.laker.admin.config;

import com.laker.admin.framework.waf.WafFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class WafConfig {
    @Autowired
    LakerConfig lakerConfig;

    /**
     * 要在 cachefilter后边
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new WafFilter());
        registration.addUrlPatterns("/*");
        registration.setName("wafFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", lakerConfig.getWaf().getExcludes());
        initParameters.put("xssEnabled", lakerConfig.getWaf().isXssEnabled() + "");
        initParameters.put("sqlEnabled", lakerConfig.getWaf().isSqlEnabled() + "");
        registration.setInitParameters(initParameters);
        return registration;
    }
}
