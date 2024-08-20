package com.laker.admin.module.ext.service;

import com.laker.admin.module.ext.mapper.ExtLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoService {

    @Autowired
    private ExtLogMapper extLogMapper;

    public void test() {
        final int distinctIp = extLogMapper.selectDistinctIp();
        log.info("distinctIp: {}", distinctIp);
    }
}
