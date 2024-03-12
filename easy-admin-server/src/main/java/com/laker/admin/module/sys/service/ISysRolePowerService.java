package com.laker.admin.module.sys.service;

import com.laker.admin.module.sys.entity.SysRolePower;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author laker
 * @since 2021-08-11
 */
public interface ISysRolePowerService extends IService<SysRolePower> {

    boolean saveRolePower(Long roleId, String powerIds);
}
