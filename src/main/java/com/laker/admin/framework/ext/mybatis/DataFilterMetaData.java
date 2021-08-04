package com.laker.admin.framework.ext.mybatis;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class DataFilterMetaData {
    DataFilterTypeEnum filterType = DataFilterTypeEnum.ALL;
    private Long userId;
    private String userName;
    private Long deptId;
    private String deptName;
    private Map metaData;
    private String deptTableAlias;
    private String userTableAlias;
    private String sql;
    private Set<Long> deptIds;
}
