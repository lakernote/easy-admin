package com.laker.admin.framework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laker.admin.framework.model.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EasyHttpResponseUtil {

    public static void json(HttpServletResponse response, Response<Void> check) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        // 防止json 中文乱码
        response.setContentType("application/json; charset=UTF-8");
        ObjectMapper objectMapper = SpringUtils.getBean(ObjectMapper.class);
        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(check));
        out.flush();
    }

}
