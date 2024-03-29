package com.laker.admin.config.druid;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidStatViewServletConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidWebStatFilterConfiguration;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * durid 监控页面配置 默认地址 localhost:8080/druid/login.html
 *
 * @author laker
 */
@Configuration
@ConditionalOnProperty(name = "druid.monitor", havingValue = "true", matchIfMissing = true)
@Slf4j
public class DruidConfig {
    private static final String DEFAULT_ALLOW_IP = "127.0.0.1";

    /**
     * 参见：
     *
     * @see DruidStatViewServletConfiguration
     * 黑白名单
     * 登录用户名-密码
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServletRegistrationBean(DruidStatProperties properties) {
        log.info("init statViewServlet Configuration ,properties:{}", JSONUtil.toJsonPrettyStr(properties));
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>();
        registrationBean.setServlet(new StatViewServlet());
        registrationBean.addUrlMappings(config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*");
        if (config.getAllow() != null) {
            registrationBean.addInitParameter("allow", config.getAllow());
        } else {
            registrationBean.addInitParameter("allow", DEFAULT_ALLOW_IP);
        }
        if (config.getDeny() != null) {
            registrationBean.addInitParameter("deny", config.getDeny());
        }
        if (config.getLoginUsername() != null) {
            registrationBean.addInitParameter("loginUsername", config.getLoginUsername());
        } else {
            // 添加默认用户名 laker
            registrationBean.addInitParameter("loginUsername", "laker");
        }
        if (config.getLoginPassword() != null) {
            registrationBean.addInitParameter("loginPassword", config.getLoginPassword());
        } else {
            // 添加默认密码 laker
            registrationBean.addInitParameter("loginPassword", "laker");
        }
        if (config.getResetEnable() != null) {
            registrationBean.addInitParameter("resetEnable", config.getResetEnable());
        }
        return registrationBean;
    }

    /**
     * 参见：
     *
     * @see DruidWebStatFilterConfiguration
     * 跳过静态资源
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilterRegistrationBean(DruidStatProperties properties) {
        log.info("init webStatFilter Configuration ,properties:{}", JSONUtil.toJsonPrettyStr(properties));
        DruidStatProperties.WebStatFilter config = properties.getWebStatFilter();
        FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<>();
        WebStatFilter filter = new WebStatFilter();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(config.getUrlPattern() != null ? config.getUrlPattern() : "/*");
        // 跳过静态资源
        registrationBean.addInitParameter("exclusions", config.getExclusions() != null ? config.getExclusions() : "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        if (config.getSessionStatEnable() != null) {
            registrationBean.addInitParameter("sessionStatEnable", config.getSessionStatEnable());
        }
        if (config.getSessionStatMaxCount() != null) {
            registrationBean.addInitParameter("sessionStatMaxCount", config.getSessionStatMaxCount());
        }
        if (config.getPrincipalSessionName() != null) {
            registrationBean.addInitParameter("principalSessionName", config.getPrincipalSessionName());
        }
        if (config.getPrincipalCookieName() != null) {
            registrationBean.addInitParameter("principalCookieName", config.getPrincipalCookieName());
        }
        if (config.getProfileEnable() != null) {
            registrationBean.addInitParameter("profileEnable", config.getProfileEnable());
        }
        return registrationBean;
    }

    /**
     * 参见 DruidFilterConfiguration
     */
    @Bean
    public StatFilter statFilter() {
        return new StatFilter();
    }

    /**
     * 参见 DruidSpringAopConfiguration
     */
    @Bean
    public Advice advice() {
        return new DruidStatInterceptor();
    }

    @Bean
    public Advisor advisor(DruidStatProperties properties) {
        // 最好确定到 controller service dao
        return new RegexpMethodPointcutAdvisor(new String[]{"com.laker.map.resource.*",}, advice());
    }

    @Bean
    @ConditionalOnProperty(name = "spring.aop.auto", havingValue = "false")
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


    /**
     * 去除底部广告
     */
    @Bean
    @ConditionalOnProperty(name = "spring.datasource.druid.statViewServlet.enabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<Filter> removeDruidFilterRegistrationBean(DruidStatProperties properties) {
        // 获取web监控页面的参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // 提取common.js的配置路径
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replace("\\*", "js/common.js");
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new EasyDruidFilter());
        registrationBean.addUrlPatterns(commonJsPattern);
        return registrationBean;
    }
}