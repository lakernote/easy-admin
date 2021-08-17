package com.laker.admin.module.sys.controller;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author laker
 */
@Data
public class LoginDto {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "验证码不能为空")
    private String captchaCode;
    @NotBlank(message = "验证码Id不能为空")
    private String uid;
}
