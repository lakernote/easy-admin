package com.laker.admin.module.sys.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import com.laker.admin.module.sys.entity.SysMenu;
import com.laker.admin.module.sys.service.ISysMenuService;
import org.springframework.web.bind.annotation.RestController;
import com.laker.admin.framework.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* <p>
    * 系统菜单表 前端控制器
    * </p>
*
* @author laker
* @since 2021-08-04
*/
@RestController
@RequestMapping("/module.sys/sys-menu")
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

    @PostMapping
    @ApiOperation(value = "新增或者更新系统菜单表")
    public Response saveOrUpdate(SysMenu param) {
        return Response.ok(sysMenuService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询系统菜单表")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysMenuService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除系统菜单表")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysMenuService.removeById(id));
    }
}