package com.laker.admin.framework.ext.satoken;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对默认的做增强，默认拦截登录check
 */
public class LakerSaAnnotationInterceptor extends SaAnnotationInterceptor {

    /**
     * 每次请求之前触发的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        // 获取处理method
//        if (handler instanceof HandlerMethod == false) {
//            return true;
//        }
        // 判断是否登录
        super.getStpLogic().checkLogin();
        // 调用父类的默认方法
        return super.preHandle(request, response, handler);

    }
}
