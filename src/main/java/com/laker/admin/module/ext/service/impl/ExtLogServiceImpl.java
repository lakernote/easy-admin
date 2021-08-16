package com.laker.admin.module.ext.service.impl;

import com.laker.admin.module.ext.entity.ExtLog;
import com.laker.admin.module.ext.mapper.ExtLogMapper;
import com.laker.admin.module.ext.service.IExtLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志 服务实现类
 * </p>
 *
 * @author laker
 * @since 2021-08-16
 */
@Service
public class ExtLogServiceImpl extends ServiceImpl<ExtLogMapper, ExtLog> implements IExtLogService {

}
