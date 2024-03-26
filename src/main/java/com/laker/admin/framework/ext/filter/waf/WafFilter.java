package com.laker.admin.framework.ext.filter.waf;

import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.model.Response;
import com.laker.admin.framework.utils.EasyHttpResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        // 禁用浏览器内容嗅探
        response.setHeader("X-Content-Type-Options", "nosniff");
        // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
        response.setHeader("X-XSS-Protection", "1; mode=block");

        // 只处理 POST PUT DELETE
        if (!request.getMethod().equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        if (ServletFileUpload.isMultipartContent(request)) {
            Response<Void> check = MultipartRequestChecker.check(request);
            if (!check.getSuccess()) {
                EasyHttpResponseUtil.json(response, check);
                return;
            }
            chain.doFilter(request, response);
            return;
        }

        if (handle(request)) {
            try {
                //Request请求过滤
                chain.doFilter(new WafRequestWrapper(request, xssEnabled, sqlEnabled), servletResponse);
            } catch (Exception e) {
                log.error(" WafFilter exception , requestURL: " + request.getRequestURL(), e);
            }
        }
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
