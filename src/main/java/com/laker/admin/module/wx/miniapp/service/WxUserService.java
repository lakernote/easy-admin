package com.laker.admin.module.wx.miniapp.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laker.admin.framework.EasyAdminConstants;
import com.laker.admin.framework.exception.BusinessException;
import com.laker.admin.module.wx.miniapp.config.JwtProperties;
import com.laker.admin.module.wx.miniapp.dto.WxUserVo;
import com.laker.admin.module.wx.miniapp.entity.WxUser;
import com.laker.admin.module.wx.miniapp.enums.WxUserTypeEnum;
import com.laker.admin.module.wx.miniapp.mapper.WxUserMapper;
import com.laker.admin.module.wx.miniapp.mapstruct.WxUserBeanMap;
import com.laker.admin.utils.EasyJwt;
import io.jsonwebtoken.Claims;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WxUserService {

    @Value("${wx.miniapp.debug:false}")
    private boolean debug;

    private final WxMaService wxMaService;
    private final WxUserMapper wxUserMapper;
    private final EasyJwt easyJwt;
    private final WxUserBeanMap wxUserBeanMap;
    private final JwtProperties jwtProperties;

    public WxUserService(WxMaService wxMaService, WxUserMapper wxUserMapper, EasyJwt easyJwt, WxUserBeanMap wxUserBeanMap, JwtProperties jwtProperties) {
        this.wxMaService = wxMaService;
        this.wxUserMapper = wxUserMapper;
        this.easyJwt = easyJwt;
        this.wxUserBeanMap = wxUserBeanMap;
        this.jwtProperties = jwtProperties;
    }

    public Map<String, Object> login(String code) {
        final WxMaJscode2SessionResult session = getSession(code);
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
            wxUser.setCreateTime(LocalDateTime.now());
            wxUserMapper.insert(wxUser);
        } else {
            // 如果是老用户，更新 sessionKey
            wxUserMapper.update(Wrappers.<WxUser>lambdaUpdate()
                    .set(WxUser::getSessionKey, sessionKey)
                    .set(WxUser::getUpdateTime, LocalDateTime.now())
                    .eq(WxUser::getId, wxUser.getId()));
        }

        // 返回用户信息和 token
        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", easyJwt.generateToken(wxUser.getId(), jwtProperties.getExpiration(), EasyAdminConstants.TokenType.ACCESS_TOKEN));
        result.put("refreshToken", easyJwt.generateToken(wxUser.getId(), jwtProperties.getRefreshExpiration(), EasyAdminConstants.TokenType.REFRESH_TOKEN));
        result.put("user", wxUserBeanMap.entityToVo(wxUser));
        return result;
    }

    private WxMaJscode2SessionResult getSession(String code) {
        if (debug) {
            WxMaJscode2SessionResult session = new WxMaJscode2SessionResult();
            session.setOpenid("debug_openid");
            session.setSessionKey("debug_session_key");
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

    public WxUserVo getByUserId(Long userId) {
        final WxUser wxUser = wxUserMapper.selectById(userId);
        if (wxUser == null) {
            throw new BusinessException("用户不存在");
        }
        return wxUserBeanMap.entityToVo(wxUser);
    }

    public Map<String, Object> renew(String oldRefreshToken) {
        // 验证 refreshToken 的有效性
        Claims claims = easyJwt.parseToken(oldRefreshToken);
        Long userId = claims.get(EasyAdminConstants.USER_ID, Long.class);
        if (userId == null) {
            throw new NotLoginException("登录失败", "登录失败", "");
        }

        // 生成新的 accessToken 和 refreshToken
        String newAccessToken = easyJwt.generateToken(userId, jwtProperties.getExpiration(), EasyAdminConstants.TokenType.ACCESS_TOKEN);
        String newRefreshToken = easyJwt.generateToken(userId, jwtProperties.getRefreshExpiration(), EasyAdminConstants.TokenType.REFRESH_TOKEN);

        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", newAccessToken);
        result.put("refreshToken", newRefreshToken);
        return result;
    }
}
