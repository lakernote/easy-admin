package com.laker.admin.framework.ext.mvc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 用于映射查询请求参数 分页、排序和过滤功能
 * ?page=1&size=2&sort=rank|asc,zip_code|desc&filter=city|eq|beijing
 * 被 PageRequestArgumentResolver 解析
 */
@Builder
@ToString
@Getter
@Schema(description = "查询请求参数 分页、排序和过滤功能", example = "?page=1&size=2&sort=rank|asc,zip_code|desc&filter=city|eq|beijing")
public class PageRequest {
    @Schema(description = "页码，从1开始，默认1")
    private int page;
    @Schema(description = "每页大小，默认10")
    private int size;

    /**
     * 用于在 JSON 序列化和反序列化过程中忽略某个属性。
     */
    @JsonIgnore
    private QueryWrapper queryWrapper;

    public Page toPage() {
        return new Page<>(page, size);
    }
}