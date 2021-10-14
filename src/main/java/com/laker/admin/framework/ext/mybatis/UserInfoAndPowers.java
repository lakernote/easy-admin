package com.laker.admin.framework.ext.mybatis;

import com.laker.admin.module.sys.entity.SysDataPower;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndPowers {
    private Long userId;
    private String userName;
    private String nickName;
    private Long deptId;
    private String deptName;
    private Map metaData;
    private String deptTableAlias;
    private String userTableAlias;
    private String sql;
    private Set<Long> deptIds;
    private List<SysDataPower> userDataPowers;

    public Boolean isSuperAdmin() {
        return userId.longValue() == 1L;
    }

}
