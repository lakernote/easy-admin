package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.PageResponse;
import com.laker.admin.framework.Response;
import com.laker.admin.framework.ResultTree;
import com.laker.admin.framework.aop.Metrics;
import com.laker.admin.module.sys.entity.SysMenu;
import com.laker.admin.module.sys.entity.SysRole;
import com.laker.admin.module.sys.entity.SysRolePower;
import com.laker.admin.module.sys.service.ISysMenuService;
import com.laker.admin.module.sys.service.ISysRolePowerService;
import com.laker.admin.module.sys.service.ISysRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-11
 */
@RestController
@RequestMapping("/sys/role")
@Metrics
public class SysRoleController {
    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    ISysMenuService sysMenuService;
    @Autowired
    ISysRolePowerService sysRolePowerService;

    @GetMapping
    @ApiOperation(value = "分页查询")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long current,
                                @RequestParam(required = false, defaultValue = "10") long size,
                                Integer roleType) {
        Page roadPage = new Page<>(current, size);
        LambdaQueryWrapper<SysRole> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.eq(SysRole::getRoleType, roleType);
        Page pageList = sysRoleService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新")
    @SaCheckPermission("role.update")
    public Response saveOrUpdate(@RequestBody SysRole param) {
        return Response.ok(sysRoleService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysRoleService.getById(id));
    }


    @GetMapping("/getRolePower")
    @ApiOperation(value = "根据角色id查询角色权限")
    public ResultTree getRolePower(Long roleId) {
        List<SysMenu> allPower = sysMenuService.list(null);
        List<SysRolePower> myPower = sysRolePowerService.list(Wrappers.<SysRolePower>lambdaQuery().eq(SysRolePower::getRoleId, roleId));
        allPower.forEach(sysPower -> {
            myPower.forEach(sysRolePower -> {
                if (sysRolePower.getPowerId().equals(sysPower.getMenuId())) {
                    sysPower.setCheckArr("1");
                }
            });
        });

        return ResultTree.data(allPower);
    }

    @PutMapping("/saveRolePower")
    @ApiOperation(value = "保存角色权限数据")
    @SaCheckPermission("role.update.power")
    @Transactional(rollbackFor = Exception.class)
    public Response saveRolePower(Long roleId, String powerIds) {
        List<String> stringList = Arrays.asList(powerIds.split(","));
        sysRolePowerService.remove(Wrappers.<SysRolePower>lambdaQuery().eq(SysRolePower::getRoleId, roleId));
        List<SysRolePower> rolePowers = new ArrayList<>();
        stringList.forEach(powerId -> {
            SysRolePower sysRolePower = new SysRolePower();
            sysRolePower.setRoleId(roleId);
            sysRolePower.setPowerId(Long.valueOf(powerId));
            rolePowers.add(sysRolePower);
        });
        boolean saveBatch = sysRolePowerService.saveBatch(rolePowers);
        return Response.ok(saveBatch);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除")
    @SaCheckPermission("role.delete")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysRoleService.removeById(id));
    }
}