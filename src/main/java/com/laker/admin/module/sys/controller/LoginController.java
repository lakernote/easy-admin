package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.Response;
import com.laker.admin.framework.aop.Metrics;
import com.laker.admin.framework.cache.ICache;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "认证授权")
@ApiSupport(order = 2)
@RestController
@Slf4j
@Metrics
public class LoginController {

    @Autowired
    ISysUserService sysUserService;
    @Autowired
    ICache iCache;

    @PostMapping("/api/v1/login")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "登录")
    public Response login(@Validated @RequestBody LoginDto loginDto) {
        log.info("login {}", loginDto);
        // 验证码是否正确
        String code = iCache.get(loginDto.getUid());
        if (!StrUtil.equalsIgnoreCase(code, loginDto.getCaptchaCode())) {
            return Response.error("500", "验证码不正确或已失效");
        }
        // 单机版：在map中创建了会话，token id等映射关系 // 写入cookie
        SysUser sysUser = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUserName, loginDto.getUsername())
                .eq(SysUser::getPassword, SecureUtil.sha256(loginDto.getPassword())));
        if (sysUser == null) {
            return Response.error("5001", "用户名或密码不正确");
        }
        StpUtil.login(sysUser.getUserId());
        return Response.ok(StpUtil.getTokenInfo());
    }


    @GetMapping("/api/v1/tokenInfo")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取当前会话的token信息")
    public Response tokenInfo() {
        return Response.ok(StpUtil.getTokenInfo());
    }


    @GetMapping("/api/v1/userInfo")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取当前用户信息")
    public Response userInfo() {
        return Response.ok(sysUserService.getById(StpUtil.getLoginIdAsLong()));
    }

    @GetMapping("/api/v1/loginOut")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "登出")
    @SaCheckLogin
    public Response loginOut() {
        //获取token
        //   1. 尝试从request里读取 tokenName 默认值 satoken
        //   2. 尝试从请求体里面读取
        //   3. 尝试从header里读取
        //   4. 尝试从cookie里读取
        // 删除cookie
        log.info(StpUtil.getLoginIdAsString());
        StpUtil.logout();
        return Response.ok();
    }

}
