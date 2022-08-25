package com.laker.admin.config;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.alibaba.druid.util.Utils;
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

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

/**
 * durid 监控页面配置 默认地址 localhost:8080/druid/login.html
 *
 * @author laker
 */
@Configuration
@Slf4j
public class DruidConfig {
    private static final String DEFAULT_ALLOW_IP = "127.0.0.1";

    /**
     * 解决druid 日志报错：discard long time none received connection:xxx
     */
    @PostConstruct
    public void setUsePingMethod() {
        System.setProperty("druid.mysql.usePingMethod", "false");
    }

    /**
     * 参见：DruidStatViewServletConfiguration
     * 黑白名单
     * 登录用户名-密码
     *
     * @param properties
     * @return
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
     * 参见：DruidWebStatFilterConfiguration
     * 跳过静态资源
     *
     * @param properties
     * @return
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

    // 参见 DruidFilterConfiguration
    @Bean
    public StatFilter statFilter() {
        return new StatFilter();
    }

    // 参见 DruidSpringAopConfiguration
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
     *
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "spring.datasource.druid.statViewServlet.enabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<Filter> removeDruidFilterRegistrationBean(DruidStatProperties properties) {
        // 获取web监控页面的参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // 提取common.js的配置路径
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replace("\\*", "js/common.js");
        final String filePath = "support/http/resources/js/common.js";
        // 创建filter进行过滤
        Filter filter = new Filter() {
            @Override
            public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
                // Do nothing
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                chain.doFilter(request, response);
                // 重置缓冲区，响应头不会被重置
                response.resetBuffer();
                // 获取common.js
                String text = Utils.readFromResource(filePath);
                // 正则替换banner, 除去底部的广告信息
                text = text.replaceAll("<a.*?banner\"></a><br/>", "");
                text = text.replaceAll("powered.*?shrek.wang</a>", "");
                response.getWriter().write(text);
            }

            @Override
            public void destroy() {
                // Do nothing
            }
        };
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(commonJsPattern);
        return registrationBean;
    }
}