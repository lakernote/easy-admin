package com.laker.admin.module.sys.pojo;

import lombok.Data;

@Data
public class PwdQo {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}