package com.laker.admin.framework.ext.filter.waf.escape;

public class SqlEscape {

    public static String escape(String value) {

        //剥离SQL注入部分代码
        return value.replaceAll("('.+--)|(--)|(\\|)|(%7C)", "");
    }
}
