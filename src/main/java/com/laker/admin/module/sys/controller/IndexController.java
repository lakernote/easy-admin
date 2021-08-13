package com.laker.admin.module.sys.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * /admin/** 无需check login
 */
@Controller
@Slf4j
public class IndexController {

    @GetMapping({"/admin", "/admin/index", "/admin/"})
    public String adminIndex() {
        return "redirect:/admin/index.html";
    }


    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response) throws IOException {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100, 40, 4, 2);
        log.info("当前验证码：{}", captcha.getCode());
        captcha.write(response.getOutputStream());
    }
}