package com.laker.admin.framework.model;

import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.EasyAdminConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.context.annotation.DependsOn;

/**
 * @author longli
 */
@Data
@AllArgsConstructor
@Schema(name = "文件part对象",description = "Part信息")
@DependsOn
public class Response<T> {
    @Schema(description  = "响应码，非0 即为异常", example = "0")
    private final String code;
    @Schema(description  = "响应消息", example = "提交成功")
    private final String msg;
    @Schema(description  = "响应数据")
    private final T data;
    @Schema(description  = "traceId")
    private final String traceId;
    @Schema(description  = "请求id")
    private final Boolean success;

    protected Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = StrUtil.equals("0", code);
        this.traceId = MDC.get(EasyAdminConstants.TRACE_ID);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>("0", "操作成功", data);
    }

    public static <Void> Response<Void> ok() {
        return new Response<Void>("0", "操作成功", null);
    }

    public static <T> Response<T> error(T data) {
        return new Response<>("400", "", data);
    }

    public static <T> Response<T> error(String code, String msg, T data) {
        return new Response<>(code, msg, data);
    }

    public static <T> Response<T> error(String code, String msg) {
        return new Response<>(code, msg, null);
    }
}
