package com.laker.admin.module.ext.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.PageResponse;
import com.laker.admin.framework.Response;
import com.laker.admin.module.ext.entity.ExtLog;
import com.laker.admin.module.ext.service.IExtLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-16
 */
@RestController
@RequestMapping("/ext/log")
public class ExtLogController {
    @Autowired
    IExtLogService extLogService;

    @GetMapping
    @ApiOperation(value = "日志分页查询")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<ExtLog> queryWrapper = new QueryWrapper().lambda();
        Page pageList = extLogService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新日志")
    public Response saveOrUpdate(@RequestBody ExtLog param) {
        return Response.ok(extLogService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询日志")
    public Response get(@PathVariable Long id) {
        return Response.ok(extLogService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除日志")
    public Response delete(@PathVariable Long id) {
        return Response.ok(extLogService.removeById(id));
    }

    @DeleteMapping("/batch/{ids}")
    @ApiOperation(value = "根据批量删除ids删除")
    public Response batchRemove(@PathVariable Long[] ids) {
        return Response.ok(extLogService.removeByIds(CollUtil.toList(ids)));
    }
}