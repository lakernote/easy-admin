package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxDumper;
import com.laker.admin.config.LakerConfig;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.pojo.NginxQo;
import com.laker.admin.module.sys.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * nginx前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-11
 */
@RestController
@RequestMapping("/sys/nginx")
public class NginxController {
    @Autowired
    ISysDeptService sysDeptService;
    @Autowired
    LakerConfig lakerConfig;

    @GetMapping
    public Response get(@RequestParam(required = false) String path) {
        NgxConfig conf = null;
        try {
            if (StrUtil.isBlank(path)) {
                path = lakerConfig.getNginx().getConfPath();
            }
            conf = NgxConfig.read(path);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error("500", "路径错误：" + path);
        }
        String content = new NgxDumper(conf).dump();
        return Response.ok(content);
    }

    @PostMapping
    @SaCheckPermission("nginx.update")
    public Response update(@RequestBody NginxQo nginxQo) {
        if (StrUtil.isBlank(nginxQo.getPath())) {
            nginxQo.setPath(lakerConfig.getNginx().getConfPath());
        }
        FileUtil.rename(new File(nginxQo.getPath()), "nginx.conf-bak-" + DateUtil.format(new Date(), "yyyy-MM-dd-HH-mm-ss"), true);
        FileUtil.writeString(nginxQo.getContext(), new File(nginxQo.getPath()), "utf-8");
        return Response.ok();
    }

    @PostMapping("/check")
    @SaCheckPermission("nginx.check")
    public Response check(@RequestBody NginxQo nginxQo) {
        if (StrUtil.isBlank(nginxQo.getPath())) {
            nginxQo.setPath(lakerConfig.getNginx().getConfPath());
        }
        String res = RuntimeUtil.execForStr("nginx -t -c " + nginxQo.getPath());
        return Response.ok(res);
    }

    @PostMapping("/reload")
    @SaCheckPermission("nginx.reload")
    public Response reload(@RequestBody NginxQo nginxQo) {
        if (StrUtil.isBlank(nginxQo.getPath())) {
            nginxQo.setPath(lakerConfig.getNginx().getConfPath());
        }
        String res = RuntimeUtil.execForStr("nginx -s reload -c " + nginxQo.getPath());
        return Response.ok(res);
    }

    @PostMapping("/start")
    @SaCheckPermission("nginx.start")
    public Response start(@RequestBody NginxQo nginxQo) {
        if (StrUtil.isBlank(nginxQo.getPath())) {
            nginxQo.setPath(lakerConfig.getNginx().getConfPath());
        }
        String res = RuntimeUtil.execForStr("nginx -c " + nginxQo.getPath());
        return Response.ok(res);
    }

    @PostMapping("/stop")
    @SaCheckPermission("nginx.stop")
    public Response stop() {
        String res = RuntimeUtil.execForStr("nginx -s quit");
        return Response.ok(res);
    }
}