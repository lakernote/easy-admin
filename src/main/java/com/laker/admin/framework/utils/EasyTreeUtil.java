package com.laker.admin.framework.utils;

import com.laker.admin.module.sys.pojo.MenuVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laker
 */
public class EasyTreeUtil {

    public static List<MenuVo> toTree(List<MenuVo> treeList, Long pid) {
        List<MenuVo> retList = new ArrayList<>();
        for (MenuVo parent : treeList) {
            if (pid.equals(parent.getPid())) {
                retList.add(findChildren(parent, treeList));
            }
        }
        return retList;
    }

    private static MenuVo findChildren(MenuVo parent, List<MenuVo> treeList) {
        for (MenuVo child : treeList) {
            if (parent.getId().equals(child.getPid())) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(findChildren(child, treeList));
            }
        }
        return parent;
    }
}
