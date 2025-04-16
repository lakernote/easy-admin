package com.laker.admin.framework.aop.idempotent;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.time.Instant;

public class MysqlIdempotentHandler implements IdempotentHandler {

    // "INSERT INTO idempotent_record (`key`, expireTime) VALUES (?, ?) " +
    //                "ON DUPLICATE KEY UPDATE `key` = `key`";

    private JdbcTemplate jdbcTemplate;

    public MysqlIdempotentHandler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean checkAndSet(String key, long expireTime) {
        // 数据库表中已经为 `key` 字段添加了唯一约束
        String sql = "SELECT COUNT(*) FROM idempotent_record WHERE `key` = ? AND expireTime > ?";
        Timestamp now = Timestamp.from(Instant.now());
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, key, now);
        if (count != null && count > 0) {
            return false;
        }
        Timestamp expire = Timestamp.from(Instant.now().plusSeconds(expireTime));
        sql = "INSERT INTO idempotent_record (`key`, expireTime) VALUES (?, ?) ";
        int rowsAffected = jdbcTemplate.update(sql, key, expire);
        return rowsAffected == 1;
    }

    @Override
    public void remove(String key) {
        String sql = "DELETE FROM idempotent_record WHERE `key` = ?";
        jdbcTemplate.update(sql, key);
    }

    /**
     * 清理过期的记录 TODO
     */
    public void cleanExpiredRecords() {
        String sql = "DELETE FROM idempotent_record WHERE expireTime < ?";
        Timestamp now = Timestamp.from(Instant.now());
        jdbcTemplate.update(sql, now);
    }
}    