package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Dict;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.ext.mapper.ExtLogMapper;
import com.laker.admin.module.flow.process.SnakerEngineFacets;
import io.swagger.annotations.Api;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "系统-数据报表")
@ApiSupport(order = 5)
@RestController
@RequestMapping("/sys/statistics")
public class StatisticsController {
    @Autowired
    private SnakerEngineFacets snakerEngineFacets;
    @Autowired
    ExtLogMapper extLogMapper;


    @GetMapping("/console")
    public Response<Dict> get() {
        List<Task> activeTasks = snakerEngineFacets.getEngine().query()
                .getActiveTasks(new QueryFilter().setOperator(StpUtil.getLoginIdAsString()));
        List<HistoryTask> historyTasks = snakerEngineFacets.getEngine().query()
                .getHistoryTasks(new QueryFilter().setOperator(StpUtil.getLoginIdAsString()));
        List<String> sessionIds = StpUtil.searchTokenValue(null, -1, 1000, true);
        Dict res = Dict.create().set("todo", activeTasks.size())
                .set("done", historyTasks.size())
                .set("ip", extLogMapper.selectDistinctIp())
                .set("online", sessionIds.size());
        return Response.ok(res);
    }
}
