package com.laker.admin.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laker.admin.framework.aop.trace.LakerTrace;
import com.laker.admin.framework.aop.trace.SpanType;
import com.laker.admin.framework.aop.trace.TraceCodeBlock;
import com.laker.admin.framework.ext.mybatis.UserDataPower;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.mapper.SysDeptMapper;
import com.laker.admin.module.sys.mapper.SysUserMapper;
import com.laker.admin.module.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author laker
 * @since 2021-08-05
 */
@LakerTrace(spanType = SpanType.Service)
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    SysDeptMapper deptMapper;

    @LakerTrace(spanType = SpanType.Service)
    @Override
    public SysUser getUserAndDeptById(Long userId) {
        SysUser user = this.getById(userId);
        if (user == null) {
            return null;
        }
        TraceCodeBlock.trace("deptMapper.selectById", value -> user.setDept(deptMapper.selectById(user.getDeptId())));
        return user;
    }

    @Override
    public List<UserDataPower> getUserDataPowers(Long userId) {
        return TraceCodeBlock.trace("sysUserMapper.getUserDataPowers", () -> this.getBaseMapper().getUserDataPowers(userId));
    }
}
