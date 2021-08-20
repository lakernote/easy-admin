package com.laker.admin.framework.ext.mybatis;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.laker.admin.framework.EasyAdminConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author longli
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
        this.strictInsertFill(metaObject, "createBy", Long.class, StpUtil.getLoginIdAsLong());
        UserInfoAndPowers userInfoAndPowers = (UserInfoAndPowers) StpUtil.getSession().get(EasyAdminConstants.CURRENT_USER);
        this.strictInsertFill(metaObject, "createDeptId", Long.class, userInfoAndPowers.getDeptId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
        this.strictUpdateFill(metaObject, "operator", String.class, "张三");
    }
}