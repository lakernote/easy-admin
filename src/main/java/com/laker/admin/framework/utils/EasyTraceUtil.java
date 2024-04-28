package com.laker.admin.framework.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.EasyAdminConstants;
import org.slf4j.MDC;

/**
 * @author laker
 */
public class EasyTraceUtil {

    public static String getTraceId() {
        return MDC.get(EasyAdminConstants.TRACE_ID);
    }

    public static void getOrGenerateTraceId() {
        String traceId = getTraceId();
        if (StrUtil.isBlank(traceId)) {
            traceId = IdUtil.simpleUUID();
            setTraceId(traceId);
        }
    }

    public static void setTraceId(final String traceId) {
        MDC.put(EasyAdminConstants.TRACE_ID, traceId);
    }

    public static void setTraceIdOrGenerateNew() {
        setTraceId(StrUtil.isBlank(getTraceId()) ? IdUtil.simpleUUID() : getTraceId());
    }

    /**
     * 设置traceId，如果为空则生成一个
     */
    public static void setTraceIdOrGenerateNew(final String traceId) {
        setTraceId(StrUtil.isBlank(traceId) ? IdUtil.simpleUUID() : traceId);
    }
}
