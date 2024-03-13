package com.laker.admin.module.sys.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyMenuVo {

    private String path;

    private String component;

    private Meta meta;

    private String name;

    private String redirect;

    private List<MyMenuVo> children = new ArrayList<>();

    public MyMenuVo addChild(MyMenuVo child) {
        children.add(child);
        return this;
    }

    @Data
    @Builder
    public static class Meta {
        private String title;
        private String icon;
        private boolean alwaysShow;
        private boolean hidden;
        private boolean canTo;
        private boolean noTagsView;
        private boolean noCache;
        private boolean showMainRoute;
        private boolean affix;
        private String activeMenu;
        private List<String> permission;
    }
}