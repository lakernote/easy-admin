package com.laker.admin.module.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laker.admin.module.sys.entity.SysRolePower;
import com.laker.admin.module.sys.mapper.SysRolePowerMapper;
import com.laker.admin.module.sys.service.ISysRolePowerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author laker
 * @since 2021-08-11
 */
@Service
public class SysRolePowerServiceImpl extends ServiceImpl<SysRolePowerMapper, SysRolePower> implements ISysRolePowerService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRolePower(Long roleId, String powerIds) {
        List<String> stringList = Arrays.asList(powerIds.split(","));
        this.remove(Wrappers.<SysRolePower>lambdaQuery().eq(SysRolePower::getRoleId, roleId));
        List<SysRolePower> rolePowers = new ArrayList<>();
        stringList.forEach(powerId -> {
            SysRolePower sysRolePower = new SysRolePower();
            sysRolePower.setRoleId(roleId);
            sysRolePower.setPowerId(Long.valueOf(powerId));
            rolePowers.add(sysRolePower);
        });
        return this.saveBatch(rolePowers);
    }
}
