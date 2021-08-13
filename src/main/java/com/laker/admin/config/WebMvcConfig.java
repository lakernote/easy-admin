package com.laker.admin.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }

    /**
     * 注册sa-token的拦截器，打开注解式鉴权功能 (如果您不需要此功能，可以删除此类)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/admin/**",
                        "/admin/login.html",
                        "/error",
                        "/api/v1/login",
                        "/captcha");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File file = new File("web");
        log.info(file.getAbsolutePath());
        registry.addResourceHandler("/admin/**")
                .addResourceLocations("file:" + file.getAbsolutePath() + "/admin/");
    }

}
