package com.laker.admin.module.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class IndexController {

    @GetMapping({"", "/index", "/"})
    public String adminIndex() {
        return "redirect:/admin/index.html";
    }

}