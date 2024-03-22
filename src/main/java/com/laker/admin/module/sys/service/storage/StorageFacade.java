package com.laker.admin.module.sys.service.storage;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.ext.mybatis.UserInfoAndPowers;
import com.laker.admin.framework.utils.EasyAdminSecurityUtils;
import com.laker.admin.module.sys.entity.SysFile;
import com.laker.admin.module.sys.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StorageFacade {

    @Autowired
    ISysFileService sysFileService;
    @Autowired
    Storage storage;

    public SysFile store(InputStream inputStream, long contentLength, String contentType, String originalFilename) {
        // png/jpg
        String suffix = FileUtil.getSuffix(originalFilename);
        String fileName = IdUtil.fastSimpleUUID() + "." + suffix;
        String filePath = storage.store(inputStream, contentLength, contentType, fileName);
        SysFile sysFile = new SysFile();
        UserInfoAndPowers currentUserInfo = EasyAdminSecurityUtils.getCurrentUserInfo();
        sysFile.setUserId(currentUserInfo.getUserId());
        sysFile.setNickName(currentUserInfo.getNickName());
        sysFile.setFilePath(filePath);
        sysFile.setFileName(originalFilename);
        sysFile.setCreateTime(LocalDateTime.now());
        sysFileService.save(sysFile);
        return sysFile;
    }

    public Page page(Page page, String keyWord) {
        LambdaQueryWrapper<SysFile> queryWrapper = new QueryWrapper().lambda();
        if (StrUtil.isNotBlank(keyWord)) {
            queryWrapper.like(SysFile::getFileName, keyWord);
        }
        Page pageList = sysFileService.page(page, queryWrapper);
        List<SysFile> records = page.getRecords();
        records.forEach(sysFile -> {
            sysFile.setFilePath(storage.getUrl(sysFile.getFilePath()));
        });
        return pageList;
    }

    @Transactional
    public void delete(Long id) {
        SysFile sysFile = sysFileService.getById(id);
        sysFileService.removeById(id);
        storage.delete(sysFile.getFilePath());
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
            delete(id);
        }
    }
}
