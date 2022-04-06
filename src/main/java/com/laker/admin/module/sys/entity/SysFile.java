package com.laker.admin.module.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author laker
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "file_id", type = IdType.AUTO)
    private Long fileId;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String fileName;
    private Long userId;
    private String nickName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
