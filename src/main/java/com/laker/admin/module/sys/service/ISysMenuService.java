package com.laker.admin.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.laker.admin.module.sys.entity.SysMenu;

import java.util.Map;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author laker
 * @since 2021-08-04
 */
public interface ISysMenuService extends IService<SysMenu> {
    Map<String, Object> menu();
}
