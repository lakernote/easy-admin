package com.laker.admin.module.task;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.task.core.CoreProcessor;
import com.laker.admin.module.task.entity.SysTask;
import com.laker.admin.module.task.service.ISysTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<SysTask> queryWrapper = new QueryWrapper().lambda();
        Page pageList = sysTaskService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }


    @PostMapping
    @SaCheckPermission("task.update")
    public Response update(@RequestBody SysTask sysTask) {
        sysTaskService.saveOrUpdate(sysTask);
        return Response.ok();
    }

    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysTaskService.getById(id));
    }

    @GetMapping("/start")
    @SaCheckPermission("task.start")
    public Response start(String taskCode) {
        coreProcessor.startJob(taskCode);
        return Response.ok();
    }

    @GetMapping("/stop")
    public Response stopCron(String taskCode) {
        coreProcessor.stopJob(taskCode);
        return Response.ok();
    }


}