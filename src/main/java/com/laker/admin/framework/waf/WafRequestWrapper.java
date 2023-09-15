
package com.laker.admin.framework.waf;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.waf.attack.HTMLFilter;
import com.laker.admin.framework.waf.attack.SqlFilter;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Request请求过滤包装
 * <p>
 *
 * @author hubin
 * @since 2014-5-8
 */
public class WafRequestWrapper extends HttpServletRequestWrapper {

    private boolean filterXSS = true;

    private boolean filterSQL = true;


    public WafRequestWrapper(HttpServletRequest request, boolean filterXSS, boolean filterSQL) {
        super(request);
        this.filterXSS = filterXSS;
        this.filterSQL = filterSQL;
    }


    public WafRequestWrapper(HttpServletRequest request) {
        this(request, true, true);
    }


    /**
     * @param parameter 过滤参数
     * @return
     * @since 数组参数过滤
     */
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = filterParamString(values[i]);
        }

        return encodedValues;
    }

    @Override
    public Map getParameterMap() {
        Map<String, String[]> primary = super.getParameterMap();
        Map<String, String[]> result = new HashMap<String, String[]>(primary.size());
        for (Map.Entry<String, String[]> entry : primary.entrySet()) {
            result.put(entry.getKey(), filterEntryString(entry.getValue()));
        }
        return result;

    }


    /**
     * @param parameter 过滤参数
     * @return
     * @since 参数过滤
     */
    @Override
    public String getParameter(String parameter) {
        return filterParamString(super.getParameter(parameter));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 非json类型，直接返回
        if (!isJsonRequest()) {
            return super.getInputStream();
        }

        // 为空，直接返回
        String json = IoUtil.read(super.getInputStream(), "utf-8");
        if (StrUtil.isBlank(json)) {
            return super.getInputStream();
        }

        // xss过滤
        json = filterParamString(json).trim();
        System.out.println("web防火墙处理后的结果如下：");
        System.out.println(json);
        final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("utf-8"));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    /**
     * 是否是Json请求
     */
    private boolean isJsonRequest() {
        String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
        return MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(header)
                || MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(header);
    }

    /**
     * @param name 过滤内容
     * @return
     * @since 请求头过滤
     */
    @Override
    public String getHeader(String name) {
        return filterParamString(super.getHeader(name));
    }


    /**
     * @return
     * @since Cookie内容过滤
     */
    @Override
    public Cookie[] getCookies() {
        Cookie[] existingCookies = super.getCookies();
        if (existingCookies != null) {
            for (int i = 0; i < existingCookies.length; ++i) {
                Cookie cookie = existingCookies[i];
                cookie.setValue(filterParamString(cookie.getValue()));
            }
        }
        return existingCookies;
    }


    protected String[] filterEntryString(String[] rawValue) {
        for (int i = 0; i < rawValue.length; i++) {
            rawValue[i] = filterParamString(rawValue[i]);
        }
        return rawValue;
    }

    /**
     * @param rawValue 待处理内容
     * @return
     * @since 过滤字符串内容
     */
    protected String filterParamString(String rawValue) {
        if (rawValue == null) {
            return null;
        }
        String tmpStr = rawValue;
        if (this.filterXSS) {
            tmpStr = SqlFilter.strip(rawValue);
        }
        if (this.filterSQL) {
            tmpStr = HTMLFilter.htmlSpecialChars(tmpStr);
        }
        return tmpStr;
    }
}
