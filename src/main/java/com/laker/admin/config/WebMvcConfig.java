package com.laker.admin.config;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.laker.admin.framework.ext.interceptor.TraceAnnotationInterceptor;
import com.laker.admin.framework.ext.mvc.CurrentUser;
import com.laker.admin.framework.ext.mvc.PageRequestArgumentResolver;
import com.laker.admin.framework.ext.mvc.StringToEnumConvertFactory;
import com.laker.admin.framework.ext.satoken.EasySaInterceprot;
import com.laker.admin.framework.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
            "/swagger-resources/**",
            "/sys/auth/v1/login",
            "/favicon.ico",
            "/ureport/preview",
            "/ureport/res/**",
            "/captcha"};
    private static final String[] trace_exclude_path = {"/admin/**",
            "/admin/login.html",
            "/error",
            "/swagger-resources/**"};
    @Resource
    EasyConfig easyConfig;

    /**
     * 注册sa-token的拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址
        registry.addInterceptor(new EasySaInterceprot(handler ->
                        SaRouter.match("/**", r -> StpUtil.checkLogin()))
                        .isAnnotation(true))
                .addPathPatterns("/**")
                .excludePathPatterns(exclude_path);

        // 配置自定义Trace拦截器
        registry.addInterceptor(new TraceAnnotationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(trace_exclude_path);
    }

    /**
     * 注册 Sa-Token 全局过滤器 是过滤器层面的
     * 为了解决 ureport不是Controller而使用
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 拦截路由
                .addInclude("/ureport/**")
                // 放行路由
                .addExclude("/ureport/preview", "/ureport/res/**")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    if (StpUtil.getLoginIdAsLong() != 1) {
                        // 登录校验 -- 拦截所有路由
                        SaRouter.match("/**").check(() -> StpUtil.checkRole("admin"));
                        // 检查是否登录 是否有token
//                        SaRouter.match("/ureport/preview").check(StpUtil::checkLogin);
                    }
                })
                .setError(e ->
                        {
                            log.error(e.getMessage(), e);
                            return JSONUtil.toJsonStr(Response.error("401", "认证失败，无法访问系统资源"));
                        }
                );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源，自定义虚拟磁盘功能
        File web = new File("web");
        EasyConfig.Local local = easyConfig.getStorage().getLocal();
        if (local.isEnable()) {
            String path = local.getStoragePath();
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            log.info(file.getAbsolutePath());
            registry.addResourceHandler("/" + path + "/**")
                    .addResourceLocations("file:" + file.getAbsolutePath() + "/");
        }

        registry.addResourceHandler("/admin/**")
                .addResourceLocations("file:" + web.getAbsolutePath() + "/admin/");

    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        // 预先配置响应状态代码或视图以呈现响应主体。
//        // 这在不需要自定义控制器逻辑的情况下很有用
//        // @RequestMapping("/html1")
//        //    public String html1(){
//        //        return "index";
//        //    }
//        // ——例如呈现主页、执行简单的站点 URL 重定向、返回带有 HTML 内容的 404 状态、没有内容的 204 等等。
////        registry.addViewController("/").setViewName("login");
////        registry.addViewController("/login.html").setViewName("login");
//    }

//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        // 内容协商
//        // 设置默认的设置默认的ContentType
//        configurer.defaultContentType(MediaType.APPLICATION_JSON);
//    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 路由匹配规则
//        configurer.setUseTrailingSlashMatch(false);
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加自定义类型转换器
        // 比如将字符串转换为日期类型，可通过DateFormatter类来实现自动转换。
        // formatters和converters用于对日期格式进行转换，默认已注册了Number和Date类型的formatters，
        // 支持@NumberFormat和@DateTimeFormat注解，需要自定义formatters和converters可以实现addFormatters方法。
//        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
        // 添加请求参数枚举类型支持
        registry.addConverter(new StringToEnumConvertFactory());
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        // 配置异常解析器
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 添加自定义方法参数处理器
        // 添加请求参数的PageRequest对象自动填充
        argumentResolvers.add(new PageRequestArgumentResolver());
        // 添加请求参数 CurrentUser对象自动填充
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
