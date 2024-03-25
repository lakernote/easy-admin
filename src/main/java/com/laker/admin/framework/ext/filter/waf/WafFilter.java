
package com.laker.admin.framework.ext.filter.waf;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * web防火墙
 */
@Slf4j
public class WafFilter implements Filter {
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
        String excludesUrls = config.getInitParameter("excludes");
        excludes = StrUtil.split(excludesUrls, ',');
        xssEnabled = getParamConfig(config.getInitParameter("xssEnabled"));
        sqlEnabled = getParamConfig(config.getInitParameter("sqlEnabled"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
        response.setHeader("X-Frame-Options", "SAMEORIGIN");

        if (handle(request)) {
            try {
                //Request请求过滤
                chain.doFilter(new WafRequestWrapper(request, xssEnabled, sqlEnabled), servletResponse);
            } catch (Exception e) {
                log.error(" WafFilter exception , requestURL: " + request.getRequestURL(), e);
            }
            return;
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.warn(" WafFilter destroy .");
    }


    private boolean handle(HttpServletRequest request) {
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
