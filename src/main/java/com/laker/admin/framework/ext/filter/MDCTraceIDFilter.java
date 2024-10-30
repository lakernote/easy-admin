package com.laker.admin.framework.ext.filter;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.dev33.satoken.util.SaTokenConsts;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.EasyAdminConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(value = Ordered.HIGHEST_PRECEDENCE + 100)
@Component
@WebFilter(filterName = "MDCTraceIDFilter", urlPatterns = "/*")
@Slf4j
public class MDCTraceIDFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 当前端没有传递 traceId 时，由后台生成
            String traceId = httpServletRequest.getHeader(EasyAdminConstants.TRACE_ID);
            if (StrUtil.isBlank(traceId)) {
                traceId = IdUtil.simpleUUID();
            }
            MDC.put(EasyAdminConstants.TRACE_ID, traceId);

            // 获取userID
            String keyTokenName = StpUtil.stpLogic.getTokenName();
            SaTokenConfig config = StpUtil.stpLogic.getConfigOrGlobal();
            String tokenValue = null;
            // 1. 尝试从header里读取
            if (config.getIsReadHeader()) {
                tokenValue = httpServletRequest.getHeader(keyTokenName);
            }
            // 2. 尝试从cookie里读取
            if (tokenValue == null && config.getIsReadCookie()) {
                tokenValue = this.getCookieValue(httpServletRequest, keyTokenName);
            }

            // 3. 如果打开了前缀模式
            String tokenPrefix = config.getTokenPrefix();
            if (!SaFoxUtil.isEmpty(tokenPrefix) && !SaFoxUtil.isEmpty(tokenValue)) {
                // 如果token以指定的前缀开头, 则裁剪掉它, 否则视为未提供token
                if (tokenValue.startsWith(tokenPrefix + SaTokenConsts.TOKEN_CONNECTOR_CHAT)) {
                    tokenValue = tokenValue.substring(tokenPrefix.length() + SaTokenConsts.TOKEN_CONNECTOR_CHAT.length());
                } else {
                    tokenValue = null;
                }
            }
            MDC.put(EasyAdminConstants.USER_ID, (String) StpUtil.getLoginIdByToken(tokenValue));
        } catch (Exception e) {
            log.error("", e);
        }

        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            MDC.clear();
        }
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null && name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
