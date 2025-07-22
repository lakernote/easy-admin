CREATE TABLE IF NOT EXISTS `distribute_lock` (
    `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `lock_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '锁记录key',
    `token` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '锁的token，防止误删其他人的锁',
    `thread_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '获取锁的线程id',
    `expire` bigint NOT NULL COMMENT '锁的失效时间，时间戳',
    PRIMARY KEY (`id`),
    UNIQUE KEY `distribute_lock_UN` (`lock_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='分布式锁';
