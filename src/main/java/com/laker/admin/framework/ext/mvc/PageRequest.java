package com.laker.admin.framework.ext.mvc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 用于映射查询请求参数 分页、排序和过滤功能
 * ?page=1&size=2&sort=rank|asc,zip_code|desc&filter=city|eq|beijing
 */
@Builder
@ToString
@Getter
public class PageRequest {
    @Schema(description = "页码，从1开始，默认1")
    private int page;
    @Schema(description = "每页大小，默认10")
    private int size;
    private QueryWrapper queryWrapper;

    public Page toPage() {
        return new Page<>(page, size);
    }
}