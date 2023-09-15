package com.laker.admin.framework.model;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author longli
 */
@Schema
public class PageResponse<T> extends Response<T> {

    @Schema(description = "数量")
    private Long count;

    public PageResponse(String code, String msg, T data, Long count) {
        super(code, msg, data);
        this.count = count;
    }

    public static <T> PageResponse<T> ok(T data, Long count) {
        return new PageResponse<>("0", "", data, count);
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
