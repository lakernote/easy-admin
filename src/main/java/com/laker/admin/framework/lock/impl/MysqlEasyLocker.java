package com.laker.admin.framework.lock.impl;

import com.laker.admin.framework.lock.base.AbstractSimpleIEasyLocker;
import com.laker.admin.framework.lock.base.EasyLocker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

/**
 * @author laker
 */
@Slf4j
public class MysqlEasyLocker extends AbstractSimpleIEasyLocker {

    /**
     * 原始sql 需要配合DuplicateKeyException使用，不优雅：INSERT INTO distribute_lock (lock_key, token, expire, thread_id) VALUES (?, ?, ?, ?);
     */
    public static final String ACQUIRE_FORMATTED_QUERY = "INSERT ignore INTO distribute_lock (lock_key, token, expire, thread_id) VALUES (?, ?, ?, ?);";
    public static final String RELEASE_FORMATTED_QUERY = "DELETE FROM distribute_lock WHERE lock_key = ? AND token = ?;";
    public static final String DELETE_EXPIRED_FORMATTED_QUERY = "DELETE FROM distribute_lock WHERE expire < ?;";
    public static final String REFRESH_FORMATTED_QUERY = "UPDATE distribute_lock SET expire = ? WHERE lock_key = ? AND token = ?;";
    private final JdbcTemplate jdbcTemplate;

    public MysqlEasyLocker(JdbcTemplate jdbcTemplate, TaskScheduler taskScheduler) {
        super(taskScheduler);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean acquire(final String key, final String token, final Duration expiration) {
        final long now = System.currentTimeMillis();
        // 这里是为了删除由于一些异常导致的锁,因为db 没有ttl
        final int expired = jdbcTemplate.update(DELETE_EXPIRED_FORMATTED_QUERY, now);
        if (expired > 0) {
            log.warn("Deleted {} expired locks", expired);
        }
        final long expireAt = expiration.toMillis() + System.currentTimeMillis();
        final int created = jdbcTemplate.update(ACQUIRE_FORMATTED_QUERY, key, token, expireAt, Thread.currentThread().getName());
        return created == 1;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean release0(EasyLocker lock) {
        String key = lock.getKey();
        String token = lock.getToken();
        final int deleted = jdbcTemplate.update(RELEASE_FORMATTED_QUERY, key, token);

        final boolean released = deleted == 1;
        if (released) {
            log.info("Release query successfully affected 1 record for key {} with token {}", key, token);
        } else if (deleted > 0) {
            log.error("Unexpected result from release for key {} with token {}, released {}", key, token, deleted);
        } else {
            log.error("Release query did not affect any records for key {} with token {}", key, token);
        }

        return released;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean refresh(final String key, final String token, final Duration expiration) {
        // 计算新的过期时间
        final long now = System.currentTimeMillis();
        final long expireAt = expiration.toMillis() + now;
        // 更新锁的过期时间为新的过期时间
        final int updated = jdbcTemplate.update(REFRESH_FORMATTED_QUERY, expireAt, key, token);
        // 判断更新是否成功
        final boolean refreshed = updated == 1;
        // 如果更新了 1 条记录，说明锁的过期时间成功刷新，记录日志并返回 true
        if (updated == 1) {
            log.info("Refresh query successfully affected 1 record for key {} with token {}", key, token);
            // 如果更新了多条记录
        } else if (updated > 1) {
            log.warn("Unexpected result from refresh for key {} with token {}, refreshed {}", key, token, updated);
        } else {
            // 如果没有更新任何记录，说明锁已经过期
            log.warn("Refresh query did not affect any records for key {} with token {}", key, token);
        }
        return refreshed;
    }
}