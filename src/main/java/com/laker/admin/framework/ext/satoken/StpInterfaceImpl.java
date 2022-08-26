package com.laker.admin.framework.ext.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laker.admin.module.sys.entity.SysPower;
import com.laker.admin.module.sys.entity.SysRolePower;
import com.laker.admin.module.sys.entity.SysUserRole;
import com.laker.admin.module.sys.service.ISysMenuService;
import com.laker.admin.module.sys.service.ISysRolePowerService;
import com.laker.admin.module.sys.service.ISysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    ISysUserRoleService sysUserRoleService;
    @Autowired
    ISysRolePowerService sysRolePowerService;
    @Autowired
    ISysMenuService menuService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginKey) {
        List<SysUserRole> userRoles = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, loginId));
        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        List<SysRolePower> rolePowerLists = sysRolePowerService.list(Wrappers.<SysRolePower>lambdaQuery().in(SysRolePower::getRoleId, roleIds));
        List<Long> powerIds = rolePowerLists.stream().map(SysRolePower::getPowerId).collect(Collectors.toList());
        // 2是按钮
        List<SysPower> sysPowers = menuService.list(Wrappers.<SysPower>lambdaQuery().in(SysPower::getMenuId, powerIds).eq(SysPower::getType, 2));
        List<String> strings = sysPowers.stream().map(SysPower::getPowerCode).collect(Collectors.toList());
        return strings;
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {

        List<SysUserRole> userRoles = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, loginId));
        List<String> roleIds = userRoles.stream().map(sysUserRole -> sysUserRole.getRoleId() + "").collect(Collectors.toList());

        return roleIds;
    }
}