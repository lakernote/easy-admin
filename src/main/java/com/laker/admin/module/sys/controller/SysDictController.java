package com.laker.admin.module.sys.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.PageResponse;
import com.laker.admin.framework.Response;
import com.laker.admin.module.sys.entity.SysDict;
import com.laker.admin.module.sys.service.ISysDictService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-16
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {
    @Autowired
    ISysDictService sysDictService;

    @GetMapping
    @ApiOperation(value = "分页查询")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<SysDict> queryWrapper = new QueryWrapper().lambda();
        Page pageList = sysDictService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新")
    public Response saveOrUpdate(@RequestBody SysDict param) {
        return Response.ok(sysDictService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysDictService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysDictService.removeById(id));
    }

    @DeleteMapping("/batch/{ids}")
    @ApiOperation(value = "根据批量删除ids删除")
    public Response batchRemove(@PathVariable Long[] ids) {
        return Response.ok(sysDictService.removeByIds(CollUtil.toList(ids)));
    }
}