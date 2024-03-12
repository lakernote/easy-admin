package com.laker.admin.module.task.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.module.task.entity.SysTasklog;
import com.laker.admin.module.task.service.ISysTasklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-18
 */
@RestController
@RequestMapping("/sys/tasklog")
public class SysTasklogController {
    @Autowired
    ISysTasklogService sysTasklogService;

    @GetMapping
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit,
                                String taskCode) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<SysTasklog> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.eq(SysTasklog::getTaskCode, taskCode);
        queryWrapper.orderByDesc(SysTasklog::getStartTime);
        Page pageList = sysTasklogService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

}