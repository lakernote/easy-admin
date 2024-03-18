package com.laker.admin.framework.model;

import com.laker.admin.framework.EasyAdminConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

/**
 * @author longli
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Response<T> {
    public static final Integer SUCCESS_CODE = 0;
    @Schema(description = "响应码，非0 即为异常", example = "0")
    private final Integer code;
    @Schema(description = "响应消息", example = "提交成功")
    private final String message;
    @Schema(description = "响应数据")
    private final T data;
    @Schema(description = "traceId")
    private final String traceId;

    protected Response(Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
        this.traceId = MDC.get(EasyAdminConstants.TRACE_ID);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(SUCCESS_CODE, "操作成功", data);
    }

    public static <Void> Response<Void> ok() {
        return new Response<>(SUCCESS_CODE, "操作成功", null);
    }

    public static <T> Response<T> error(T data) {
        return new Response<>(400, "", data);
    }

    public static <T> Response<T> error(Integer code, String msg, T data) {
        return new Response<>(code, msg, data);
    }

    public static <T> Response<T> error(Integer code, String msg) {
        return new Response<>(code, msg, null);
    }
}
