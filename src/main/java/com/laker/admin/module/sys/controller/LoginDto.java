package com.laker.admin.module.sys.controller;

import lombok.Data;

/**
 * @author laker
 */
@Data
public class LoginDto {

    private String username;
    private String password;
    private String captchaCode;
    private String uid;
}
