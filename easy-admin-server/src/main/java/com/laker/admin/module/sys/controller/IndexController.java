package com.laker.admin.module.sys.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import com.laker.admin.framework.aop.ratelimit.LimitType;
import com.laker.admin.framework.aop.ratelimit.RateLimit;
import com.laker.admin.framework.cache.ICache;
import com.laker.admin.framework.model.Response;
import com.laker.admin.framework.utils.EasyImageUtils;
import com.wf.captcha.ArithmeticCaptcha;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
    @RateLimit(key = "captcha", period = 1, limit = 2, limitType = LimitType.IP)
    public Response<Dict> captcha() {
//        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 中文gif类型
//        ChineseGifCaptcha specCaptcha = new ChineseGifCaptcha(130, 48, 4);
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
        captcha.text();  // 获取运算的结果：5
        String verCode = captcha.text().toLowerCase();
        String uid = IdUtil.simpleUUID();
        log.info("当前uid:{},验证码：{}", uid, verCode);
        // 前后端分离，这时还未有会话信息，用于确定uid和验证码的一一映射关系，防止多人串码
        // 把uuid和图片码一起给前端，验证的时候再一起回来
        iCache.put(uid, verCode, 3 * 60);

        return Response.ok(Dict.create().set("uid", uid).set("image", captcha.toBase64()));
    }

    /**
     * 缩略图
     * http://localhost:8080/thumbnail?url=http://localhost:8080/admin/admin/images/wx.jpg
     */
    @GetMapping("/thumbnail")
    public void thumbnail(String url, HttpServletResponse response,
                          @RequestParam(required = false, defaultValue = "1") int type,
                          @RequestParam(required = false, defaultValue = "100") int width,
                          @RequestParam(required = false, defaultValue = "100") int height) throws IOException {
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        switch (type) {
            case 1: // 预览
                response.setContentType("image/" + FileUtil.getSuffix(url) + "; charset=utf-8");
                break;
            case 2: // 下载
                response.addHeader("Content-Disposition", "attachment;filename="
                        + new String(FileUtil.mainName(url).getBytes("utf-8"), "iso-8859-1") + "." + FileUtil.getSuffix(url));
                response.setContentType("application/octet-stream");
                break;
            default:
                response.setContentType("image/" + FileUtil.getSuffix(url) + "; charset=utf-8");

        }
        EasyImageUtils.compressBysize(url, out, width, height);
    }

}