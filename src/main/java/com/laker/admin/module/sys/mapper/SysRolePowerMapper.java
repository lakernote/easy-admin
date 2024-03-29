package com.laker.admin.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laker.admin.module.sys.entity.SysRolePower;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author laker
 * @since 2021-08-11
 */
public interface SysRolePowerMapper extends BaseMapper<SysRolePower> {
    List<String> getPowerCodesByUserIdAndPowerType(Long userId, Integer type);
}
