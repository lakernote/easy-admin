package com.laker.admin.service;

import cn.hutool.crypto.SecureUtil;
import com.laker.admin.utils.IP2CityUtil;

public class Test {

    public static void main(String[] args) {
        String s = SecureUtil.sha256("lakernote");
        System.out.println(s);

        String cityInfo = IP2CityUtil.getCityInfo("203.119.241.114");
        System.out.println(cityInfo);
    }
}
