package com.laker.admin.framework.ext.thread;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * 自定义扩展拒绝策略
 * 
 * @author laker
 *
 *
 */
public class EasyAdminRejectPolicy extends AbortPolicy {
    private static final Log LOG = LogFactory.get();

    /** {@inheritDoc} */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        LOG.warn("触发线程拒绝策略：\r\n{}", "Task " + r.toString() + " rejected from "
                + e.toString());
        super.rejectedExecution(r, e);
    }

}