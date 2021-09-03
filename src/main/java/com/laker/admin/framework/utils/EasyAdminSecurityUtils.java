package com.laker.admin.framework.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.laker.admin.framework.EasyAdminConstants;
import com.laker.admin.framework.ext.mybatis.UserInfoAndPowers;

/**
 *
 */
public class EasyAdminSecurityUtils {

    public static UserInfoAndPowers getCurrentUserInfo() {
        UserInfoAndPowers userInfoAndPowers = (UserInfoAndPowers) StpUtil.getSession().get(EasyAdminConstants.CURRENT_USER);
        return userInfoAndPowers;
    }

}
