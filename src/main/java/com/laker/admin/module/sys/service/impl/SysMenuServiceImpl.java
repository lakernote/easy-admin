package com.laker.admin.module.sys.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laker.admin.module.sys.dto.MenuVo;
import com.laker.admin.module.sys.entity.SysPower;
import com.laker.admin.module.sys.entity.SysRolePower;
import com.laker.admin.module.sys.entity.SysUserRole;
import com.laker.admin.module.sys.mapper.SysMenuMapper;
import com.laker.admin.module.sys.service.ISysMenuService;
import com.laker.admin.module.sys.service.ISysRolePowerService;
import com.laker.admin.module.sys.service.ISysUserRoleService;
import com.laker.admin.utils.EasyTreeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    private final ISysUserRoleService sysUserRoleService;
    private final ISysRolePowerService sysRolePowerService;
    private final SysMenuMapper sysMenuMapper;

    public SysMenuServiceImpl(ISysUserRoleService sysUserRoleService, ISysRolePowerService sysRolePowerService, SysMenuMapper sysMenuMapper) {
        this.sysUserRoleService = sysUserRoleService;
        this.sysRolePowerService = sysRolePowerService;
        this.sysMenuMapper = sysMenuMapper;
    }

    @Override
    public List<MenuVo> menu() {
        long loginId = StpUtil.getLoginIdAsLong();
        List<SysPower> sysPowers;
        // 超级管理员开玩笑
        if (loginId == 1L) {
            sysPowers = sysMenuMapper.findAllByStatusOrderBySort(true);
        } else {
            sysPowers = getSysMenusPowerByLoginUser(loginId);
        }
        final List<MenuVo> menuInfo = getMenuVos(sysPowers);
        return EasyTreeUtil.toTree(menuInfo, 0L);
    }

    private static List<MenuVo> getMenuVos(List<SysPower> sysPowers) {
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
        return menuInfo;
    }

    private List<SysPower> getSysMenusPowerByLoginUser(Long loginId) {
        List<SysUserRole> userRoles = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, loginId));
        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).toList();
        List<SysRolePower> rolePowerLists = sysRolePowerService.list(Wrappers.<SysRolePower>lambdaQuery().in(SysRolePower::getRoleId, roleIds));
        List<Long> powerIds = rolePowerLists.stream().map(SysRolePower::getPowerId).toList();
        return this.list(Wrappers.<SysPower>lambdaQuery().in(SysPower::getMenuId, powerIds).eq(SysPower::getEnable, true).orderByAsc(SysPower::getSort));
    }

}
