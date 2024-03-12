package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.exception.BusinessException;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.framework.model.ResultTree;
import com.laker.admin.module.sys.entity.SysPower;
import com.laker.admin.module.sys.entity.SysRole;
import com.laker.admin.module.sys.entity.SysRolePower;
import com.laker.admin.module.sys.service.ISysMenuService;
import com.laker.admin.module.sys.service.ISysRolePowerService;
import com.laker.admin.module.sys.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class SysRoleController {
    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    ISysMenuService sysMenuService;
    @Autowired
    ISysRolePowerService sysRolePowerService;

    @GetMapping
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long current,
                                @RequestParam(required = false, defaultValue = "10") long size,
                                Integer roleType) {
        Page roadPage = new Page<>(current, size);
        LambdaQueryWrapper<SysRole> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.eq(roleType != null, SysRole::getRoleType, roleType);
        Page pageList = sysRoleService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @PostMapping
    @SaCheckPermission("role.update")
    public Response saveOrUpdate(@RequestBody SysRole param) {
        return Response.ok(sysRoleService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysRoleService.getById(id));
    }


    /**
     * 根据角色id查询角色权限
     * 如果角色是菜单角色 则剔除数据权限
     * 如果角色是数据权限 则剔除非数据权限
     *
     * @param roleId
     * @return
     */
    @GetMapping("/getRolePower")
    public ResultTree getRolePower(Long roleId) {
        SysRole role = sysRoleService.getById(roleId);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }
        List<SysPower> allPower = null;
        // 菜单角色
        if (role.getRoleType().equals(SysRole.ROLE_TYPE_MENU)) {
            allPower = sysMenuService.list(Wrappers.<SysPower>lambdaQuery().ne(SysPower::getType, 3));
        } else {
            allPower = sysMenuService.list(Wrappers.<SysPower>lambdaQuery().ne(SysPower::getType, 2));
        }

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
    @SaCheckPermission("role.update.power")
    public Response saveRolePower(Long roleId, String powerIds) {
        return Response.ok(sysRolePowerService.saveRolePower(roleId, powerIds));
    }


    @DeleteMapping("/{id}")
    @SaCheckPermission("role.delete")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysRoleService.removeById(id));
    }
}