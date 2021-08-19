package com.laker.admin.module.sys.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuVo {
    private Long id;

    private Long pid;

    private String title;

    private String icon;

    private String href;

    private String openType;

    private Integer type;

    private String powerCode;

    private List<MenuVo> children;
}