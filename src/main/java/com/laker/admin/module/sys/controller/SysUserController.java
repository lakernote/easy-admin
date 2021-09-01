package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.config.LakerConfig;
import com.laker.admin.framework.PageResponse;
import com.laker.admin.framework.Response;
import com.laker.admin.framework.aop.Metrics;
import com.laker.admin.module.sys.entity.SysRole;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.entity.SysUserRole;
import com.laker.admin.module.sys.pojo.PwdQo;
import com.laker.admin.module.sys.service.ISysRoleService;
import com.laker.admin.module.sys.service.ISysUserRoleService;
import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-05
 */
@RestController
@RequestMapping("/sys/user")
@Metrics
public class SysUserController {
    @Autowired
    ISysUserService sysUserService;
    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    ISysUserRoleService sysUserRoleService;
    @Autowired
    LakerConfig lakerConfig;

    @GetMapping
    @ApiOperation(value = "分页查询")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit,
                                Long deptId,
                                String keyWord) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.eq(deptId != null, SysUser::getDeptId, deptId)
                .and(StrUtil.isNotBlank(keyWord), i -> i.
                        like(StrUtil.isNotBlank(keyWord), SysUser::getUserName, keyWord)
                        .or().like(StrUtil.isNotBlank(keyWord), SysUser::getUserName, keyWord));
        Page pageList = sysUserService.page(roadPage, queryWrapper);

        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新")
    @Transactional
    @SaCheckPermission("user.update")
    public Response saveOrUpdate(@RequestBody SysUser param) {

        if (param.getUserId() == null) {
            // 只有超级管理员才能创建用户
            if (StpUtil.getLoginIdAsLong() != 1L) {
                return Response.error("403", "只有超级管理员才能创建用户!");
            }
            String password = param.getPassword();
            param.setPassword(SecureUtil.sha256(password));
            param.setCreateTime(LocalDateTime.now());
            sysUserService.save(param);
            if (StrUtil.isNotBlank(param.getRoleIds())) {
                this.saveUserRole(param.getUserId(), Arrays.asList(param.getRoleIds().split(",")));
            }
        } else {
            sysUserService.saveOrUpdate(param);
            if (StrUtil.isNotBlank(param.getRoleIds())) {
                this.saveUserRole(param.getUserId(), Arrays.asList(param.getRoleIds().split(",")));
            }
        }
        return Response.ok(sysUserService.saveOrUpdate(param));
    }

    public boolean saveUserRole(Long userId, List<String> roleIds) {
        sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        roleIds.forEach(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(Long.valueOf(roleId));
            sysUserRole.setUserId(userId);
            sysUserRoles.add(sysUserRole);
        });
        return sysUserRoleService.saveBatch(sysUserRoles);
    }


    @PutMapping("/updatePwd")
    @ApiOperation(value = "更新用户密码")
    @SaCheckPermission("user.update.pwd")
    public Response updatePwd(@RequestBody PwdQo param) {

        if (!StrUtil.equals(param.getNewPassword(), param.getConfirmPassword())) {
            return Response.error("500", "两次输入密码不一致");
        }
        long userId = StpUtil.getLoginIdAsLong();
        SysUser sysUser = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUserId, userId)
                .eq(SysUser::getPassword, SecureUtil.sha256(param.getOldPassword())));
        if (sysUser == null) {
            return Response.error("500", "用户名密码错误");
        }
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setPassword(SecureUtil.sha256(param.getNewPassword()));
        sysUserService.updateById(user);
        return Response.ok();
    }

    @PutMapping("/resetPwd/{userId}")
    @ApiOperation(value = "更新用户密码")
    @SaCheckPermission("user.reset.pwd")
    public Response resetPwd(@PathVariable Long userId) {
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setPassword(SecureUtil.sha256(lakerConfig.getDefaultPwd()));
        sysUserService.updateById(user);
        return Response.ok();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysUserService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除")
    @SaCheckPermission("user.delete")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysUserService.removeById(id));
    }

    @GetMapping("/getRoles")
    public Response edit(Long userId) {
        List<SysRole> allRole = sysRoleService.list(null);
        if (userId != null) {
            List<SysUserRole> myRole = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
            allRole.forEach(sysRole -> {
                myRole.forEach(sysUserRole -> {
                    if (sysRole.getRoleId().equals(sysUserRole.getRoleId())) {
                        sysRole.setChecked(true);
                    }
                });
            });
        }
        return Response.ok(allRole);
    }


    @DeleteMapping("/batch/{ids}")
    @ApiOperation(value = "根据批量删除ids删除")
    @SaCheckPermission("dict.delete")
    public Response batchRemove(@PathVariable Long[] ids) {
        return Response.ok(sysUserService.removeByIds(CollUtil.toList(ids)));
    }

}