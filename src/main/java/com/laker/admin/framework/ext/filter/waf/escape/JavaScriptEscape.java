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
                    escaped.append("&lt;");
                    break;
                case '>':
                    escaped.append("&gt;");
                    break;
                case '&':
                    escaped.append("&amp;");
                    break;
                // 日志伪造防范,
                // 处理回车、换行符
                case '\n':
                case '\r':
                case '\t':
                case '\\':
                case '/':
                case '`':
                case ';':
                    break;
                default:
                    escaped.append(c);
            }
        }

        return escaped.toString();
    }
}
