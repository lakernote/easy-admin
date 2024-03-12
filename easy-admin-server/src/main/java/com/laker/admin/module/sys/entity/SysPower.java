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
 * 系统权限表
 * </p>
 *
 * @author laker
 * @since 2021-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysPower implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 父ID
     */
    private Long pid;

    /**
     * 名称
     */
    private String title;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 链接
     */
    private String href;

    /**
     * 链接打开方式
     */
    private String openType;

    /**
     * 菜单排序
     */
    private Integer sort;

    /**
     * 状态(0:禁用,1:启用)
     */
    private Boolean enable;

    /**
     * 备注信息
     */
    private String remark;

    private Integer type;
    /**
     * 如果是数据权限类型，则为数据权限标识 ExtLeaveMapper.selectPage Mapper名称+方法名称
     */
    private String powerCode;

    /**
     * 权限类型
     */
    private DataFilterTypeEnum dataFilterType;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    /**
     * 计算列 提供给前端组件
     */
    @TableField(exist = false)
    private String checkArr = "0";


}
