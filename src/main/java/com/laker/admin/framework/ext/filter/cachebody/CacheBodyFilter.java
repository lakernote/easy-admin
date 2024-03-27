package com.laker.admin.framework.ext.filter.cachebody;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author laker
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "CacheBodyFilter", urlPatterns = "/*")
public class CacheBodyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 不能拦截 application/x-www-form-urlencoded 和 multipart/form-data请求，
        // 否则会出现参数丢失，因为都是从http body中的 它们2个只能从流读取一次，后面解析的时候会出现问题。
        if (StrUtil.contains(httpServletRequest.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            CachedBodyHttpServletRequestWrapper cachedBodyHttpServletRequest = new CachedBodyHttpServletRequestWrapper(httpServletRequest);
            filterChain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
