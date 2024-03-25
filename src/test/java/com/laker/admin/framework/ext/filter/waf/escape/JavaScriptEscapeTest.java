package com.laker.admin.framework.ext.filter.waf.escape;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaScriptEscapeTest {

    @Test
    void escape() {
        // 假设这是用户输入的数据
        String userInput = "<script>alert('XSS Attack!');</script>|\"json\":{}";

        // 转义用户输入，确保安全
        String escapedInput = JavaScriptEscape.escape(userInput);

        Assertions.assertEquals(escapedInput, "scriptalert('XSS Attack!');/script|\"json\":{}");
    }
}