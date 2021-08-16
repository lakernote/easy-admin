package com.laker.admin.module.ext.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    /**
     * ip地址
     */
    private String ip;

    /**
     * 浏览器或者app信息
     */
    private String client;

    /**
     * 请求
     */
    private String request;

    /**
     * 响应
     */
    private String response;

    /**
     * 耗时ms
     */
    private Integer cost;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
