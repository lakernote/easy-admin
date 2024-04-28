package com.laker.admin.module.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.laker.admin.framework.ext.desensitization.Desensitization;
import com.laker.admin.framework.ext.desensitization.DesensitizationTypeEnum;
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
 * @since 2021-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    private String userName;

    @Desensitization(type = DesensitizationTypeEnum.PASSWORD)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String nickName;

    private Long deptId;
    @TableField(exist = false)
    private SysDept dept;
    @TableField(exist = false)
    private String deptName;
    private Integer sex;

    @Desensitization(type = DesensitizationTypeEnum.MOBILE_PHONE)
    private String phone;

    private Integer enable;
    @Desensitization(type = DesensitizationTypeEnum.EMAIL)
    private String email;

    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;


    @TableField(exist = false)
    private String roleIds;

    @TableField(exist = false)
    private String dataRoleId;

}
