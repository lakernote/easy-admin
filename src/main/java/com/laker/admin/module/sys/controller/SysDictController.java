package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.framework.lock.api.Lock;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.entity.SysDict;
import com.laker.admin.module.sys.service.ISysDictService;
import io.swagger.annotations.Api;
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
@Api(tags = "系统-字典管理")
@ApiSupport(order = 5)
@RestController
@RequestMapping("/sys/dict")
@Metrics
public class SysDictController {
    @Autowired
    ISysDictService sysDictService;

    @Autowired
    Lock lock;

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
    @SaCheckPermission("dict.update")
    public Response saveOrUpdate(@RequestBody SysDict param) {
//        LLock llock = lock.acquire(param.getDictCode(), Duration.ofSeconds(10));
//        if (Objects.isNull(llock)) {
//            throw new BusinessException("其他人正在处理中，请稍后重试");
//        }


        try {
            return Response.ok(sysDictService.saveOrUpdate(param));
        } finally {
//            lock.release(llock);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysDictService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除")
    @SaCheckPermission("dict.delete")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysDictService.removeById(id));
    }

    @DeleteMapping("/batch/{ids}")
    @ApiOperation(value = "根据批量删除ids删除")
    @SaCheckPermission("dict.delete")
    public Response batchRemove(@PathVariable Long[] ids) {
        return Response.ok(sysDictService.removeByIds(CollUtil.toList(ids)));
    }
}
