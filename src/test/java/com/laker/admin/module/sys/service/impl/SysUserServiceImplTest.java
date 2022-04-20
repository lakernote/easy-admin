package com.laker.admin.module.sys.service.impl;

import com.laker.admin.framework.ext.mybatis.UserDataPower;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.mapper.SysDeptMapper;
import com.laker.admin.module.sys.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author : [laker]
 * @className : SysUserControllerTest
 * @description : [powermock测试demo，测试类包目录应与原类目录一致，方便统计单元测试覆盖率]
 * @date : 2022年04月16日 17:52
 * @createTime : [2022/4/16 17:52]
 */
@RunWith(PowerMockRunner.class) // 告诉JUnit使用PowerMockRunner进行测试
@PrepareForTest({SysUserServiceImpl.class}) // 所有需要测试的类列在此处
@Slf4j
public class SysUserServiceImplTest {
    /**
     * 需要单元测试的类
     */
    @InjectMocks
    SysUserServiceImpl SysUserServiceImpl = new SysUserServiceImpl();
    /**
     * 单元测试类依赖的类
     */
    @Mock
    SysDeptMapper deptMapper;
    @Mock
    SysUserMapper baseMapper;

    /**
     * 待ut的方法中，有this关键字的。
     */
    @Test
    public void getUserAndDeptById() {
        /** 录制 mock操作 */
        when(deptMapper.selectById(any()))
                .thenReturn(null);
        //when  thenReturn 配合mock使用 mock的都是假的默认返回null，必须要打桩

        SysUserServiceImpl spy = PowerMockito.spy(SysUserServiceImpl);
        // 打桩 SysUserServiceImpl的 this.getById();
        PowerMockito.doReturn(new SysUser()).when(spy).getById(3L);
        PowerMockito.doReturn(null).when(spy).getById(4L);
        // doReturn when 配合 spy使用 spy的都是真的 特殊的this类的才打桩。

        /** 执行操作 */
        //执行mock查询操作
        SysUser result1 = spy.getUserAndDeptById(3L);

        SysUser result2 = spy.getUserAndDeptById(4L);
        log.info(result1.toString());
    }

    @Test
    public void getUserDataPowers() throws IllegalAccessException {
        /** 录制 mock操作 */
        List<UserDataPower> result = new ArrayList<>();
        result.add(new UserDataPower());
        when(baseMapper.getUserDataPowers(4L)).thenReturn(result);
        /**
         * 模拟私有属性
         */
        MemberModifier
                .field(SysUserServiceImpl.class, "baseMapper").set(
                        SysUserServiceImpl, baseMapper);

        List<UserDataPower> userDataPowers = SysUserServiceImpl.getUserDataPowers(4L);
        Assert.assertEquals(1l, userDataPowers.size());
    }
}
