package com.laker.admin.framework.ext.mvc;

import lombok.Builder;
import lombok.ToString;

/**
 * @author: laker
 * @date: 2022/11/25
 **/
@Builder
@ToString
public class CurrentUser {
    private String id;
    private String username;
    private String displayName;
}
