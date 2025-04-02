package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.entity.SysPower;
import com.laker.admin.module.sys.pojo.MenuVo;
import com.laker.admin.module.sys.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-04
 */
@Api(tags = "系统-权限菜单管理")
@ApiSupport(order = 5)
@RestController
@RequestMapping("/sys/menu")
@Metrics
public class SysMenuController {
    @Autowired
    ISysMenuService sysMenuService;

    @GetMapping
    @ApiOperation(value = "系统菜单表分页查询")
    public Response<Page<SysPower>> pageAll(@RequestParam(required = false, defaultValue = "1") long current,
                                            @RequestParam(required = false, defaultValue = "10") long size) {
        Page<SysPower> roadPage = new Page<>(current, size);
        LambdaQueryWrapper<SysPower> queryWrapper = new LambdaQueryWrapper<>();
        Page<SysPower> pageList = sysMenuService.page(roadPage, queryWrapper);
        return Response.ok(pageList);
    }


    @GetMapping("/list")
    @ApiOperation(value = "系统菜单表分页查询")
    public Response<List<SysPower>> list() {
        List<SysPower> list = sysMenuService.list(Wrappers.<SysPower>lambdaQuery().orderByAsc(SysPower::getSort));
        return Response.ok(list);
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新系统菜单表")
    @SaCheckPermission("menu.update")
    public Response<Boolean> saveOrUpdate(@RequestBody SysPower param) {
        return Response.ok(sysMenuService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询系统菜单表")
    public Response<SysPower> get(@PathVariable Long id) {
        return Response.ok(sysMenuService.getById(id));
    }


    @GetMapping("/tree")
    @ApiOperation(value = "菜单树")
    public List<MenuVo> tree() {
        return sysMenuService.menu();
    }


    @GetMapping("/selectTree")
    @ApiOperation(value = "菜单列表树")
    public Response<List<MenuVo>> selectTree() {
        List<MenuVo> menuVos = sysMenuService.menu();
        return Response.ok(menuVos);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除系统菜单表")
    @SaCheckPermission("menu.delete")
    public Response<Boolean> delete(@PathVariable Long id) {
        return Response.ok(sysMenuService.removeById(id));
    }

    @DeleteMapping("/batch/{ids}")
    @ApiOperation(value = "批量删除系统菜单表")
    @SaCheckPermission("menu.delete")
    public Response<Boolean> deleteBatch(@PathVariable Long[] ids) {
        return Response.ok(sysMenuService.removeByIds(CollUtil.toList(ids)));
    }
}
