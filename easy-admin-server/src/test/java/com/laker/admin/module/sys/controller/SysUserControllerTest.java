package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.config.LakerConfig;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.service.ISysRoleService;
import com.laker.admin.module.sys.service.ISysUserRoleService;
import com.laker.admin.module.sys.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * @author : [laker]
 * @className : SysUserControllerTest
 * @description : [powermock测试demo，测试类包目录应与原类目录一致，方便统计单元测试覆盖率]
 * @date : 2022年04月16日 17:52
 * @createTime : [2022/4/16 17:52]
 */
@RunWith(PowerMockRunner.class) // 告诉JUnit使用PowerMockRunner进行测试
@PrepareForTest({SysUserController.class,StpUtil.class}) // 所有需要测试的类列在此处
@Slf4j
public class SysUserControllerTest {
    /**
     * 需要单元测试的类
     */
    @InjectMocks
    SysUserController sysUserController;
    /**
     * 单元测试类依赖的类
     */
    @Mock
    ISysUserService sysUserService;
    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    ISysUserRoleService sysUserRoleService;
    @Autowired
    LakerConfig lakerConfig;

    @Test
    public void pageAll() {
        /** 录制 mock操作 */
        when(sysUserService.page(any(), any()))
                .thenReturn(new Page<>());
        /** 执行操作 */
        //执行mock查询操作
        PageResponse result = sysUserController.pageAll(1, 2, 3L, "laker");
        log.info(result.toString());

    }

    @Test
    public void saveOrUpdate() {
        PowerMockito.mockStatic(StpUtil.class);
        when(StpUtil.getLoginIdAsLong()).thenReturn(1l);
        /** 录制 mock操作 */
        when(sysUserService.save(any()))
                .thenReturn(Boolean.TRUE);
        when(sysUserService.saveOrUpdate(any()))
                .thenReturn(Boolean.TRUE);
        /** 执行操作 */
        //执行mock查询操作
        SysUser parameter = new SysUser();
        parameter.setDeptId(1L);
        sysUserController.saveOrUpdate(parameter);
    }
}
