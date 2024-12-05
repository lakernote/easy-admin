package com.laker.admin.module.sys.dto.user;

import lombok.Data;

@Data
public abstract class BaseUser {
    private Long userId;

    private String nickName;

    private Integer sex;

    private String phone;

    private Integer enable;

    private String email;

    private String avatar;
}
