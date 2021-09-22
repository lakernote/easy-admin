package com.laker.admin.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.laker.admin.utils.IP2CityUtil;

public class Test {

    public static void main(String[] args) {
        String s = SecureUtil.sha256("lakernote");
        System.out.println(s);
        String pw_hash = BCrypt.hashpw("lakernote", BCrypt.gensalt());
        System.out.println("密文：" + pw_hash);
        boolean checkpw = BCrypt.checkpw("lakernote", pw_hash);
        System.out.println("校验结果：" + checkpw);
        String cityInfo = IP2CityUtil.getCityInfo("120.36.3.41");
        System.out.println(cityInfo);
        String[] split = cityInfo.split("\\|");
        System.out.println(StrUtil.format("{}.{}.{}.{}", split[0], split[2], split[3], split[4]));
    }
}
