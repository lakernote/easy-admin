package com.laker.admin.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.laker.admin.framework.ext.interceptor.TraceAnnotationInterceptor;
import com.laker.admin.framework.ext.mvc.CurrentUser;
import com.laker.admin.framework.ext.mvc.PageRequestArgumentResolver;
import com.laker.admin.framework.ext.mvc.StringToEnumConvertFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.List;

/**
 * @author laker
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    private static final String[] exclude_path = {"/admin/**",
            "/admin/login.html",
            "/error",
            "/favicon.ico",
            "/swagger-resources/**"};
    @Autowired
    EasyConfig easyConfig;

    /**
     * 注册sa-token的拦截器，打开注解式鉴权功能 (如果您不需要此功能，可以删除此类)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址
        registry.addInterceptor(new SaInterceptor(handler -> {
                    SaRouter.match("/**", r -> StpUtil.checkLogin());
                    // 角色认证 -- 拦截以 admin 开头的路由，必须具备 admin 角色或者 super-admin 角色才可以通过认证
                    SaRouter.match("/admin/**", r -> StpUtil.checkRoleOr("admin", "super-admin"));
                }).isAnnotation(true)).addPathPatterns("/**")
                .excludePathPatterns(exclude_path);
        // 配置自定义拦截器
        registry.addInterceptor(new TraceAnnotationInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(exclude_path);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源，自定义虚拟磁盘功能
        File web = new File("easy-admin-client");
        String path = easyConfig.getOssFile().getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        log.info(file.getAbsolutePath());
        registry.addResourceHandler("/admin/**")
                .addResourceLocations("file:" + web.getAbsolutePath() + "/admin/");

        registry.addResourceHandler("/" + path + "/**")
                .addResourceLocations("file:" + file.getAbsolutePath() + "/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加自定义类型转换器
        // 比如将字符串转换为日期类型，可通过DateFormatter类来实现自动转换。
        // formatters和converters用于对日期格式进行转换，默认已注册了Number和Date类型的formatters，
        // 支持@NumberFormat和@DateTimeFormat注解，需要自定义formatters和converters可以实现addFormatters方法。
//        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
        registry.addConverter(new StringToEnumConvertFactory());
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        // 配置异常解析器
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 添加自定义方法参数处理器
        argumentResolvers.add(new PageRequestArgumentResolver());
        argumentResolvers.add(new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return CurrentUser.class.equals(parameter.getParameterType());
            }

            @Override
            public Object resolveArgument(
                    MethodParameter parameter,
                    ModelAndViewContainer mvContainer,
                    NativeWebRequest nativeWebRequest,
                    WebDataBinderFactory webDataBinderFactory) throws Exception {
                HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
                if (request == null) {
                    return null;
                }
                // CurrentUser  = from request
                return CurrentUser.builder().id(StpUtil.getLoginId().toString()).build();
            }
        });
    }
}