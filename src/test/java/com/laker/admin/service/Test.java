package com.laker.admin.service;

import cn.hutool.crypto.SecureUtil;

public class Test {

    public static void main(String[] args) {
        String s = SecureUtil.sha256("lakernote");
        System.out.println(s);
    }
}
