package com.laker.admin.module.task;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.task.core.processor.EasyTaskProcessor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laker
 */
@Api(tags = "定时任务-任务监控")
@ApiSupport(order = 22)
@RestController
@RequestMapping("/task-manager-monitor")
@Slf4j
public class TaskManagerMonitorController {

    @Autowired
    private EasyTaskProcessor easyTaskProcessor;

    @GetMapping("/jvm-task-list")
    @ApiOperation("列表")
    public Response startCron() {
        return Response.ok(easyTaskProcessor.runningTaskList());
    }

}
