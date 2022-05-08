package com.laker.admin.module.ext.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.module.ext.entity.ExtLeave;
import com.laker.admin.module.ext.mapper.ExtLeaveMapper;
import com.laker.admin.module.ext.service.IExtLeaveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laker.admin.module.ext.vo.LeaveVo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author laker
 * @since 2021-08-19
 */
@Service
public class ExtLeaveServiceImpl extends ServiceImpl<ExtLeaveMapper, ExtLeave> implements IExtLeaveService {

    @Override
    public IPage<LeaveVo> pageV2(Page page, Wrapper queryWrapper) {
        return this.getBaseMapper().pageV2(page,queryWrapper);
    }
}
