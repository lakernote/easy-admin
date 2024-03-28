package com.laker.admin.module.task.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.task.core.processor.EasyTaskProcessor;
import com.laker.admin.module.task.entity.SysTask;
import com.laker.admin.module.task.service.ISysTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author laker
 */
@Api(tags = "定时任务-任务管理")
@ApiSupport(order = 20)
@RestController
@RequestMapping("/sys/task")
@Slf4j
public class TaskManagerController {

    @Autowired
    private EasyTaskProcessor easyTaskProcessor;

    @Autowired
    ISysTaskService sysTaskService;

    @GetMapping
    @ApiOperation(value = "任务定义分页查询")
    public PageResponse<List<SysTask>> pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                               @RequestParam(required = false, defaultValue = "10") long limit) {
        Page<SysTask> roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<SysTask> queryWrapper = new LambdaQueryWrapper<>();
        Page<SysTask> pageList = sysTaskService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }


    @PostMapping
    @ApiOperation("更新任务")
    @SaCheckPermission("task.update")
    public Response<Void> update(@RequestBody SysTask sysTask) {
        sysTaskService.saveOrUpdate(sysTask);
        return Response.ok();
    }

    @GetMapping("/{id}")
    public Response<SysTask> get(@PathVariable Long id) {
        return Response.ok(sysTaskService.getById(id));
    }

    @GetMapping("/start")
    @ApiOperation("开始任务")
    @SaCheckPermission("task.start")
    public Response<Void> start(String taskCode) {
        easyTaskProcessor.startJob(taskCode);
        return Response.ok();
    }

    @GetMapping("/stop")
    @ApiOperation("暂停任务")
    public Response<Void> stopCron(String taskCode) {
        easyTaskProcessor.stopJob(taskCode);
        return Response.ok();
    }


}
