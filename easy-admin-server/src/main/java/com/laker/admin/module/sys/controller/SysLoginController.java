package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.laker.admin.framework.EasyAdminConstants;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.framework.ext.mybatis.UserDataPower;
import com.laker.admin.framework.ext.mybatis.UserInfoAndPowers;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.dto.AuthLoginRespVO;
import com.laker.admin.module.sys.dto.LoginQo;
import com.laker.admin.module.sys.entity.SysDept;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.service.ISysDeptService;
import com.laker.admin.module.sys.service.ISysRoleService;
import com.laker.admin.module.sys.service.ISysUserRoleService;
import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "系统-认证")
@RestController
@RequestMapping("/sys/auth/v1")
@Slf4j
@Metrics
public class SysLoginController {

    @Autowired
    ISysUserService sysUserService;
    @Autowired
    ISysUserRoleService sysUserRoleService;
    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    ISysDeptService sysDeptService;

    /**
     * 登录接口 忽略认证
     *
     * @param loginQo
     * @return
     */
    @SaIgnore
    @PostMapping("/login")
    @Operation(summary = "登录-使用账号密码")
    public Response<AuthLoginRespVO> login(@Validated @RequestBody LoginQo loginQo) {
        // 单机版：在map中创建了会话，token id等映射关系 // 写入cookie
        SysUser sysUser = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUserName, loginQo.getUsername())
                .eq(SysUser::getPassword, SecureUtil.sha256(loginQo.getPassword())));
        if (sysUser == null) {
            return Response.error(401, "用户名或密码不正确");
        }
        if (sysUser.getEnable() == 0) {
            return Response.error(401, "用户已被禁用");
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
        return Response.ok(AuthLoginRespVO.builder().userId(sysUser.getUserId())
                .accessToken(StpUtil.getTokenInfo().getTokenValue()).build());
    }


    @GetMapping("/api/v1/tokenInfo")
    @ApiOperationSupport(order = 2)
    @Operation(summary = "获取当前会话的token信息")
    public Response tokenInfo() {
        return Response.ok(StpUtil.getTokenInfo());
    }


    @GetMapping("/api/v1/userInfo")
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
            return Response.error(500, "不能踢自己啊老弟");
        }
        StpUtil.logoutByTokenValue(token);
        return Response.ok();
    }

    @DeleteMapping("/logout")
    @Operation(summary = "登出")
    @SaCheckLogin
    public Response<Void> logout() {
        StpUtil.logout();
        return Response.ok();
    }

}
