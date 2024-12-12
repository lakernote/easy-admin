package com.laker.admin.module.wx.miniapp.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laker.admin.module.wx.miniapp.entity.WxUser;
import com.laker.admin.module.wx.miniapp.enums.WxUserTypeEnum;
import com.laker.admin.module.wx.miniapp.mapper.WxUserMapper;
import com.laker.admin.module.wx.miniapp.mapstruct.WxUserBeanMap;
import com.laker.admin.utils.EasyJwt;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WxUserService {

    private final WxMaService wxMaService;
    private final WxUserMapper wxUserMapper;
    private final EasyJwt easyJwt;
    private final WxUserBeanMap wxUserBeanMap;

    public WxUserService(WxMaService wxMaService, WxUserMapper wxUserMapper, EasyJwt easyJwt, WxUserBeanMap wxUserBeanMap) {
        this.wxMaService = wxMaService;
        this.wxUserMapper = wxUserMapper;
        this.easyJwt = easyJwt;
        this.wxUserBeanMap = wxUserBeanMap;
    }

    public Map<String, Object> login(String code, boolean test) {
        final WxMaJscode2SessionResult session = getSession(code, test);
        String openId = session.getOpenid();
        String sessionKey = session.getSessionKey();
        String unionid = session.getUnionid();
        WxUser wxUser = wxUserMapper.selectOne(new QueryWrapper<WxUser>().eq("open_id", openId));
        // 查询用户是否存在
        if (wxUser == null) {          // 如果是新用户，创建用户信息
            wxUser = new WxUser();
            wxUser.setOpenId(openId);
            wxUser.setSessionKey(sessionKey);
            wxUser.setUnionId(unionid);
            wxUser.setRoleType(WxUserTypeEnum.GUEST);
            wxUser.setAvatar("https://cdn.laker.com.cn/2021/08/04/1628050131.png");
            wxUser.setNickName("游客" + RandomUtil.randomString(6));
            wxUserMapper.insert(wxUser);
        } else {
            wxUser.setSessionKey(sessionKey);
            wxUserMapper.updateById(wxUser);
        }


        // 生成 JWT Token
        String token = easyJwt.generateToken(wxUser.getId().toString());

        // 返回用户信息和 token
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", wxUserBeanMap.entityToVo(wxUser));
        return result;
    }

    private WxMaJscode2SessionResult getSession(String code, boolean test) {
        if (test) {
            WxMaJscode2SessionResult session = new WxMaJscode2SessionResult();
            session.setOpenid(RandomUtil.randomString(16));
            session.setSessionKey("test");
            return session;
        }
        WxMaJscode2SessionResult session = null;
        try {
            session = wxMaService.getUserService().getSessionInfo(code);
        } catch (WxErrorException e) {
            throw new NotLoginException("登录失败", "登录失败", "");
        }
        return session;
    }
}
