package com.laker.admin.module.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.laker.admin.module.enums.DataFilterTypeEnum;
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
 * @since 2021-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * Key值
     */
    private String roleCode;

    /**
     * 描述
     */
    private String details;

    /**
     * 是否可用
     */
    private Boolean enable;

    private LocalDateTime createTime;

    private Integer roleType;

    private DataFilterTypeEnum dataType;

    private String dataSql;

    /**
     * 提供前端 显示
     */
    @TableField(exist = false)
    private boolean checked = false;


}
