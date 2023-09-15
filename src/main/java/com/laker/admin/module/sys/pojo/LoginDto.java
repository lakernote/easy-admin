package com.laker.admin.module.sys.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author laker
 */
@Data
public class LoginDto {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    /**
     * 因为密码比较重要，故仅用于反序列化
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotBlank(message = "验证码不能为空")
    private String captchaCode;
    @NotBlank(message = "验证码Id不能为空")
    private String uid;
}
