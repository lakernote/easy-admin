package com.laker.admin.module.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.entity.SysFile;
import com.laker.admin.module.sys.service.storage.StorageFacade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    final StorageFacade storageFacade;

    public SysFileController(StorageFacade storageFacade) {
        this.storageFacade = storageFacade;
    }

    @GetMapping
    @Operation(summary = "分页查询")
    public PageResponse<List<SysFile>> pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                               @RequestParam(required = false, defaultValue = "10") long limit,
                                               String keyWord) {
        Page<SysFile> query = new Page<>(page, limit);
        Page<SysFile> pageList = storageFacade.page(query, keyWord);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @SneakyThrows
    @PostMapping("/upload")
    public Response<String> upload(@RequestParam("file") MultipartFile file) {
        SysFile store = storageFacade.store(file.getInputStream(), file.getSize(),
                file.getContentType(), file.getOriginalFilename());
        return Response.ok(store.getFilePath());
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "根据id删除")
    public Response<Void> delete(@PathVariable Long id) {
        storageFacade.delete(id);
        return Response.ok();
    }

    @DeleteMapping("/batch/{ids}")
    @Operation(summary = "根据批量删除ids删除")
    public Response<Void> batchRemove(@PathVariable Long[] ids) {
        storageFacade.delete(ids);
        return Response.ok();
    }
}