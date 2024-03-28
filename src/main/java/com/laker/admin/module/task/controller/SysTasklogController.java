package com.laker.admin.module.task.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.module.task.entity.SysTasklog;
import com.laker.admin.module.task.service.ISysTasklogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-18
 */
@Api(tags = "定时任务-任务日志")
@ApiSupport(order = 22)
@RestController
@RequestMapping("/sys/tasklog")
public class SysTasklogController {
    @Autowired
    ISysTasklogService sysTasklogService;

    @GetMapping
    @ApiOperation(value = "分页查询")
    public PageResponse<List<SysTasklog>> pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                                  @RequestParam(required = false, defaultValue = "10") long limit,
                                                  String taskCode) {
        Page<SysTasklog> roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<SysTasklog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(taskCode), SysTasklog::getTaskCode, taskCode);
        queryWrapper.orderByDesc(SysTasklog::getStartTime);
        Page<SysTasklog> pageList = sysTasklogService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

}
