package com.laker.admin.framework.ext.filter;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.dev33.satoken.util.SaTokenConsts;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.EasyAdminConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author laker
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE + 100)
@Component
@WebFilter(filterName = "MDCFilter", urlPatterns = "/*")
@Slf4j
public class MDCFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {

            String keyTokenName = StpUtil.getStpLogic().getTokenName();
            SaTokenConfig config = StpUtil.getStpLogic().getConfigOrGlobal();
            String tokenValue = null;
            // 1. 尝试从header里读取
            if (config.getIsReadHeader()) {
                tokenValue = httpServletRequest.getHeader(keyTokenName);
            }
            // 2. 尝试从cookie里读取
            if (tokenValue == null && config.getIsReadCookie()) {
                tokenValue = this.getCookieValue(httpServletRequest, keyTokenName);
            }

            // 3. 如果打开了前缀模式 则裁剪掉它, 否则视为未提供token
            String tokenPrefix = config.getTokenPrefix();
            if (!SaFoxUtil.isEmpty(tokenPrefix) && !SaFoxUtil.isEmpty(tokenValue)) {
                // 如果token以指定的前缀开头, 则裁剪掉它, 否则视为未提供token
                if (tokenValue.startsWith(tokenPrefix + SaTokenConsts.TOKEN_CONNECTOR_CHAT)) {
                    tokenValue = tokenValue.substring(tokenPrefix.length() + SaTokenConsts.TOKEN_CONNECTOR_CHAT.length());
                } else {
                    tokenValue = null;
                }
            }
            // 设置MDC userId
            MDC.put(EasyAdminConstants.USER_ID, (String) StpUtil.getLoginIdByToken(tokenValue));
            // 如果请求头没有携带traceId，则由后端生成一个
            String traceId = httpServletRequest.getHeader(EasyAdminConstants.TRACE_ID);
            if (StrUtil.isBlank(traceId)) {
                traceId = IdUtil.simpleUUID();
            }
            // 设置MDC traceId
            MDC.put(EasyAdminConstants.TRACE_ID, traceId);
        } catch (Exception e) {
            //
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
