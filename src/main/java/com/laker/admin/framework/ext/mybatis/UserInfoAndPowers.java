package com.laker.admin.framework.ext.mybatis;

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
    private Map<String, Object> metaData;
    private String deptTableAlias;
    private String userTableAlias;
    private String sql;
    private Set<Long> deptIds;
    private List<UserDataPower> userDataPowers;

    public Boolean isSuperAdmin() {
        return userId.longValue() == 1L;
    }

}
