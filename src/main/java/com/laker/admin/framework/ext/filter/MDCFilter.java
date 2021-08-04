package com.laker.admin.framework.ext.filter;

import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(value = Ordered.HIGHEST_PRECEDENCE + 100)
@Component
@WebFilter(filterName = "MDCFilter", urlPatterns = "/*")
public class MDCFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            MDC.put("userId", "laker");
            MDC.put("requestId", IdUtil.fastUUID());
        } catch (Exception e) {
            //
        }

        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            MDC.clear();
        }
    }
}
