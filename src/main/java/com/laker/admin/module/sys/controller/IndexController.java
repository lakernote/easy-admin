package com.laker.admin.module.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * /admin/** 无需check login
 */
@Controller
public class IndexController {

    @GetMapping({"/admin", "/admin/index", "/admin/"})
    public String adminIndex() {
        return "redirect:/admin/index.html";
    }

}