package com.laker.admin.config.druid;

import com.alibaba.druid.util.Utils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * 去除底部广告
 *
 * @author laker
 */
@Slf4j
public class EasyDruidFilter implements Filter {

    final String filePath = "support/http/resources/js/common.js";

    // 创建filter进行过滤
    @Override
    public void init(javax.servlet.FilterConfig filterConfig) {
        // Do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);
        // 重置缓冲区，响应头不会被重置
        response.resetBuffer();
        // 获取common.js
        String text = Utils.readFromResource(filePath);
        // 正则替换banner, 除去底部的广告信息
        text = text.replaceAll("<a.*?banner\"></a><br/>", "");
        text = text.replaceAll("powered.*?shrek.wang</a>", "");
        response.getWriter().write(text);
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
