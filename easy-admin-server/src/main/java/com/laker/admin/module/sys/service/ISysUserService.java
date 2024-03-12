package com.laker.admin.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.laker.admin.framework.ext.mybatis.UserDataPower;
import com.laker.admin.module.sys.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author laker
 * @since 2021-08-05
 */
public interface ISysUserService extends IService<SysUser> {


    SysUser getUserAndDeptById(Long userId);

    /**
     * 根据用户id获取其所拥有的数据权限列表
     *
     * @param userId
     * @return
     */
    List<UserDataPower> getUserDataPowers(Long userId);
}
