package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.EasyAdminConstants;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.framework.cache.ICache;
import com.laker.admin.framework.ext.mybatis.UserDataPower;
import com.laker.admin.framework.ext.mybatis.UserInfoAndPowers;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.entity.SysDept;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.pojo.LoginDto;
import com.laker.admin.module.sys.service.ISysDeptService;
import com.laker.admin.module.sys.service.ISysRoleService;
import com.laker.admin.module.sys.service.ISysUserRoleService;
import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order = 2)
@RestController
@Slf4j
@Metrics
public class LoginController {

    @Autowired
    ISysUserService sysUserService;
    @Autowired
    ICache iCache;
    @Autowired
    ISysUserRoleService sysUserRoleService;
    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    ISysDeptService sysDeptService;

    @PostMapping("/api/v1/login")
    @ApiOperationSupport(order = 1)
     @Operation(summary = "登录")
    public Response login(@Validated @RequestBody LoginDto loginDto) {
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
        if (sysUser.getEnable().intValue() == 0) {
            return Response.error("5001", "用户:" + loginDto.getUsername() + "已被禁用");
        }
        StpUtil.login(sysUser.getUserId());
        // 获取用户的数据权限
        List<UserDataPower> userDataPowers = sysUserService.getUserDataPowers(sysUser.getUserId());
        UserInfoAndPowers.UserInfoAndPowersBuilder userInfoAndPowersBuilder = UserInfoAndPowers.builder()
                .deptId(sysUser.getDeptId())
                .userId(sysUser.getUserId())
                .nickName(sysUser.getNickName())
                .userDataPowers(userDataPowers);
        StpUtil.getSession().set(EasyAdminConstants.CURRENT_USER, userInfoAndPowersBuilder.build());
        return Response.ok(StpUtil.getTokenInfo());
    }


    @GetMapping("/api/v1/tokenInfo")
    @ApiOperationSupport(order = 2)
     @Operation(summary = "获取当前会话的token信息")
    public Response tokenInfo() {
        return Response.ok(StpUtil.getTokenInfo());
    }


    @GetMapping("/api/v1/userInfo")
    @ApiOperationSupport(order = 2)
     @Operation(summary = "获取当前用户信息")
    public Response userInfo() {
        SysUser user = sysUserService.getById(StpUtil.getLoginIdAsLong());
        SysDept dept = sysDeptService.getById(user.getDeptId());
        if (dept != null) {
            user.setDeptName(dept.getDeptName());
        }
        return Response.ok(user);
    }


    @GetMapping("/api/v1/kickOffline")
    @ApiOperationSupport(order = 2)
     @Operation(summary = "踢人下线")
    @SaCheckPermission("online.user.kick")
    public Response kickOffline(String token) {
        log.info("踢人下线，token:{}", token);
        if (StpUtil.getTokenValue().equals(token)) {
            return Response.error("500", "不能踢自己啊老弟");
        }
        StpUtil.logoutByTokenValue(token);
        return Response.ok();
    }

    @GetMapping("/api/v1/loginOut")
    @ApiOperationSupport(order = 3)
     @Operation(summary = "登出")
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
