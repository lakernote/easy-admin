package com.laker.admin.framework.ext.filter.waf.escape;


public class JavaScriptEscape {

    public static String escape(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder escaped = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case '<':
                case '>':
                    break;
                default:
                    escaped.append(c);
            }
        }

        return escaped.toString();
    }
}
