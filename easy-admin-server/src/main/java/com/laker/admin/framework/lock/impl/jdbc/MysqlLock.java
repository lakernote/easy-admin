package com.laker.admin.framework.lock.impl.jdbc;

import com.laker.admin.framework.lock.api.LLock;
import com.laker.admin.framework.lock.core.AbstractSimpleLock;
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
public class MysqlLock extends AbstractSimpleLock {

    /**
     * 原始sql 需要配合DuplicateKeyException使用，不优雅：INSERT INTO distribute_lock (lock_key, token, expire, thread_id) VALUES (?, ?, ?, ?);
     */
    public static final String ACQUIRE_FORMATTED_QUERY = "INSERT ignore INTO distribute_lock (lock_key, token, expire, thread_id) VALUES (?, ?, ?, ?);";
    public static final String RELEASE_FORMATTED_QUERY = "DELETE FROM distribute_lock WHERE lock_key = ? AND token = ?;";
    public static final String DELETE_EXPIRED_FORMATTED_QUERY = "DELETE FROM distribute_lock WHERE expire < ?;";
    public static final String REFRESH_FORMATTED_QUERY = "UPDATE distribute_lock SET expire = ? WHERE lock_key = ? AND token = ?;";
    private JdbcTemplate jdbcTemplate;

    public MysqlLock(JdbcTemplate jdbcTemplate, TaskScheduler taskScheduler) {
        super(taskScheduler);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    protected String acquire(final String key, final String token, final Duration expiration) {
        final long now = System.currentTimeMillis();
        // 这里是为了删除由于一些异常导致的锁,因为db 没有ttl
        final int expired = jdbcTemplate.update(DELETE_EXPIRED_FORMATTED_QUERY, now);
        log.info("Expired {} locks", expired);
        final long expireAt = expiration.toMillis() + System.currentTimeMillis();
        final int created = jdbcTemplate.update(ACQUIRE_FORMATTED_QUERY, key, token, expireAt, Thread.currentThread().getName());
        return created == 1 ? token : null;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    protected boolean release0(LLock lock) {
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
    protected boolean refresh(final String key, final String token, final Duration expiration) {
        final long now = System.currentTimeMillis();
        final long expireAt = expiration.toMillis() + now;

        final int updated = jdbcTemplate.update(REFRESH_FORMATTED_QUERY, expireAt, key, token);
        final boolean refreshed = updated == 1;
        if (refreshed) {
            log.info("Refresh query successfully affected 1 record for key {} with token {}", key, token);
        } else if (updated > 0) {
            log.error("Unexpected result from refresh for key {} with token {}, refreshed {}", key, token, updated);
        } else {
            log.error("Refresh query did not affect any records for key {} with token {}", key, token);
        }
        return refreshed;
    }
}