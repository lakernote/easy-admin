
package com.laker.admin.framework.waf;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // 1.设置安全响应头
        setSecurityHeaders(res);
        // 2.处理xss请求
        if (handle(req)) {
            try {
                //Request请求过滤
                chain.doFilter(new WafRequestWrapper(req, xssEnabled, sqlEnabled), response);
            } catch (Exception e) {
                log.error(" WafFilter exception , requestURL: " + req.getRequestURL(), e);
            }
            return;
        }
        chain.doFilter(request, response);
    }

    private void setSecurityHeaders(HttpServletResponse response) {
        // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
        // 避免被iframe 点击劫持 造成ClickJacking
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        // 禁用浏览器内容嗅探
        // 防止浏览器嗅探文件类型MIME 类型嗅探（MIME sniffing）
        // 防止攻击者上传恶意脚本文件（如 .jpg 文件中隐藏 JavaScript 代码），然后利用浏览器自动嗅探并执行该脚本。
        response.setHeader("X-Content-Type-Options", "nosniff");
        // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
        // 防止XSS攻击 如果 URL 或输入框中有恶意 JavaScript 代码注入，浏览器会阻止页面加载，避免 XSS 攻击生效。
        // 现代浏览器已经默认移除了 X-XSS-Protection，推荐使用 Content Security Policy (CSP) 进行更强的防护。
        response.setHeader("X-XSS-Protection", "1; mode=block");
        // 控制哪些资源可以被加载，防止 XSS 和数据注入攻击。
        // 要所有内容均来自站点的同一个源（不包括其子域名）
        // 有助于检测和减轻某些类型的攻击 例如跨站脚本（XSS）数据注入攻击
        // 只允许加载同源（self）的资源（如 JS、CSS、图片等）。
//        response.setHeader("Content-Security-Policy", "default-src 'self'");
        // 强制浏览器在指定时间内（max-age）仅使用 HTTPS 访问网站，防止中间人攻击（MITM）。
//        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
        // 防止泄露服务器信息，减少攻击者的侦察范围。
        response.setHeader("Server", "");

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
        if (value == null || value.trim().isEmpty()) {
            //未配置默认 True
            return true;
        }
        return Boolean.parseBoolean(value);
    }
}
