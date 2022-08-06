package com.laker.admin.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laker.admin.framework.aop.trace.LakerTrace;
import com.laker.admin.framework.aop.trace.SpanType;
import com.laker.admin.module.sys.entity.SysDept;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author laker
 * @since 2021-08-11
 */
@LakerTrace(spanType = SpanType.Mapper)
public interface SysDeptMapper extends BaseMapper<SysDept> {

}
