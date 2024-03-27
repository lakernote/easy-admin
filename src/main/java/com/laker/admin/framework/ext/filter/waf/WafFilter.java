package com.laker.admin.framework.ext.filter.waf;

import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.model.Response;
import com.laker.admin.framework.utils.EasyHttpResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * web防火墙
 */
@Slf4j
public class WafFilter implements Filter {
    private static final List<HttpMethod> ALLOW_METHODS = Arrays.asList(HttpMethod.POST,
            HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.PATCH);
    /**
     * 排除链接
     */
    public List<String> excludes = new ArrayList<>();
    /**
     * 开启XSS脚本过滤
     */
    private boolean xssEnabled = true;
    /**
     * 开启SQL注入过滤
     */
    private boolean sqlEnabled = true;

    @Override
    public void init(FilterConfig config) throws ServletException {
        excludes = StrUtil.split(config.getInitParameter("excludes"), ',');
        xssEnabled = getParamConfig(config.getInitParameter("xssEnabled"));
        sqlEnabled = getParamConfig(config.getInitParameter("sqlEnabled"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 设置安全响应头
        setSecurityHeaders(response);

        // 只处理 POST PUT DELETE PATCH
        if (!ALLOW_METHODS.contains(HttpMethod.resolve(request.getMethod()))) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 处理文件上传请求 脚本攻击检测
        if (ServletFileUpload.isMultipartContent(request)) {
            Response<Void> check = MultipartRequestChecker.check(request);
            if (!check.getSuccess()) {
                log.warn(" WafFilter exception , multipart requestURL: " + request.getRequestURL());
                EasyHttpResponseUtil.json(response, check);
                return;
            }
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 拦截请求  脚本攻击转义
        if (shouldHandleRequest(request)) {
            try {
                //Request请求过滤
                chain.doFilter(new WafRequestWrapper(request, xssEnabled, sqlEnabled), servletResponse);
                return;
            } catch (Exception e) {
                log.error(" WafFilter exception , requestURL: " + request.getRequestURL(), e);
            }
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.warn(" WafFilter destroy .");
    }

    private void setSecurityHeaders(HttpServletResponse response) {
        // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        // 禁用浏览器内容嗅探
        response.setHeader("X-Content-Type-Options", "nosniff");
        // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
        response.setHeader("X-XSS-Protection", "1; mode=block");
    }

    private boolean shouldHandleRequest(HttpServletRequest request) {
        if (!xssEnabled && !sqlEnabled) {
            return false;
        }

        if (excludes == null || excludes.isEmpty()) {
            return true;
        }
        String url = request.getServletPath();
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return false;
            }
        }
        return true;
    }

    private boolean getParamConfig(String value) {
        if (value == null || "".equals(value.trim())) {
            //未配置默认 True
            return true;
        }
        return Boolean.parseBoolean(value);
    }
}
