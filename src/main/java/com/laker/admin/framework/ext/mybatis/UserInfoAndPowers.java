package com.laker.admin.framework.ext.mybatis;

import com.laker.admin.module.enums.DataFilterTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class UserInfoAndPowers {
    DataFilterTypeEnum filterType = DataFilterTypeEnum.SELF;
    private Long userId;
    private String userName;
    private Long deptId;
    private String deptName;
    private Map metaData;
    private String deptTableAlias;
    private String userTableAlias;
    private String sql;
    private Set<Long> deptIds;

    public Boolean isSuperAdmin() {
        return userId.longValue() == 1L;
    }

}
