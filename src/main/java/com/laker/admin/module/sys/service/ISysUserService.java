package com.laker.admin.module.sys.service;

import com.laker.admin.module.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author laker
 * @since 2021-08-05
 */
public interface ISysUserService extends IService<SysUser> {


    SysUser getUserAndDeptById(Long userId);

}
