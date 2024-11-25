package com.laker.admin.framework.model;

import cn.hutool.core.text.CharSequenceUtil;
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
@Schema(name = "响应", description = "响应")
public class Response<T> {
    @Schema(description = "响应码，非0 即为异常", example = "0")
    private final String code;
    @Schema(description = "响应消息", example = "提交成功")
    private final String msg;
    @Schema(description = "响应数据")
    private final T data;
    @Schema(description = "traceId")
    private final String traceId;
    @Schema(description = "请求id")
    private final Boolean success;

    protected Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = CharSequenceUtil.equals("0", code);
        this.traceId = MDC.get(EasyAdminConstants.TRACE_ID);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>("0", "操作成功", data);
    }

    public static Response<Void> ok() {
        return new Response<>("0", "操作成功", null);
    }

    public static <T> Response<T> error(String code, String msg, T data) {
        return new Response<>(code, msg, data);
    }

    public static <T> Response<T> error(String code, String msg) {
        return new Response<>(code, msg, null);
    }

    public static <T> Response<T> error400(String msg) {
        return new Response<>("400", msg, null);
    }

    public static <T> Response<T> error400(T data) {
        return new Response<>("400", "", data);
    }

    public static <T> Response<T> error401() {
        return new Response<>("401", "Not Login", null);
    }


    public static <T> Response<T> error403() {
        return new Response<>("403", "Forbidden", null);
    }

    public static <T> Response<T> error403(String msg) {
        return new Response<>("403", msg, null);
    }

    public static <T> Response<T> error404() {
        return new Response<>("404", "Not Found", null);
    }

    public static <T> Response<T> error500(String msg) {
        return new Response<>("500", msg, null);
    }
}
