package com.laker.admin.framework.ext.mvc;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

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
        String filter = webRequest.getParameter("filter");
        String sort = webRequest.getParameter("sort");
        builder.page(Integer.parseInt(CharSequenceUtil.isBlank(page) ? "1" : page))
                .size(Integer.parseInt(CharSequenceUtil.isBlank(pageSize) ? "10" : pageSize));
        QueryWrapper queryWrapper = new QueryWrapper<>();

        // 解析过滤条件
        if (StrUtil.isNotBlank(filter)) {
            String[] filterParams = filter.split(",");
            for (String filterParam : filterParams) {
                String[] _filter = filterParam.split("\\|");
                String field = _filter[0];
                String operation = _filter[1];
                String value = _filter[2];

                switch (operation) {
                    case "eq":
                        queryWrapper.eq(field, value);
                        break;
                    case "like":
                        queryWrapper.like(field, value);
                        break;
                    case "gt":
                        queryWrapper.gt(field, value);
                        break;
                    case "lt":
                        queryWrapper.lt(field, value);
                        break;
                    case "gte":
                        queryWrapper.ge(field, value);
                        break;
                    case "lte":
                        queryWrapper.le(field, value);
                        break;
                }
            }
        }

        // 解析排序条件
        if (!sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            for (String sortOrder : sortParams) {
                String[] _sort = sortOrder.split("\\|");
                String field = _sort[0];
                String direction = _sort[1];

                if ("asc".equalsIgnoreCase(direction)) {
                    queryWrapper.orderByAsc(field);
                } else {
                    queryWrapper.orderByDesc(field);
                }
            }
        }

        // 分页
        builder.queryWrapper(queryWrapper);
        return builder.build();
    }
}