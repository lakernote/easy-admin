package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.lock.EasyLock;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.framework.redis.EasyRedisCacheConfig;
import com.laker.admin.module.sys.entity.SysDict;
import com.laker.admin.module.sys.service.ISysDictService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    EasyLock easyLock;

    @GetMapping
    @Operation(summary = "分页查询")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<SysDict> queryWrapper = new QueryWrapper().lambda();
        Page pageList = sysDictService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @PostMapping
    @Operation(summary = "新增或者更新")
    public Response saveOrUpdate(@RequestBody SysDict param) {
        return Response.ok(sysDictService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询")
    @Cacheable(value = EasyRedisCacheConfig.CACHE_NAME_1H, key = "#id")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysDictService.getById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据id删除")
    @CacheEvict(value = EasyRedisCacheConfig.CACHE_NAME_1H, key = "#id")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysDictService.removeById(id));
    }

    @DeleteMapping("/batch/{ids}")
    @Operation(summary = "根据批量删除ids删除")
    @SaCheckPermission("dict.delete")
    public Response batchRemove(@PathVariable Long[] ids) {
        return Response.ok(sysDictService.removeByIds(CollUtil.toList(ids)));
    }
}