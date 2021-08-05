package com.laker.admin.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laker.admin.module.sys.entity.SysMenu;
import com.laker.admin.module.sys.mapper.SysMenuMapper;
import com.laker.admin.module.sys.pojo.MenuVo;
import com.laker.admin.module.sys.service.ISysMenuService;
import com.laker.admin.utils.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author laker
 * @since 2021-08-04
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {


    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public Map<String, Object> menu() {
        Map<String, Object> map = new HashMap<>(16);
        Map<String, Object> home = new HashMap<>(16);
        Map<String, Object> logo = new HashMap<>(16);
        List<SysMenu> menuList = sysMenuMapper.findAllByStatusOrderBySort(true);
        List<MenuVo> menuInfo = new ArrayList<>();
        for (SysMenu e : menuList) {
            MenuVo menuVO = new MenuVo();
            menuVO.setId(e.getId());
            menuVO.setPid(e.getPid());
            menuVO.setHref(e.getHref());
            menuVO.setTitle(e.getTitle());
            menuVO.setIcon(e.getIcon());
            menuVO.setTarget(e.getTarget());
            menuInfo.add(menuVO);
        }
        // 1.定义首页名称以及显示的页面
        home.put("title", "首页");
        //控制器路由,自行定义
        home.put("href", "page/welcome-1.html?t=1");
        // 2.定义logo的信息
        logo.put("title", "Easy-Admin后台管理系统");
        //静态资源文件路径,可使用默认的logo.png
        logo.put("image", "images/logo.png");
        logo.put("href", "");
        map.put("homeInfo", home);
        map.put("logoInfo", logo);
        // 3.定义菜单，可根据人员权限定义
        map.put("menuInfo", TreeUtil.toTree(menuInfo, 0L));
        return map;
    }

}
