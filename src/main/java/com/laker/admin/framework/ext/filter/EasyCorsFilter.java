package com.laker.admin.framework.ext.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @see org.springframework.web.filter.CorsFilter
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "EasyCorsFilter", urlPatterns = "/*")
@Slf4j
public class EasyCorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isValid = this.processRequest(request, response);
        if (!isValid || CorsUtils.isPreFlightRequest(request)) {
            return;
        }
        filterChain.doFilter(request, response);
    }


    private boolean processRequest(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {

        Collection<String> varyHeaders = response.getHeaders(HttpHeaders.VARY);
        if (!varyHeaders.contains(HttpHeaders.ORIGIN)) {
            response.addHeader(HttpHeaders.VARY, HttpHeaders.ORIGIN);
        }
        if (!varyHeaders.contains(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD)) {
            response.addHeader(HttpHeaders.VARY, HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD);
        }
        if (!varyHeaders.contains(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS)) {
            response.addHeader(HttpHeaders.VARY, HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
        }

        if (!CorsUtils.isCorsRequest(request)) {
            return true;
        }

        if (response.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN) != null) {
            logger.trace("Skip: response already contains \"Access-Control-Allow-Origin\"");
            return true;
        }

        boolean preFlightRequest = CorsUtils.isPreFlightRequest(request);

        return handleInternal(new ServletServerHttpRequest(request), new ServletServerHttpResponse(response), preFlightRequest);
    }

    protected boolean handleInternal(ServerHttpRequest request, ServerHttpResponse response
            , boolean preFlightRequest) throws IOException {

        String requestOrigin = request.getHeaders().getOrigin();
        HttpHeaders responseHeaders = response.getHeaders();
        List<String> requestHeaders = getHeadersToUse(request, preFlightRequest);
        HttpMethod requestMethod = getMethodToUse(request, preFlightRequest);
        responseHeaders.setAccessControlAllowOrigin(requestOrigin);
        responseHeaders.setAccessControlAllowCredentials(true);
        if (preFlightRequest) {
            responseHeaders.setAccessControlAllowMethods(Collections.singletonList(requestMethod));
            responseHeaders.setAccessControlAllowHeaders(requestHeaders);
//            responseHeaders.setAccessControlExposeHeaders(config.getExposedHeaders());
            responseHeaders.setAccessControlMaxAge(3600);
        }
        response.flush();
        return true;
    }

    private HttpMethod getMethodToUse(ServerHttpRequest request, boolean isPreFlight) {
        return (isPreFlight ? request.getHeaders().getAccessControlRequestMethod() : request.getMethod());
    }

    private List<String> getHeadersToUse(ServerHttpRequest request, boolean isPreFlight) {
        HttpHeaders headers = request.getHeaders();
        return (isPreFlight ? headers.getAccessControlRequestHeaders() : new ArrayList<>(headers.keySet()));
    }
}