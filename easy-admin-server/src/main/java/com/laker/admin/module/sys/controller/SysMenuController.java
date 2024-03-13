package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.dto.MenuVo;
import com.laker.admin.module.sys.dto.MyMenuVo;
import com.laker.admin.module.sys.entity.SysPower;
import com.laker.admin.module.sys.service.ISysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-04
 */
@Tag(name = "系统-菜单")
@RestController
@RequestMapping("/sys/menu/v1")
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
        return sysMenuService.myMenu();
    }


    @Operation(summary = "获取侧边栏动态菜单")
    @GetMapping("/asideMenus")
    public Response<List<MyMenuVo>> asideMenus() {

        List<MyMenuVo> menuVos = new ArrayList<>();
        // 1.dashboard
        ArrayList<MyMenuVo> arrayList = new ArrayList<>();
        MyMenuVo dashboard = MyMenuVo.builder()
                .path("/dashboard").name("Dashboard").component("#")
                .meta(MyMenuVo.Meta.builder()
                        .title("router.dashboard")
                        .icon("ant-design:dashboard-filled")
                        .alwaysShow(true).build())
                .children(arrayList).build();
        MyMenuVo analysis = MyMenuVo.builder()
                .path("analysis").name("'Analysis'").component("views/Dashboard/Analysis")
                .meta(MyMenuVo.Meta.builder()
                        .title("router.analysis")
                        .noCache(true)
                        .affix(true).build())
                .children(new ArrayList<>()).build();
        arrayList.add(analysis);
        MyMenuVo workplace = MyMenuVo.builder()
                .path("workplace").name("Workplace").component("views/Dashboard/Workplace")
                .meta(MyMenuVo.Meta.builder()
                        .title("router.workplace")
                        .noCache(true)
                        .affix(true).build())
                .children(new ArrayList<>()).build();
        arrayList.add(workplace);


        // 2. 权限管理

        MyMenuVo department = MyMenuVo.builder()
                .path("department")
                .component("views/Authorization/Department/Department")
                .name("Department")
                .meta(MyMenuVo.Meta.builder()
                        .title("router.department")
                        .icon("clarity:document-solid").build())
                .children(new ArrayList<>()).build();

        MyMenuVo authorization = MyMenuVo.builder()
                .path("/authorization")
                .name("'Authorization'")
                .redirect("/authorization/user")
                .component("#")
                .meta(MyMenuVo.Meta.builder()
                        .title("router.authorization")
                        .icon("ant-design:lock-outlined")
                        .alwaysShow(true)
                        .build())
                .children(new ArrayList<>())
                .build().addChild(department);

        menuVos.add(dashboard);
        menuVos.add(authorization);

        return Response.ok(menuVos);
    }


    @GetMapping("/selectTree")
    public Response selectTree() {
        List<MenuVo> menuVos = sysMenuService.myMenu();
        return Response.ok(menuVos);
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission("menu.delete")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysMenuService.removeById(id));
    }
}
