package com.laker.admin.module.wx.miniapp.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.wx.miniapp.service.WxUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-16
 */
@Tag(name = "1.用户")// 在doc.html中的名称
@ApiSupport(order = 0, author = "laker") // 在doc.html中的顺序
@RestController
@RequestMapping("/wx/miniapp/user")
@Slf4j
@Validated  // 启用请求参数和路径变量的参数校验
public class WxMiniAppController {

    private final WxUserService wxUserService;

    public WxMiniAppController(WxUserService wxUserService) {
        this.wxUserService = wxUserService;
    }

    /**
     * <p>这个接口会接收微信小程序传递过来的code参数，然后通过Java SDK获取到用户的openid和sessionKey，最后返回给小程序端。</p>
     *
     * @param code 前端wx.login获取的code
     */
    @Operation(summary = "0.登录接口", description = "登录接口")
    @GetMapping("/login")
    public Response<Map> login(@NotBlank(message = "登录凭证不能为空") String code,
                               @RequestParam(required = false, defaultValue = "false") Boolean test) {
        return Response.ok(wxUserService.login(code, test));
    }

    @Operation(summary = "1.获取用户设置", description = "获取用户设置")
    @GetMapping("/setting")
    public Response<Void> login() {
        return Response.ok();
    }
}