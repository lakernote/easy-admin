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

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(value = Ordered.HIGHEST_PRECEDENCE + 100)
@Component
@WebFilter(filterName = "MDCFilter", urlPatterns = "/*")
@Slf4j
public class MDCFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {

            String keyTokenName = StpUtil.stpLogic.getTokenName();
            SaTokenConfig config = StpUtil.stpLogic.getConfig();
            String tokenValue = null;
            // 3. 尝试从header里读取
            if (config.getIsReadHead()) {
                tokenValue = httpServletRequest.getHeader(keyTokenName);
            }
            // 4. 尝试从cookie里读取
            if (tokenValue == null && config.getIsReadCookie()) {
                tokenValue = this.getCookieValue(httpServletRequest, keyTokenName);
            }

            // 5. 如果打开了前缀模式
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
            String traceId = httpServletRequest.getHeader(EasyAdminConstants.TRACE_ID);
            if (StrUtil.isBlank(traceId)) {
                traceId = IdUtil.simpleUUID();
            }
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
            Cookie[] var3 = cookies;
            int var4 = cookies.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Cookie cookie = var3[var5];
                if (cookie != null && name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
