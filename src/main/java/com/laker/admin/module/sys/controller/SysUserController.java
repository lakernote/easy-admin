package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.config.EasyConfig;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.PageVO;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.entity.SysRole;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.entity.SysUserRole;
import com.laker.admin.module.sys.pojo.FlowAssigneVo;
import com.laker.admin.module.sys.pojo.PwdQo;
import com.laker.admin.module.sys.pojo.UserDto;
import com.laker.admin.module.sys.service.ISysRoleService;
import com.laker.admin.module.sys.service.ISysUserRoleService;
import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-05
 */
@Api(tags = "系统-用户管理")
@ApiSupport(order = 5)
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
    EasyConfig easyConfig;

    @GetMapping
    @ApiOperation(value = "分页查询")
    public PageResponse<List<SysUser>> pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                               @RequestParam(required = false, defaultValue = "10") long limit,
                                               Long deptId,
                                               String keyWord) {
        Page<SysUser> roadPage = new Page<>(page, limit);

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(deptId != null, SysUser::getDeptId, deptId)
                .and(CharSequenceUtil.isNotBlank(keyWord), i ->
                        i.like(CharSequenceUtil.isNotBlank(keyWord), SysUser::getUserName, keyWord)
                                .or()
                                .like(CharSequenceUtil.isNotBlank(keyWord), SysUser::getNickName, keyWord));

        Page<SysUser> pageList = sysUserService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @GetMapping("/pageComplexAll")
    @ApiOperation(value = "复杂分页查询示例")
    public PageResponse<List<SysUser>> pageComplexAll(PageVO page, UserDto userDto) {
        Page<SysUser> roadPage = page.toPage();
        Page<SysUser> pageList = sysUserService.page(roadPage, userDto.queryWrapper());
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }


    @GetMapping("/getAll")
    @ApiOperation(value = "获取所有用户")
    public Response<List<FlowAssigneVo>> getAll() {
        List<SysUser> list = sysUserService.list();
        if (CollUtil.isEmpty(list)) {
            return Response.ok();
        }
        List<FlowAssigneVo> collect = list.stream().map(sysUser -> FlowAssigneVo.builder()
                .name(sysUser.getNickName()).value(String.valueOf(sysUser.getUserId())).build()).collect(Collectors.toList());
        collect.add(0, FlowAssigneVo.builder().name("请选择").value("").build());
        collect.add(1, FlowAssigneVo.builder().name("当前用户").value("CurrentUser").build());
        return Response.ok(collect);
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新")
    @Transactional
    public Response<Void> saveOrUpdate(@RequestBody SysUser param) {

        if (param.getUserId() == null && param.getDeptId() == null) {
            return Response.error500("请选择部门");
        }

        if (param.getUserId() == null) {
            // 只有超级管理员才能创建用户
            if (StpUtil.getLoginIdAsLong() != 1L) {
                return Response.error403("只有超级管理员才能创建用户!");
            }
            String password = param.getPassword();
            param.setPassword(SecureUtil.sha256(password));
            param.setCreateTime(LocalDateTime.now());
            sysUserService.save(param);
        } else {
            sysUserService.saveOrUpdate(param);
        }
        if (CharSequenceUtil.isNotBlank(param.getRoleIds())) {
            ArrayList<String> list = CollUtil.newArrayList(param.getRoleIds().split(","));
            list.add(param.getDataRoleId());
            this.saveUserRole(param.getUserId(), list);
        }
        return Response.ok();
    }


    @PutMapping("/switch")
    @ApiOperation(value = "用户启用停用开关")
    @SaCheckPermission("user.switch")
    public Response<Boolean> userSwitch(@RequestBody SysUser param) {
        return Response.ok(sysUserService.updateById(param));
    }

    private void saveUserRole(Long userId, List<String> roleIds) {
        sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        roleIds.forEach(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(Long.valueOf(roleId));
            sysUserRole.setUserId(userId);
            sysUserRoles.add(sysUserRole);
        });
        sysUserRoleService.saveBatch(sysUserRoles);
    }


    @PutMapping("/updatePwd")
    @ApiOperation(value = "更新用户密码")
    @SaCheckPermission("user.update.pwd")
    public Response<Void> updatePwd(@RequestBody PwdQo param) {

        if (!CharSequenceUtil.equals(param.getNewPassword(), param.getConfirmPassword())) {
            return Response.error("500", "两次输入密码不一致");
        }
        long userId = StpUtil.getLoginIdAsLong();
        SysUser sysUser = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUserId, userId)
                .eq(SysUser::getPassword, SecureUtil.sha256(param.getOldPassword())));
        if (sysUser == null) {
            return Response.error("500", "旧密码错误");
        }
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setPassword(SecureUtil.sha256(param.getNewPassword()));
        sysUserService.updateById(user);
        return Response.ok();
    }

    @PutMapping("/resetPwd/{userId}")
    @ApiOperation(value = "重置用户密码")
    @SaCheckPermission("user.reset.pwd")
    @SaCheckRole("admin")
    public Response<Void> resetPwd(@PathVariable Long userId) {
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setPassword(SecureUtil.sha256(easyConfig.getDefaultPwd()));
        sysUserService.updateById(user);
        return Response.ok();
    }

    @PutMapping("/updateAvatar")
    @ApiOperation(value = "更新用户头像")
    @SaCheckPermission("user.updateAvatar")
    public Response<Void> resetPwd(@RequestBody UserDto userDto) {
        long userId = StpUtil.getLoginIdAsLong();
        SysUser user = new SysUser();
        user.setAvatar(userDto.getAvatar());
        user.setUserId(userId);
        sysUserService.updateById(user);
        return Response.ok();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    public Response<SysUser> get(@PathVariable Long id) {
        return Response.ok(sysUserService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除")
    @SaCheckPermission("user.delete")
    public Response<Boolean> delete(@PathVariable Long id) {
        return Response.ok(sysUserService.removeById(id));
    }

    @GetMapping("/getRoles")
    public Response<List<SysRole>> edit(Long userId, Integer roleType) {
        List<SysRole> allRole = sysRoleService.list(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleType, roleType));
        if (userId != null) {
            List<SysUserRole> myRole = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
                    .eq(SysUserRole::getUserId, userId));
            allRole.forEach(sysRole -> myRole.forEach(sysUserRole -> {
                if (sysRole.getRoleId().equals(sysUserRole.getRoleId())) {
                    sysRole.setChecked(true);
                }
            }));
        } else {
            allRole.stream()
                    .filter(sysRole -> sysRole.getRoleType() == 1)
                    .findFirst()
                    .ifPresent(sysRole -> sysRole.setChecked(true));
            allRole.stream()
                    .filter(sysRole -> sysRole.getRoleType() == 2)
                    .findFirst()
                    .ifPresent(sysRole -> sysRole.setChecked(true));
        }
        return Response.ok(allRole);
    }


    @DeleteMapping("/batch/{ids}")
    @ApiOperation(value = "根据批量删除ids删除")
    @SaCheckPermission("dict.delete")
    public Response<Boolean> batchRemove(@PathVariable Long[] ids) {
        return Response.ok(sysUserService.removeByIds(CollUtil.toList(ids)));
    }

}
