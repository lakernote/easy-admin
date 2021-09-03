package com.laker.admin.framework.waf.attack;

public class SqlFilter {

    public static String strip(String value) {

        //剥离SQL注入部分代码
        return value.replaceAll("('.+--)|(--)|(\\|)|(%7C)", "");
    }
}
