package com.laker.admin.framework.ext.stomp;

import lombok.Builder;
import lombok.Data;

import java.security.Principal;

/**
 * 定义一个自己的权限验证类
 */
@Data
@Builder
public class EasyPrincipal implements Principal {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 地址
     */
    private String address;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户状态
     */
    private int status;

    @Override
    public String getName() {
        return userId;
    }
}
