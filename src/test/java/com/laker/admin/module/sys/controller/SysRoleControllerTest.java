package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laker.admin.framework.exception.BusinessException;
import com.laker.admin.framework.model.ResultTree;
import com.laker.admin.module.sys.entity.SysPower;
import com.laker.admin.module.sys.entity.SysRole;
import com.laker.admin.module.sys.entity.SysRolePower;
import com.laker.admin.module.sys.service.ISysMenuService;
import com.laker.admin.module.sys.service.ISysRolePowerService;
import com.laker.admin.module.sys.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

/**
 * <p>
 * 角色前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-11
 */
@RunWith(PowerMockRunner.class) // 告诉JUnit使用PowerMockRunner进行测试
@PrepareForTest({SysRoleController.class, StpUtil.class}) // 所有需要测试的类列在此处
@Slf4j
public class SysRoleControllerTest {
    @InjectMocks
    SysRoleController SysRoleController;
    @Mock
    ISysRoleService sysRoleService;
    @Mock
    ISysMenuService sysMenuService;
    @Mock
    ISysRolePowerService sysRolePowerService;

    @Test
    public void getRolePower() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleType(SysRole.ROLE_TYPE_MENU);
        PowerMockito.when(sysRoleService.getById(any())).thenReturn(sysRole);
        SysRoleController.getRolePower(1L);
    }

}