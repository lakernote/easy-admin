package com.laker.admin.module.ext.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.module.ext.entity.ExtLeave;
import com.laker.admin.module.ext.vo.LeaveVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author laker
 * @since 2021-08-19
 */
public interface ExtLeaveMapper extends BaseMapper<ExtLeave> {

    @Results(id = "pageV2",
            value = {
                    @Result(property = "createUser.nickName", column = "uNickName"),
                    @Result(property = "createUser.deptName", column = "uDeptName")
            })
    @Select("select l.* ,u.nick_name uNickName,d.dept_name uDeptName" +
            "   from ext_leave  l " +
            "       left join sys_user u on u.user_id = l.create_by " +
            "       left join sys_dept d on d.dept_id = u.dept_id " +
            "${ew.customSqlSegment}")
    IPage<LeaveVo> pageV2(Page page, @Param(Constants.WRAPPER) Wrapper queryWrapper);
}
