package com.laker.admin.framework.ext.satoken;

import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author laker
 */
public class EasySaInterceprot extends SaInterceptor {

    /**
     * 创建一个 Sa-Token 综合拦截器，默认带有注解鉴权能力
     */
    public EasySaInterceprot() {
    }

    /**
     * 创建一个 Sa-Token 综合拦截器，默认带有注解鉴权能力
     *
     * @param auth 认证函数，每次请求执行
     */
    public EasySaInterceprot(SaParamFunction<Object> auth) {
        this.auth = auth;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (StpUtil.getLoginIdAsLong() == 1) {
            return true;
        }
        return super.preHandle(request, response, handler);
    }
}
