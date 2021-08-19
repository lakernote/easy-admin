package com.laker.admin.utils;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Slf4j
public class IP2CityUtil {
    public static String getCityInfo(String ip) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("ip2region.db");
            InputStream inputStream = classPathResource.getInputStream();
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, IoUtil.readBytes(inputStream, true));
            DataBlock dataBlock = searcher.memorySearch(ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }
}