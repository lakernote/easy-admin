package com.laker.admin.utils.http;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * request 工具类
 */
@Slf4j
public class HttpServletRequestUtil {

    /**
     * 获取request对象
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;

    }

    /**
     * 获取getRequestURI
     *
     * @return /sys/user/getInfo?id=1
     */
    public static String getRequestURI() {
        HttpServletRequest request = getRequest();
        String queryString = request.getQueryString();
        if (!StringUtils.hasText(queryString)) {
            return request.getRequestURI();
        }
        return request.getRequestURI() + "?" + queryString;

    }

    /**
     * 获取getRequestURI
     *
     * @return
     */
    public static UserAgent getRequestUserAgent() {
        HttpServletRequest request = getRequest();
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        return userAgent;

    }

    public static String getRemoteIP() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多次反向代理后会有多个IP值，第一个为真实IP。
        int index = ip.indexOf(',');
        if (index != -1) {
            ip = ip.substring(0, index);
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 取得请求头信息 name:value
     */
    public static Map getHeaders() {
        HttpServletRequest request = getRequest();
        Map<String, String> map = new HashMap<>(32);
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取请求体信息
     */
    public static String getBody() {
        HttpServletRequest request = getRequest();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            return StreamUtils.copyToString(inputStream, Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("HttpServletRequest.getBody", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("HttpServletRequest.getBody", e);
                }
            }
        }
        return StrUtil.EMPTY;
    }

    public static String getAllRequestInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("请求详情为：").append(StrUtil.CRLF);
        sb.append("RemoteAddress: ").append(getRemoteIP()).append(StrUtil.CRLF);
        sb.append("Method: ").append(getRequest().getMethod()).append(StrUtil.CRLF);
        sb.append("URI: ").append(getRequestURI()).append(StrUtil.CRLF);
        sb.append("Headers: ").append(StrUtil.join(StrUtil.CRLF + "         ", mapToList(getHeaders()))).append(StrUtil.CRLF);
        sb.append("Body: ").append(getBody()).append(StrUtil.CRLF);
        return sb.toString();
    }

    private static List mapToList(Map parameters) {
        List parametersList = new ArrayList();
        parameters.forEach((name, value) -> {
            parametersList.add(name + "=" + value);
        });
        return parametersList;
    }
}