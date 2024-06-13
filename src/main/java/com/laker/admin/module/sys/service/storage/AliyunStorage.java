package com.laker.admin.module.sys.service.storage;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.laker.admin.config.EasyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
@ConditionalOnProperty(name = "laker.storage.aliyun.enable", havingValue = "true")
public class AliyunStorage implements Storage {

    private final String endpoint;
    private final String accessKeyId;
    private final String accessKeySecret;
    private final String bucketName;

    public AliyunStorage(EasyConfig easyConfig) {
        EasyConfig.Aliyun aliyun = easyConfig.getStorage().getAliyun();
        endpoint = aliyun.getEndpoint();
        accessKeyId = aliyun.getAccessKeyId();
        accessKeySecret = aliyun.getAccessKeySecret();
        bucketName = aliyun.getBucketName();
    }

    private OSSClient getOSSClient() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }


    /**
     * 阿里云OSS对象存储简单上传实现
     */
    @Override
    public String store(InputStream inputStream, long contentLength, String contentType, String fileName) {
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentLength);
        objectMetadata.setContentType(contentType);
        // 对象键（Key）是对象在存储桶中的唯一标识。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);
        PutObjectResult putObjectResult = getOSSClient().putObject(putObjectRequest);
        return fileName;
    }

    @Override
    public void delete(String filePath) {
        try {
            getOSSClient().deleteObject(bucketName, filePath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    @Override
    public String getUrl(String filePath) {
        return "https://" + bucketName + "." + endpoint + "/" + filePath;
    }
}
