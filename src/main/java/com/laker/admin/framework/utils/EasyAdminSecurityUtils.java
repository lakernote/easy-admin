package com.laker.admin.framework.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.laker.admin.framework.EasyAdminConstants;
import com.laker.admin.framework.ext.mybatis.UserInfoAndPowers;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class EasyAdminSecurityUtils {

    public static UserInfoAndPowers getCurrentUserInfo() {
        UserInfoAndPowers userInfoAndPowers = null;
        try {
            userInfoAndPowers = (UserInfoAndPowers) StpUtil.getSession().get(EasyAdminConstants.CURRENT_USER);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return userInfoAndPowers;
    }

}
