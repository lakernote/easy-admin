package com.laker.admin.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laker.admin.module.sys.entity.SysPower;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统菜单表 Mapper 接口
 * </p>
 *
 * @author laker
 * @since 2021-08-04
 */
public interface SysMenuMapper extends BaseMapper<SysPower> {

    @Select("select * from sys_power where enable = #{enable} order by sort")
    List<SysPower> findAllByStatusOrderBySort(Boolean enable);
}
