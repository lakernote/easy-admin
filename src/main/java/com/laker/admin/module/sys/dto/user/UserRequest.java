package com.laker.admin.module.sys.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRequest extends BaseUser {
    private Long userId;
    private String userName;
    private String password;
    private Long deptId;
    private String roleIds;
}
