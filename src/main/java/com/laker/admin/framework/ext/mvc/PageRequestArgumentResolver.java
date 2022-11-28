package com.laker.admin.framework.ext.mvc;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageRequestArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return PageRequest.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {
        PageRequest.PageRequestBuilder builder = PageRequest.builder();
        String page = webRequest.getParameter("page");
        String pageSize = webRequest.getParameter("size");

        String[] directionParameter = webRequest.getParameterValues("sort");
        List<PageRequest.Order> allOrders = new ArrayList<>();
        builder.page(Integer.valueOf(page))
                .size(Integer.valueOf(pageSize))
                .orders(allOrders);
        if (ArrayUtil.isNotEmpty(directionParameter)) {
            for (String part : directionParameter) {

                if (part == null) {
                    continue;
                }
                String[] elements = Arrays.stream(part.split(",")) //
                        .toArray(String[]::new);
                allOrders.add(PageRequest.Order.builder()
                        .property(elements[0])
                        .direction(elements.length > 1 ? elements[1] : "asc")
                        .build());
            }
        }
        return builder.build();
    }
}