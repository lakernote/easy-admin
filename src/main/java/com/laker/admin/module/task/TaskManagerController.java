package com.laker.admin.module.task;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.Response;
import com.laker.admin.module.task.core.CoreProcessor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "定时任务管理")
@ApiSupport(order = 1)
@RestController
@RequestMapping("/task-manager")
@Slf4j
public class TaskManagerController {

    @Autowired
    private CoreProcessor coreProcessor;

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response startCron() {
        return Response.ok(coreProcessor.listJob());
    }

    @GetMapping("/start")
    @ApiOperation("开始任务")
    public Response start(String taskCode) {
        coreProcessor.startJob(taskCode);
        return Response.ok();
    }


    @PutMapping("/update")
    @ApiOperation("更新任务")
    public Response update(String taskCode, String taskCron, String taskName) {
        coreProcessor.updateJob( taskCode,  taskCron,  taskName);
        return Response.ok();
    }

    @DeleteMapping("/stop")
    @ApiOperation("取消任务")
    public Response stopCron(String taskCode) {
        coreProcessor.stopJob(taskCode);
        return Response.ok();
    }


}