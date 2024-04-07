package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.framework.lock.api.ILock;
import com.laker.admin.framework.lock.api.Locker;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.entity.SysDict;
import com.laker.admin.module.sys.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-16
 */
@Api(tags = "系统-字典管理")
@ApiSupport(order = 5)
@RestController
@RequestMapping("/sys/dict")
@Metrics
public class SysDictController {
    final ISysDictService sysDictService;

    final ILock ILock;

    public SysDictController(ISysDictService sysDictService, ILock ILock) {
        this.sysDictService = sysDictService;
        this.ILock = ILock;
    }

    @GetMapping
    @ApiOperation(value = "分页查询")
    public PageResponse<List<SysDict>> pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                               @RequestParam(required = false, defaultValue = "10") long limit) {
        Page<SysDict> roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        Page<SysDict> pageList = sysDictService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新")
    @SaCheckPermission("dict.update")
    public Response<Boolean> saveOrUpdate(@RequestBody SysDict param) {
        Locker locker = ILock.acquire(param.getDictCode(), Duration.ofSeconds(10));
        try {
            return Response.ok(sysDictService.saveOrUpdate(param));
        } finally {
            ILock.release(locker);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    public Response<SysDict> get(@PathVariable Long id) {
        return Response.ok(sysDictService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除")
    @SaCheckPermission("dict.delete")
    public Response<Boolean> delete(@PathVariable Long id) {
        return Response.ok(sysDictService.removeById(id));
    }

    @DeleteMapping("/batch/{ids}")
    @ApiOperation(value = "根据批量删除ids删除")
    @SaCheckPermission("dict.delete")
    public Response<Boolean> batchRemove(@PathVariable Long[] ids) {
        return Response.ok(sysDictService.removeByIds(CollUtil.toList(ids)));
    }
}
