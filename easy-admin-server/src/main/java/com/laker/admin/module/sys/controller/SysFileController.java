package com.laker.admin.module.sys.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.config.LakerConfig;
import com.laker.admin.framework.exception.BusinessException;
import com.laker.admin.framework.ext.mybatis.UserInfoAndPowers;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.framework.utils.EasyAdminSecurityUtils;
import com.laker.admin.module.sys.entity.SysFile;
import com.laker.admin.module.sys.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/sys/file")
public class SysFileController {
    @Autowired
    ISysFileService sysFileService;
    @Autowired
    LakerConfig lakerConfig;

    @GetMapping
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<SysFile> queryWrapper = new QueryWrapper().lambda();
        Page pageList = sysFileService.page(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @PostMapping("/upload")
    public Response upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "name", required = false) String name) {
        // 这里实际项目中文件基本都存储在oss上，这里仅做演示
        String fileName = file.getOriginalFilename();
        File dest = new File(new File(lakerConfig.getOssFile().getPath()).getAbsolutePath() + "/" + fileName);
        String filePath = lakerConfig.getOssFile().getDomain() + "/" + lakerConfig.getOssFile().getPath() + "/" + fileName;
        try {
            file.transferTo(dest);
            SysFile sysFile = new SysFile();
            UserInfoAndPowers currentUserInfo = EasyAdminSecurityUtils.getCurrentUserInfo();
            sysFile.setUserId(currentUserInfo.getUserId());
            sysFile.setNickName(currentUserInfo.getNickName());
            sysFile.setFilePath(filePath);
            sysFile.setFileName(fileName);
            sysFile.setCreateTime(LocalDateTime.now());
            sysFileService.save(sysFile);
        } catch (IOException e) {
            throw new BusinessException("文件上传失败");
        }

        return Response.ok(filePath);
    }

    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysFileService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysFileService.removeById(id));
    }

    @DeleteMapping("/batch/{ids}")
    public Response batchRemove(@PathVariable Long[] ids) {
        return Response.ok(sysFileService.removeByIds(CollUtil.toList(ids)));
    }
}