package com.laker.admin.module.sys.service.storage;

import java.io.InputStream;

public interface Storage {

    String store(InputStream inputStream, long contentLength, String contentType, String fileName);

    void delete(String filePath);

    String getUrl(String filePath);
}
