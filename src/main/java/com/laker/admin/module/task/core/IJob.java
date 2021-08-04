package com.laker.admin.module.task.core;

import java.util.Map;

public interface IJob {

    void execute(Map map) throws Exception;
}
