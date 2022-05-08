package com.laker.admin.module.ext.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.laker.admin.module.ext.entity.ExtLeave;
import com.laker.admin.module.ext.vo.LeaveVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author laker
 * @since 2021-08-19
 */
public interface IExtLeaveService extends IService<ExtLeave> {
    /**
     * 搞个多表联查示例
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<LeaveVo> pageV2(Page page, Wrapper queryWrapper);

}
