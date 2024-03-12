package com.laker.admin.framework.ext.stomp;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.utils.EasyAdminSecurityUtils;
import com.laker.admin.utils.IP2CityUtil;
import com.laker.admin.utils.http.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * 设置认证用户信息的握手拦截器
 * 这一步很关键，后面该链接绑定的会话都用这个，这里相当于是从http环境取出用户，后面拦截器、接口等处都不是http环境了，获取不到request等信息
 */
@Slf4j
public class EasyPrincipalHandshakeHandler extends DefaultHandshakeHandler {


    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        log.info("确定用户");
        //握手成功后调用，可以在这里保存用户id
        String userName = ((ServletServerHttpRequest) request).getServletRequest().getParameter("userName");
        String remoteIP = HttpServletRequestUtil.getRemoteIP();
        String address;
        if (!StrUtil.equals(remoteIP, "127.0.0.1")) {
            String cityInfo = IP2CityUtil.getCityInfo(remoteIP);
            String[] split = cityInfo.split("\\|");
            address = StrUtil.format("{}.{}.{}", split[2], split[3], split[4]);
        } else {
            address = "开发者";
        }
        EasyPrincipal easyPrincipal = EasyPrincipal.builder()
                .userId(StpUtil.getLoginIdAsString())
                .nickName(EasyAdminSecurityUtils.getCurrentUserInfo().getNickName())
                .address(address)
                .build();
        return easyPrincipal;
    }

}
