package com.laker.admin.framework.ext.satoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import com.laker.admin.module.sys.service.ISysUserService;
import com.laker.admin.utils.IP2CityUtil;
import com.laker.admin.utils.http.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 自定义侦听器的实现
 */
@Component
@Slf4j
public class MySaTokenListener implements SaTokenListener {
    public static final List<OnlineUser> ONLINE_USERS = new CopyOnWriteArrayList<>();

    @Autowired
    ISysUserService sysUserService;

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, SaLoginModel loginModel) {
        UserAgent requestUserAgent = HttpServletRequestUtil.getRequestUserAgent();
        String cityInfo = IP2CityUtil.getCityInfo(HttpServletRequestUtil.getRemoteIP());
        String[] split = cityInfo.split("\\|");
        ONLINE_USERS.add(OnlineUser.builder()
                .ip(HttpServletRequestUtil.getRemoteIP())
                .city(StrUtil.format("{}.{}.{}", split[0], split[2], split[3]))
                .loginTime(new Date())
                .os(requestUserAgent.getOs().getName())
                .userId((Long) loginId)
                .tokenValue(StpUtil.getTokenValue())
                .nickName(sysUserService.getById((Long) loginId).getNickName())
                .browser(requestUserAgent.getBrowser().getName()).build());
        log.info("user doLogin,useId:{},token:{}",loginId,StpUtil.getTokenValue());
    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        // ...
        ONLINE_USERS.removeIf(onlineUser ->
                onlineUser.getTokenValue().equals(tokenValue)
        );
        log.info("user doLogout,useId:{},token:{}",loginId,tokenValue);
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doLogoutByLoginId(String loginType, Object loginId, String tokenValue, String device) {
        // ...
        ONLINE_USERS.removeIf(onlineUser ->
                onlineUser.getTokenValue().equals(tokenValue)
        );
        log.info("user doLogoutByLoginId,useId:{},token:{}",loginId,tokenValue);
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue, String device) {
        ONLINE_USERS.removeIf(onlineUser ->
                onlineUser.getTokenValue().equals(tokenValue)
        );
        log.info("user doReplaced,useId:{},token:{}",loginId,tokenValue);
    }

    /**
     * 每次被封禁时触发
     */
    @Override
    public void doDisable(String loginType, Object loginId, long disableTime) {
        // ... 
    }

    /**
     * 每次被解封时触发
     */
    @Override
    public void doUntieDisable(String loginType, Object loginId) {
        // ... 
    }

    /**
     * 每次创建Session时触发
     */
    @Override
    public void doCreateSession(String id) {
        // ... 
    }

    /**
     * 每次注销Session时触发
     */
    @Override
    public void doLogoutSession(String id) {
        // ... 
    }

}
