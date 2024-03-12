package com.laker.admin.module.sys.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author laker
 */
@Data
public class LoginDto {
    @NotBlank(message = "用户名不能为空")
    private String username;

    // 因为密码比较重要，故仅用于反序列化
    @NotBlank(message = "密码不能为空")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
