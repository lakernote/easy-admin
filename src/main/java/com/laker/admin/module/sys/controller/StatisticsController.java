package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Dict;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.ext.mapper.ExtLogMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "系统-数据报表")
@ApiSupport(order = 5)
@RestController
@RequestMapping("/sys/statistics")
public class StatisticsController {
    @Autowired
    ExtLogMapper extLogMapper;

    @GetMapping("/console")
    public Response<Dict> get() {
        List<String> sessionIds = StpUtil.searchTokenValue(null, -1, 1000, true);
        Dict res = Dict.create().set("todo", 1)
                .set("done", 1)
                .set("ip", extLogMapper.selectDistinctIp())
                .set("online", sessionIds.size());
        return Response.ok(res);
    }
}
