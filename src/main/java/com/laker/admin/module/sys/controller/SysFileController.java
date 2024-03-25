package com.laker.admin.module.sys.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.exception.BusinessException;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.entity.SysFile;
import com.laker.admin.module.sys.service.storage.StorageFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2022-02-21
 */
@Api(tags = "系统-文件管理")
@ApiSupport(order = 5)
@RestController
@RequestMapping("/sys/file")
public class SysFileController {

    // 允许上传的文件类型
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".pdf");

    // 允许上传的文件最大大小
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB
    @Autowired
    StorageFacade storageFacade;

    @GetMapping
    @ApiOperation(value = "分页查询")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit,
                                String keyWord) {
        Page query = new Page<>(page, limit);
        Page pageList = storageFacade.page(query, keyWord);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @SneakyThrows
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public Response<String> upload(@RequestParam("file") MultipartFile file) {
        // 检查上传文件是否是允许的文件类型
        if (!isAllowedExtension(Objects.requireNonNull(file.getOriginalFilename()))) {
            throw new BusinessException("上传的文件类型不允许！");
        }

        // 检查上传文件是否超出允许的大小限制
        if (!isAllowedSize(file.getSize())) {
            throw new BusinessException("上传的文件大小超过限制！");
        }

        if (FileUtil.containsInvalid(file.getOriginalFilename())) {
            throw new BusinessException("文件名存在非法字符！");
        }
        SysFile store = storageFacade.store(file.getInputStream(), file.getSize(),
                file.getContentType(), file.getOriginalFilename());
        return Response.ok(store.getFilePath());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除")
    public Response delete(@PathVariable Long id) {
        storageFacade.delete(id);
        return Response.ok();
    }

    @DeleteMapping("/batch/{ids}")
    @ApiOperation(value = "根据批量删除ids删除")
    public Response batchRemove(@PathVariable Long[] ids) {
        storageFacade.delete(ids);
        return Response.ok();
    }

    private static boolean isAllowedExtension(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".")).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }

    private static boolean isAllowedSize(long fileSize) {
        return fileSize <= MAX_FILE_SIZE;
    }
}
