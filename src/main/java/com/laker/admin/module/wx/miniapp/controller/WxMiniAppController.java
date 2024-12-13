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
    public Response<Void> login() {
        final Long userId = EasyRequestUtil.getWxUserId();
        log.info("userId:{}", userId);
        return Response.ok();
    }

    @Operation(summary = "2.获取用户信息")
    @GetMapping("/info")
    public Response<WxUserVo> info() {
        final Long userId = EasyRequestUtil.getWxUserId();
        return Response.ok(wxUserService.getByUserId(userId));
    }
}