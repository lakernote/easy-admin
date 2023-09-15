package com.laker.admin.module.sys.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laker.admin.module.sys.entity.SysPower;
import com.laker.admin.module.sys.entity.SysRolePower;
import com.laker.admin.module.sys.entity.SysUserRole;
import com.laker.admin.module.sys.mapper.SysMenuMapper;
import com.laker.admin.module.sys.pojo.MenuVo;
import com.laker.admin.module.sys.service.ISysMenuService;
import com.laker.admin.module.sys.service.ISysRolePowerService;
import com.laker.admin.module.sys.service.ISysUserRoleService;
import com.laker.admin.utils.TreeUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author laker
 * @since 2021-08-04
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysPower> implements ISysMenuService {

    @Autowired
    ISysUserRoleService sysUserRoleService;
    @Autowired
    ISysRolePowerService sysRolePowerService;
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<MenuVo> menu() {
        Long loginId = StpUtil.getLoginIdAsLong();
        List<SysPower> sysPowers = null;
        // 超级管理员开玩笑
        if (loginId.longValue() == 1L) {
            sysPowers = sysMenuMapper.findAllByStatusOrderBySort(true);
        } else {
            sysPowers = getSysMenusPowerByLoginUser(loginId);
        }
        List<MenuVo> menuInfo = new ArrayList<>();
        for (SysPower e : sysPowers) {
            MenuVo menuVO = new MenuVo();
            menuVO.setId(e.getMenuId());
            menuVO.setPid(e.getPid());
            menuVO.setHref(e.getHref());
            menuVO.setTitle(e.getTitle());
            menuVO.setIcon(e.getIcon());
            menuVO.setOpenType(e.getOpenType());
            menuVO.setType(e.getType());
            menuVO.setPowerCode(e.getPowerCode());
            menuInfo.add(menuVO);
        }
        return TreeUtil.toTree(menuInfo, 0L);
    }

    private List<SysPower> getSysMenusPowerByLoginUser(Long loginId) {
        List<SysUserRole> userRoles = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, loginId));
        List<Long> roleIds = userRoles.stream().map(sysUserRole -> sysUserRole.getRoleId()).collect(Collectors.toList());
        List<SysRolePower> rolePowerLists = sysRolePowerService.list(Wrappers.<SysRolePower>lambdaQuery().in(SysRolePower::getRoleId, roleIds));
        List<Long> powerIds = rolePowerLists.stream().map(sysRolePower -> sysRolePower.getPowerId()).collect(Collectors.toList());
        return this.list(Wrappers.<SysPower>lambdaQuery().in(SysPower::getMenuId, powerIds).eq(SysPower::getEnable, true).orderByAsc(SysPower::getSort));
    }

}
