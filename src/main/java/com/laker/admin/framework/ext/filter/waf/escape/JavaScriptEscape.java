package com.laker.admin.framework.ext.filter.waf.escape;


import org.apache.commons.lang3.StringEscapeUtils;

public class JavaScriptEscape {

    public static String escape(String input) {
        if (input == null) {
            return null;
        }
        return StringEscapeUtils.escapeEcmaScript(input);
    }
}
