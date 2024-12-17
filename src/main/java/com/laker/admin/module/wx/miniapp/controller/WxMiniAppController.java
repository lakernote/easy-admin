package com.laker.admin.module.wx.miniapp.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.wx.miniapp.dto.WxLoginRequest;
import com.laker.admin.module.wx.miniapp.dto.WxUserVo;
import com.laker.admin.module.wx.miniapp.service.WxUserService;
import com.laker.admin.utils.http.EasyRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     */
    @Operation(summary = "0.登录接口", description = "登录接口")
    @PostMapping("/login")
    public Response<Map> login(@Validated @RequestBody WxLoginRequest wxLoginRequest) {
        return Response.ok(wxUserService.login(wxLoginRequest.getCode()));
    }

    @Operation(summary = "1.获取用户设置", description = "获取用户设置")
    @GetMapping("/setting")
    public Response<Void> getSetting() {
        final Long userId = EasyRequestUtil.getWxUserId();
        log.info("userId:{}", userId);
        return Response.ok();
    }

    @Operation(summary = "2.获取用户信息")
    @GetMapping("/info")
    public Response<WxUserVo> getInfo() {
        final Long userId = EasyRequestUtil.getWxUserId();
        return Response.ok(wxUserService.getByUserId(userId));
    }

    /**
     * 续约接口
     *
     * @param refreshToken 请求体中的 refreshToken
     * @return 续约后的 token
     */
    @PostMapping("/renew")
    @Operation(summary = "3.续约接口")
    public Response<Map> renewToken(@RequestBody Map<String, String> request) {
        String oldRefreshToken = request.get("refreshToken");

        // 检查 refreshToken 是否为空
        if (oldRefreshToken == null || oldRefreshToken.isEmpty()) {
            return Response.error400("Refresh token is required");
        }

        try {
            return Response.ok(wxUserService.renew(oldRefreshToken));
        } catch (Exception e) {
            log.warn("renew token failed, msg:{}", e.getMessage());
            return Response.error401();
        }
    }
}