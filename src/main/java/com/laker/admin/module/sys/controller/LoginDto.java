package com.laker.admin.module.sys.controller;

import lombok.Data;

@Data
public class LoginDto {

    private String username;
    private String password;
    private String captchaCode;
}
