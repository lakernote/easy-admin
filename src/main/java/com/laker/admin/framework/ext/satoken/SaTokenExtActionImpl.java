//package com.laker.admin.framework.ext.satoken;
//
//import cn.dev33.satoken.annotation.SaCheckLogin;
//import cn.dev33.satoken.stp.StpUtil;
//import com.laker.admin.framework.ext.mybatis.UserInfoAndPowers;
//import com.laker.admin.framework.utils.EasyAdminSecurityUtils;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.AnnotatedElement;
//
///**
// * Sa-Token 逻辑代理接口 [默认实现类]
// *
// * @author kong
// */
//@Component
//public class SaTokenExtActionImpl extends SaTokenActionDefaultImpl {
//
//    /**
//     * 从指定元素校验注解
//     *
//     * @param target see note
//     */
//    @Override
//    protected void validateAnnotation(AnnotatedElement target) {
//        UserInfoAndPowers currentUserInfo = EasyAdminSecurityUtils.getCurrentUserInfo();
//        if (currentUserInfo != null && currentUserInfo.isSuperAdmin()) {
//            return;
//        }
//        super.validateAnnotation(target);
//        // 校验 @SaCheckLogin 注解
//        if (!target.isAnnotationPresent(SaCheckLogin.class)) {
//            StpUtil.checkLogin();
//        }
//
//    }
//
//}
