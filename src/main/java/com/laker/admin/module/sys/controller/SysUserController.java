package com.laker.admin.module.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.PageResponse;
import com.laker.admin.framework.Response;
import com.laker.admin.module.sys.entity.SysRole;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.entity.SysUserRole;
import com.laker.admin.module.sys.service.ISysRoleService;
import com.laker.admin.module.sys.service.ISysUserRoleService;
import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-05
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    ISysUserService sysUserService;
    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    ISysUserRoleService sysUserRoleService;

    @GetMapping
    @ApiOperation(value = "分页查询")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long current,
                                @RequestParam(required = false, defaultValue = "10") long size) {
        Page roadPage = new Page<>(current, size);
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper().lambda();
        Page pageList = sysUserService.page(roadPage, queryWrapper);

        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新")
    @Transactional
    public Response saveOrUpdate(@RequestBody SysUser param) {
        this.saveUserRole(param.getUserId(), Arrays.asList(param.getRoleIds().split(",")));
        if (param.getUserId() == null) {
            param.setCreateTime(LocalDateTime.now());
        }
        return Response.ok(sysUserService.saveOrUpdate(param));
    }

    public boolean saveUserRole(Long userId, List<String> roleIds) {
        sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        roleIds.forEach(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(Long.valueOf(roleId));
            sysUserRole.setUserId(userId);
            sysUserRoles.add(sysUserRole);
        });
        return sysUserRoleService.saveBatch(sysUserRoles);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysUserService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysUserService.removeById(id));
    }

    @GetMapping("/getRoles")
    public Response edit(Long userId) {
        List<SysRole> allRole = sysRoleService.list(null);
        if (userId != null) {
            List<SysUserRole> myRole = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
            allRole.forEach(sysRole -> {
                myRole.forEach(sysUserRole -> {
                    if (sysRole.getRoleId().equals(sysUserRole.getRoleId())) {
                        sysRole.setChecked(true);
                    }
                });
            });
        }
        return Response.ok(allRole);
    }

}