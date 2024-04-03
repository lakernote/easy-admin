package com.laker.admin.framework.ext.actuator.endpoint;

import lombok.Data;

@Data
public class Dependency {
    private String groupId;
    private String artifactId;
    private String version;
}