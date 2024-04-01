package com.laker.admin.framework.ext.filter.waf;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationPart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
public class MultipartRequestChecker {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    // 允许上传的文件最大大小
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    public static Response<Void> check(HttpServletRequest request) {

        try {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                ApplicationPart applicationPart = (ApplicationPart) part;
                String submittedFileName = applicationPart.getSubmittedFileName();
                // 检查文件扩展名是否在允许的列表中
                if (StrUtil.isNotBlank(submittedFileName)) {
                    // 检查文件扩展名是否在允许的列表中
                    if (!isAllowedExtension(submittedFileName)) {
                        return Response.error400("文件类型不支持");
                    }

                    // 文件名检测
                    if (FileUtil.containsInvalid(submittedFileName)) {
                        return Response.error400("文件名存在非法字符！");
                    }

                    // 文件内容检查
                    String type = FileTypeUtil.getType(applicationPart.getInputStream());
                    if (!ALLOWED_EXTENSIONS.contains(type)) {
                        return Response.error400("你在改文件名吗？哈哈哈");
                    }
                } else {
                    String partString = applicationPart.getString("utf-8");
                    log.info("partString:{}", partString);
                }

                // 检查上传文件是否超出允许的大小限制
                if (!isAllowedSize(applicationPart.getSize())) {
                    return Response.error400("上传的文件大小超过限制！");
                }

            }
        } catch (Exception e) {
            return Response.error400(e.getMessage());
        }
        return Response.ok();
    }

    private static boolean isAllowedExtension(String filename) {
        String extension = FileUtil.extName(filename).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }

    private static boolean isAllowedSize(long fileSize) {
        return fileSize <= MAX_FILE_SIZE;
    }
}
