package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.exception.BusinessException;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.framework.model.ResultTree;
import com.laker.admin.module.sys.entity.SysPower;
import com.laker.admin.module.sys.entity.SysRole;
import com.laker.admin.module.sys.entity.SysRolePower;
import com.laker.admin.module.sys.service.ISysMenuService;
import com.laker.admin.module.sys.service.ISysRolePowerService;
import com.laker.admin.module.sys.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-11
 */
@Api(tags = "系统-角色管理")
@ApiSupport(order = 5)
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {
    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    ISysMenuService sysMenuService;
    @Autowired
    ISysRolePowerService sysRolePowerService;

    @GetMapping
    @ApiOperation(value = "分页查询")
    public PageResponse<List<SysRole>> pageAll(@RequestParam(required = false, defaultValue = "1") long current,
                                               @RequestParam(required = false, defaultValue = "10") long size,
                                               Integer roleType) { // 1是菜单接口角色，2为数据角色
        Page<SysRole> roadPage = new Page<>(current, size);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(roleType != null, SysRole::getRoleType, roleType);
        Page<SysRole> pageList = sysRoleService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新")
    @SaCheckPermission("role.update")
    public Response saveOrUpdate(@RequestBody SysRole param) {
        return Response.ok(sysRoleService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    public Response<SysRole> get(@PathVariable Long id) {
        return Response.ok(sysRoleService.getById(id));
    }


    /**
     * 根据角色id查询角色权限
     * 如果角色是菜单角色 则剔除数据权限
     * 如果角色是数据权限 则剔除非数据权限
     *
     * @param roleId
     * @return
     */
    @GetMapping("/getRolePower")
    @ApiOperation(value = "根据角色id查询角色权限")
    public ResultTree getRolePower(Long roleId) {
        SysRole role = sysRoleService.getById(roleId);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }
        List<SysPower> allPower = null;
        // 菜单角色
        if (role.getRoleType().equals(SysRole.ROLE_TYPE_MENU)) {
            // 菜单
            allPower = sysMenuService.list(Wrappers.<SysPower>lambdaQuery().in(SysPower::getType, 0, 1, 2));
        } else {
            // 数据
            allPower = sysMenuService.list(Wrappers.<SysPower>lambdaQuery().in(SysPower::getType, 0, 1, 3));
        }

        List<SysRolePower> myPower = sysRolePowerService.list(Wrappers.<SysRolePower>lambdaQuery().eq(SysRolePower::getRoleId, roleId));
        allPower.forEach(sysPower -> {
            myPower.forEach(sysRolePower -> {
                if (sysRolePower.getPowerId().equals(sysPower.getMenuId())) {
                    sysPower.setCheckArr("1");
                }
            });
        });

        return ResultTree.data(allPower);
    }

    @PutMapping("/saveRolePower")
    @ApiOperation(value = "保存角色权限数据")
    @SaCheckPermission("role.update.power")
    public Response<Boolean> saveRolePower(Long roleId, String powerIds) {
        return Response.ok(sysRolePowerService.saveRolePower(roleId, powerIds));
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除")
    @SaCheckPermission("role.delete")
    public Response<Boolean> delete(@PathVariable Long id) {
        return Response.ok(sysRoleService.removeById(id));
    }

    @DeleteMapping("/batch/{ids}")
    @ApiOperation(value = "根据批量删除ids删除")
    @SaCheckPermission("role.delete")
    public Response<Boolean> batchRemove(@PathVariable Long[] ids) {
        return Response.ok(sysRoleService.removeByIds(CollUtil.toList(ids)));
    }
}
