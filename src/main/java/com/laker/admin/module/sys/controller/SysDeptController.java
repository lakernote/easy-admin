package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.Response;
import com.laker.admin.framework.ResultTable;
import com.laker.admin.framework.ResultTree;
import com.laker.admin.framework.aop.Metrics;
import com.laker.admin.module.sys.entity.SysDept;
import com.laker.admin.module.sys.service.ISysDeptService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-11
 */
@RestController
@RequestMapping("/sys/dept")
@Metrics
public class SysDeptController {
    @Autowired
    ISysDeptService sysDeptService;

    @GetMapping
    @ApiOperation(value = "分页查询")
    public Response pageAll(@RequestParam(required = false, defaultValue = "1") long current,
                            @RequestParam(required = false, defaultValue = "10") long size) {
        Page roadPage = new Page<>(current, size);
        LambdaQueryWrapper<SysDept> queryWrapper = new QueryWrapper().lambda();
        Page pageList = sysDeptService.page(roadPage, queryWrapper);
        return Response.ok(pageList);
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新")
    @SaCheckPermission("dept.update")
    public Response saveOrUpdate(@RequestBody SysDept param) {
        return Response.ok(sysDeptService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    @SaCheckPermission("dept.detail")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysDeptService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除")
    @SaCheckPermission("dept.delete")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysDeptService.removeById(id));
    }


    @GetMapping("/data")
    public ResultTable data(SysDept param) {
        List<SysDept> data = sysDeptService.list(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getStatus, true)
                .orderByAsc(SysDept::getSort));
        return ResultTable.dataTable(data);
    }

    @GetMapping("/tree")
    public ResultTree tree(SysDept param) {
        List<SysDept> data = sysDeptService.list(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getStatus, true)
                .orderByAsc(SysDept::getSort));
        return ResultTree.data(data);
    }

}