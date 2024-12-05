package com.laker.admin.module.sys.mapstruct;

import com.laker.admin.module.sys.dto.user.UserBO;
import com.laker.admin.module.sys.dto.user.UserRequest;
import com.laker.admin.module.sys.dto.user.UserVO;
import com.laker.admin.module.sys.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserBeanMap {
    UserVO entityToVo(SysUser entity);

    UserBO requestToBo(UserRequest request);

    SysUser boToEntity(UserBO bo);

    SysUser requestToEntity(UserRequest userRequest);
}
