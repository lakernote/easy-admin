package com.laker.admin.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.mapper.SysDeptMapper;
import com.laker.admin.module.sys.mapper.SysUserMapper;
import com.laker.admin.module.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author laker
 * @since 2021-08-05
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    SysDeptMapper deptMapper;

    @Override
    public SysUser getUserAndDeptById(Long userId) {
        SysUser user = this.getById(userId);
        if (user == null) {
            return null;
        }
        user.setDept(deptMapper.selectById(user.getDeptId()));
        return user;
    }
}
