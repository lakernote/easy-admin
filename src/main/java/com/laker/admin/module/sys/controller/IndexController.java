package com.laker.admin.module.sys.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import com.laker.admin.framework.Response;
import com.laker.admin.framework.cache.ICache;
import com.wf.captcha.SpecCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * /admin/** 无需check login
 */
@Controller
@Slf4j
public class IndexController {
    @Autowired
    ICache iCache;

    @GetMapping({"/admin", "/admin/index", "/admin/"})
    public String adminIndex() {
        return "redirect:/admin/index.html";
    }

    /**
     * 详情参考：https://gitee.com/whvse/EasyCaptcha
     *
     * @return
     */
    @GetMapping("/captcha")
    @ResponseBody
    public Response captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String uid = IdUtil.simpleUUID();
        log.info("当前uid:{},验证码：{}", uid, verCode);
        // 前后端分离，这时还未有会话信息，用于确定uid和验证码的一一映射关系，防止多人串码
        // 把uuid和图片码一起给前端，验证的时候再一起回来
        iCache.put(uid, verCode, 3 * 60);

        return Response.ok(Dict.create().set("uid", uid).set("image", specCaptcha.toBase64()));
    }
}