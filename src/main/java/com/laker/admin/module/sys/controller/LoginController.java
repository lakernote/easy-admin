package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.EasyAdminConstants;
import com.laker.admin.framework.utils.PageDtoUtil;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.framework.aop.Metrics;
import com.laker.admin.framework.cache.ICache;
import com.laker.admin.framework.ext.mybatis.UserInfoAndPowers;
import com.laker.admin.framework.ext.satoken.MySaTokenListener;
import com.laker.admin.framework.ext.satoken.OnlineUser;
import com.laker.admin.module.sys.entity.SysDept;
import com.laker.admin.module.sys.entity.SysRole;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.entity.SysUserRole;
import com.laker.admin.module.sys.service.ISysDeptService;
import com.laker.admin.module.sys.service.ISysRoleService;
import com.laker.admin.module.sys.service.ISysUserRoleService;
import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    ISysUserRoleService sysUserRoleService;
    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    ISysDeptService sysDeptService;

    @PostMapping("/api/v1/login")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "登录")
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
        List<SysUserRole> userRoles = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getUserId()));
        List<Long> roleIds = userRoles.stream().map(sysUserRole -> sysUserRole.getRoleId()).collect(Collectors.toList());
        List<SysRole> roleList = sysRoleService.list(Wrappers.<SysRole>lambdaQuery().in(SysRole::getRoleId, roleIds).eq(SysRole::getEnable, true).eq(SysRole::getRoleType, 2));
        UserInfoAndPowers.UserInfoAndPowersBuilder userInfoAndPowersBuilder = UserInfoAndPowers.builder()
                .deptId(sysUser.getDeptId())
                .userId(sysUser.getUserId())
                .nickName(sysUser.getNickName());
        if (CollUtil.size(roleList) == 1) {
            userInfoAndPowersBuilder.filterType(roleList.get(0).getDataType());
        } else {
            log.warn("用户：{}，数据权限配置异常，数据权限：{}", sysUser.getUserId(), CollUtil.size(roleList));
        }
        StpUtil.getSession().set(EasyAdminConstants.CURRENT_USER, userInfoAndPowersBuilder.build());

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
        SysUser user = sysUserService.getById(StpUtil.getLoginIdAsLong());
        SysDept dept = sysDeptService.getById(user.getDeptId());
        if (dept != null) {
            user.setDeptName(dept.getDeptName());
        }
        return Response.ok(user);
    }

    @GetMapping("/api/v1/onlineUsers")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取在线用户信息")
    public PageResponse onlineUsers(@RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "10") int limit) {
        List<String> sessionIds = StpUtil.searchTokenValue(null, -1, 1000);
        log.info("当前用户：{}", Arrays.toString(sessionIds.toArray()));
        MySaTokenListener.ONLINE_USERS.sort((o1, o2) -> DateUtil.compare(o2.getLoginTime(), o1.getLoginTime()));
        PageDtoUtil pageDto = PageDtoUtil.getPageDto(MySaTokenListener.ONLINE_USERS, page, limit);
        log.warn("stp用户数：{}，memory用户数：{}", sessionIds.size(), pageDto.getTotal());
        List<OnlineUser> pageList = (List<OnlineUser>) pageDto.getPageList();
        pageList.forEach(onlineUser -> {
            String keyLastActivityTime = StpUtil.stpLogic.splicingKeyLastActivityTime(onlineUser.getTokenValue());
            String lastActivityTimeString = SaManager.getSaTokenDao().get(keyLastActivityTime);
            if (lastActivityTimeString != null) {
                long lastActivityTime = Long.parseLong(lastActivityTimeString);
                onlineUser.setLastActivityTime(DateUtil.date(lastActivityTime));
            }
        });
        return PageResponse.ok(pageList, (long) pageDto.getTotal());
    }

    @GetMapping("/api/v1/kickOffline")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "踢人下线")
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
