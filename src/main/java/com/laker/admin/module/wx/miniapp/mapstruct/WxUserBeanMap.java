package com.laker.admin.module.wx.miniapp.mapstruct;

import com.laker.admin.module.wx.miniapp.dto.WxUserVo;
import com.laker.admin.module.wx.miniapp.entity.WxUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WxUserBeanMap {
    WxUserVo entityToVo(WxUser entity);
}
