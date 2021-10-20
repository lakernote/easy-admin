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
    /**
     * 菜单权限
     */
    public static final Integer ROLE_TYPE_MENU = 1;
    /**
     * 数据权限
     */
    public static final Integer ROLE_TYPE_DATA = 2;
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

    /**
     * 1是菜单接口角色，2为数据角色
     */
    private Integer roleType;

    /**
     * 提供前端 显示
     */
    @TableField(exist = false)
    private boolean checked = false;


}
