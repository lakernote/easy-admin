package com.laker.admin.framework.ext.mybatis;

import com.laker.admin.module.enums.DataFilterTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataPower {
    private String powerCode;
    private DataFilterTypeEnum dataFilterType;
}
