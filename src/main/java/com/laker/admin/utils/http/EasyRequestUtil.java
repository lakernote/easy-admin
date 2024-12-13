package com.laker.admin.utils.http;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.laker.admin.framework.EasyAdminConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * request 工具类
 */
@Slf4j
public class EasyRequestUtil {

    private EasyRequestUtil() {
        // Prevent instantiation
    }

    /**
     * 获取request对象
     */
    public static HttpServletRequest getRequest() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new IllegalStateException("requestAttributes is null");
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
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
     */
    public static UserAgent getRequestUserAgent() {
        HttpServletRequest request = getRequest();
        return UserAgentUtil.parse(request.getHeader("User-Agent"));

    }

    public static String getRemoteIP() {
        HttpServletRequest request = getRequest();
        final String unknown = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.isEmpty() || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.isEmpty() || unknown.equalsIgnoreCase(ip)) {
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
    public static Map<String, String> getHeaders() {
        HttpServletRequest request = getRequest();
        Map<String, String> map = new HashMap<>(32);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
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
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
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
        return CharSequenceUtil.EMPTY;
    }

    public static String getAllRequestInfo() {
        return "请求详情为：" + StrPool.CRLF +
                "RemoteAddress: " + getRemoteIP() + StrPool.CRLF +
                "Method: " + getRequest().getMethod() + StrPool.CRLF +
                "URI: " + getRequestURI() + StrPool.CRLF +
                "Headers: " + CharSequenceUtil.join(StrPool.CRLF + "         ", mapToList(getHeaders())) + StrPool.CRLF +
                "Body: " + getBody() + StrPool.CRLF;
    }

    private static List<String> mapToList(Map<String, String> parameters) {
        List<String> parametersList = new ArrayList<>();
        parameters.forEach((name, value) -> parametersList.add(name + "=" + value));
        return parametersList;
    }

    public static Long getWxUserId() {
        return (Long) getRequest().getAttribute(EasyAdminConstants.USER_ID);
    }
}