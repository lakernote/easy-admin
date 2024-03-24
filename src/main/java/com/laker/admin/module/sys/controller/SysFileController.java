package com.laker.admin.module.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
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
    public Response upload(@RequestParam("file") MultipartFile file,
                           @RequestParam(value = "name", required = false) String name) {
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
}
