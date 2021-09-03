package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.model.Response;
import com.laker.admin.framework.aop.Metrics;
import com.laker.admin.module.sys.entity.SysMenu;
import com.laker.admin.module.sys.pojo.MenuVo;
import com.laker.admin.module.sys.service.ISysMenuService;
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
@RestController
@RequestMapping("/sys/menu")
@Metrics
public class SysMenuController {
    @Autowired
    ISysMenuService sysMenuService;

    @GetMapping
    @ApiOperation(value = "系统菜单表分页查询")
    public Response pageAll(@RequestParam(required = false, defaultValue = "1") long current,
                            @RequestParam(required = false, defaultValue = "10") long size) {
        Page roadPage = new Page<>(current, size);
        LambdaQueryWrapper<SysMenu> queryWrapper = new QueryWrapper().lambda();
        Page pageList = sysMenuService.page(roadPage, queryWrapper);
        return Response.ok(pageList);
    }


    @GetMapping("/list")
    @ApiOperation(value = "系统菜单表分页查询")
    public Response list() {
        List<SysMenu> list = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery().orderByAsc(SysMenu::getSort));
        return Response.ok(list);
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新系统菜单表")
    @SaCheckPermission("menu.update")
    public Response saveOrUpdate(@RequestBody SysMenu param) {
        return Response.ok(sysMenuService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询系统菜单表")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysMenuService.getById(id));
    }


    @GetMapping("/tree")
    @ApiOperation(value = "菜单树")
    public List<MenuVo> tree() {
        return sysMenuService.menu();
    }


    @GetMapping("/selectTree")
    @ApiOperation(value = "菜单树")
    public Response selectTree() {
        List<MenuVo> menuVos = sysMenuService.menu();
        return Response.ok(menuVos);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除系统菜单表")
    @SaCheckPermission("menu.delete")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysMenuService.removeById(id));
    }
}