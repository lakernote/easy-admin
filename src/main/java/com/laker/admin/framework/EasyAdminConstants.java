package com.laker.admin.framework;

public interface EasyAdminConstants {
    String APPLICATION_NAME = "easy-admin";
    String CURRENT_USER = "currentUser";
    String TRACE_ID = "traceID"; // 用于追踪请求 区分micrometer tracing的traceId
    String USER_ID = "userId";
    String TOKEN_TYPE = "tokenType";

    enum TokenType {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }
}
