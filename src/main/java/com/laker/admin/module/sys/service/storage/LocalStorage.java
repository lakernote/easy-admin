package com.laker.admin.module.sys.service.storage;


import cn.hutool.core.io.FileUtil;
import com.laker.admin.config.EasyAdminConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
@ConditionalOnProperty(name = "laker.storage.local.enable", havingValue = "true", matchIfMissing = true)
public class LocalStorage implements Storage {

    private final String storagePath;
    private final String address;

    public LocalStorage(EasyAdminConfig easyConfig) {
        EasyAdminConfig.Local local = easyConfig.getStorage().getLocal();
        storagePath = local.getStoragePath();
        address = local.getAddress();
    }

    @Override
    public String store(InputStream inputStream, long contentLength, String contentType, String filename) {
        FileUtil.writeFromStream(inputStream, new File(storagePath, filename));
        return storagePath + "/" + filename;
    }


    @Override
    public void delete(String filePath) {
        FileUtil.del(new File(filePath));
    }

    @Override
    public String getUrl(String filePath) {
        return address + "/" + filePath;
    }
}
