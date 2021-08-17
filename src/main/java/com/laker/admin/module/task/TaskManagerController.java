package com.laker.admin.module.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.PageResponse;
import com.laker.admin.framework.Response;
import com.laker.admin.module.task.core.CoreProcessor;
import com.laker.admin.module.task.entity.SysTask;
import com.laker.admin.module.task.service.ISysTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "定时任务管理")
@ApiSupport(order = 1)
@RestController
@RequestMapping("/sys/task")
@Slf4j
public class TaskManagerController {

    @Autowired
    private CoreProcessor coreProcessor;

    @Autowired
    ISysTaskService sysTaskService;

    @GetMapping
    @ApiOperation(value = "分页查询")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<SysTask> queryWrapper = new QueryWrapper().lambda();
        Page pageList = sysTaskService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }


    @PostMapping
    @ApiOperation("更新任务")
    public Response update(@RequestBody SysTask sysTask) {
        sysTaskService.saveOrUpdate(sysTask);
        return Response.ok();
    }

    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysTaskService.getById(id));
    }

    @GetMapping("/start")
    @ApiOperation("开始任务")
    public Response start(String taskCode) {
        coreProcessor.startJob(taskCode);
        return Response.ok();
    }

    @GetMapping("/stop")
    @ApiOperation("暂停任务")
    public Response stopCron(String taskCode) {
        coreProcessor.stopJob(taskCode);
        return Response.ok();
    }


}