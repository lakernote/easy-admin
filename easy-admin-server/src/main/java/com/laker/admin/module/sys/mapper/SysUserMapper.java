package com.laker.admin.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laker.admin.framework.ext.mybatis.UserDataPower;
import com.laker.admin.module.sys.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author laker
 * @since 2021-08-05
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户id获取其下的数据权限
     *
     * @param userId
     * @return
     */
    @Select("select p.* from sys_power p" +
            "                   inner join sys_role_power rw on rw.power_id = p.menu_id" +
            "                   inner join sys_user_role uro on rw.role_id = uro.role_id" +
            "                   inner join sys_role r on r.role_id = uro.role_id" +
            "            where uro.user_id = #{userId} and r.role_type = 2")
    List<UserDataPower> getUserDataPowers(@Param("userId") Long userId);
}
