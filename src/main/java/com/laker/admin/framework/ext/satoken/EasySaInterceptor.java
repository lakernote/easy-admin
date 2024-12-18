package com.laker.admin.framework.ext.satoken;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.laker.admin.framework.EasyAdminConstants;
import com.laker.admin.utils.EasyJwt;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


/**
 * @author laker
 */
@Slf4j
public class EasySaInterceptor extends SaInterceptor {

    EasyJwt easyJwt;

    /**
     * 创建一个 Sa-Token 综合拦截器，默认带有注解鉴权能力
     *
     * @param auth 认证函数，每次请求执行
     */
    public EasySaInterceptor(SaParamFunction<Object> auth, EasyJwt easyJwt) {
        this.auth = auth;
        this.easyJwt = easyJwt;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String requestURI = request.getRequestURI();
        if (CharSequenceUtil.startWithIgnoreCase(requestURI, "/wx")) {
            // check jwt
            String header = request.getHeader("Authorization");
            if (CharSequenceUtil.isBlank(header)) {
                throw new NotLoginException("未登录", null, null);
            }
            if (!CharSequenceUtil.startWithIgnoreCase(header, "Bearer ")) {
                throw new NotLoginException("未登录", null, null);
            }

            String token = header.substring(7); // 去掉 "Bearer "
            try {
                final Claims claims = easyJwt.parseToken(token);
                final String tokenType = claims.get(EasyAdminConstants.TOKEN_TYPE, String.class);
                if (CharSequenceUtil.isBlank(tokenType)) {
                    throw new NotLoginException("未登录", null, null);
                }
                EasyAdminConstants.TokenType tokenTypeEnum = EasyAdminConstants.TokenType.valueOf(tokenType);
                if (tokenTypeEnum != EasyAdminConstants.TokenType.ACCESS_TOKEN) {
                    throw new NotPermissionException("无权限");
                }
                final Long userId = claims.get(EasyAdminConstants.USER_ID, Long.class);
                request.setAttribute(EasyAdminConstants.USER_ID, userId);
                // 异常 instanceof SaTokenException 时，会被 SaToken 内部捕获并处理
            } catch (SaTokenException saEx) {
                throw saEx;
            } catch (Exception e) {
                log.error("jwt parse error", e);
                throw new NotLoginException("未登录", null, null);
            }
            return true;

        }
        if (StpUtil.getLoginIdAsLong() == 1) {
            return true;
        }
        return super.preHandle(request, response, handler);
    }
}
