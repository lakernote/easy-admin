package com.laker.admin.framework.localmessage;

import cn.hutool.json.JSONUtil;
import com.laker.admin.framework.localmessage.entity.LocalMessage;
import com.laker.admin.framework.localmessage.mapper.LocalMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.Map;

/**
 * <pre>
 *     之前代码
 *    // @Transactional
 *    public String exampleMethod(String param) {
 *        // 模拟本地数据操作
 *        // 模拟远程调用或者耗时操作
 *        return "Success";
 *    }
 *
 *    之后代码
 *    public String exampleMethod(String param) {
 *     // 模拟本地数据操作
 *     EasyLocalMessageTemplate.execute(本地操作, 远程/耗时操作);
 *     // 模拟远程调用或者耗时操作
 *     return "Success";
 *     // @Autowired
 *     private EasyLocalMessageTemplate easyLocalMessageTemplate;
 *
 *     public String exampleMethod(String param) {
 *         Consumer<String> localOperation = p -> System.out.println("Performing local operation with param: " + p);
 *         Supplier<String> remoteOperation = () -> {
 *             System.out.println("Performing remote operation");
 *             // 模拟远程调用失败
 *             if (Math.random() < 0.5) {
 *                 throw new RuntimeException("Remote call failed");
 *             }
 *             return "Remote call success";
 *         };
 *         String message = "exampleMethod - " + param;
 *         easyLocalMessageTemplate.execute(localOperation, remoteOperation, param, message);
 *         return "Success";
 *     }
 *  </pre>
 */
@Component
public class EasyLocalMessageTemplate {
    @Autowired
    private LocalMessageMapper localMessageMapper;

    /**
     * 高级抽象，对事务管理的常见操作进行了封装，提供了简洁的 API 来处理事务。开发者无需手动处理事务的开启、提交和回滚等细节，只需关注业务逻辑即可。
     * transactionTemplate.execute(status -> {
     * // 业务逻辑
     * return null;
     * });
     */
    @Autowired
    TransactionTemplate transactionTemplate;

    /**
     * 低级抽象，提供了对事务管理的底层操作，允许开发者手动控制事务的开启、提交和回滚等细节。适用于需要更细粒度控制事务的场景。
     */
    @Autowired
    PlatformTransactionManager transactionManager;

    /**
     *
     */
    public void execute(ILocalMessageOperation localMessageOperation, Map<String, Object> params) {

        LocalMessage localMessage = performTransactionalOperations(localMessageOperation, params);
        try {
            boolean success = localMessageOperation.remoteOperation(params);
            if (success) {
                updateMessageStatusSent(localMessage);
            } else {
                updateMessageStatusOnFailure(localMessage);
            }

        } catch (Exception e) {
            updateMessageStatusOnFailure(localMessage);
            throw e;
        }
    }


    private LocalMessage performTransactionalOperations(ILocalMessageOperation localMessageOperation, Map<String, Object> params) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            // 执行本地操作
            final boolean localOperation = localMessageOperation.localOperation(params);
            if (!localOperation) {
                throw new RuntimeException("Local operation failed");
            }
            LocalMessage localMessage = new LocalMessage();
            localMessage.setStatus("PENDING");
            localMessage.setCreateTime(new Date());
            localMessage.setUpdateTime(new Date());
            localMessage.setRetryCount(0);
            // 获取localMessageOperation上注解
            EasyLocalMessageOperation easyLocalMessageOperation = localMessageOperation.getClass().getAnnotation(EasyLocalMessageOperation.class);
            localMessage.setName(easyLocalMessageOperation.name());
            localMessage.setParam(JSONUtil.toJsonStr(params));
            localMessageMapper.insert(localMessage);
            transactionManager.commit(status);
            return localMessage;
        } catch (Exception e) {
            // 回滚事务
            transactionManager.rollback(status);
            throw e;
        }

    }

    private void updateMessageStatusSent(LocalMessage localMessage) {
        localMessage.setStatus("SENT");
        localMessage.setUpdateTime(new Date());
        localMessageMapper.updateById(localMessage);
    }

    private void updateMessageStatusOnFailure(LocalMessage localMessage) {
        localMessage.setStatus("FAILED");
        localMessage.setRetryCount(localMessage.getRetryCount() + 1);
        localMessage.setUpdateTime(new Date());
        localMessageMapper.updateById(localMessage);
    }
}
