package com.laker.admin.module.ext.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.laker.admin.module.sys.entity.SysDept;
import com.laker.admin.module.sys.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志
 * </p>
 *
 * @author laker
 * @since 2021-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExtLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    /**
     * 用户id
     */
    private Long userId;

    @TableField(exist = false)
    private SysUser user;

    @TableField(exist = false)
    private SysDept dept;

    /**
     * ip地址
     */
    private String ip;

    private String city;

    /**
     * 浏览器或者app信息
     */
    private String client;
    private String uri;
    private String method;

    /**
     * 请求
     */
    private String request;

    /**
     * 响应
     */
    private String response;

    private Boolean status;

    /**
     * 耗时ms
     */
    private Integer cost;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;


}
