package com.laker.admin.module.wx.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.model.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final WxMaService wxMaService;

    public WxMiniAppController(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }

    /**
     * <p>这个接口会接收微信小程序传递过来的code参数，然后通过Java SDK获取到用户的openid和sessionKey，最后返回给小程序端。</p>
     *
     * @param code 前端wx.login获取的code
     */
    @GetMapping("/login")
    public Response<Void> login(@NotBlank(message = "登录凭证不能为空") String code) {
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            //下面做自己的业务逻辑，大概就是去数据库查询信息存不存在，不存在创建用户信息，保存openid
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return Response.error401();
        }
        return Response.ok();
    }
}