package com.laker.admin.framework.localmessage;

import java.util.Map;

public interface ILocalMessageOperation {
    boolean localOperation(Map<String, Object> params);

    boolean remoteOperation(Map<String, Object> params);
}
