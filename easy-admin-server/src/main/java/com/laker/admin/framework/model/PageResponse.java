package com.laker.admin.framework.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author longli
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> extends Response<T> {

    @Schema(description = "数量")
    private Long total;

    private T list;

    public PageResponse(Integer code, String msg, T list, Long total) {
        super(code, msg, null);
        this.total = total;
        this.list = list;
    }

    public static <T> PageResponse<T> ok(T list, Long count) {
        return new PageResponse<>(SUCCESS_CODE, "", list, count);
    }

    public Long getCount() {
        return total;
    }

    public void setCount(Long count) {
        this.total = count;
    }
}
