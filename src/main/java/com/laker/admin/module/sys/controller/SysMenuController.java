package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.model.Response;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.module.sys.entity.SysPower;
import com.laker.admin.module.sys.pojo.MenuVo;
import com.laker.admin.module.sys.service.ISysMenuService;
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
    public Response pageAll(@RequestParam(required = false, defaultValue = "1") long current,
                            @RequestParam(required = false, defaultValue = "10") long size) {
        Page roadPage = new Page<>(current, size);
        LambdaQueryWrapper<SysPower> queryWrapper = new QueryWrapper().lambda();
        Page pageList = sysMenuService.page(roadPage, queryWrapper);
        return Response.ok(pageList);
    }


    @GetMapping("/list")
    public Response list() {
        List<SysPower> list = sysMenuService.list(Wrappers.<SysPower>lambdaQuery().orderByAsc(SysPower::getSort));
        return Response.ok(list);
    }

    @PostMapping
    @SaCheckPermission("menu.update")
    public Response saveOrUpdate(@RequestBody SysPower param) {
        return Response.ok(sysMenuService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysMenuService.getById(id));
    }


    @GetMapping("/tree")
    public List<MenuVo> tree() {
        return sysMenuService.menu();
    }


    @GetMapping("/selectTree")
    public Response selectTree() {
        List<MenuVo> menuVos = sysMenuService.menu();
        return Response.ok(menuVos);
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission("menu.delete")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysMenuService.removeById(id));
    }
}