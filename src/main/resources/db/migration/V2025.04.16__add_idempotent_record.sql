-- idempotent_record

CREATE TABLE IF NOT EXISTS `idempotent_record` (
       `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
       `key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '幂等键',
       `expireTime` timestamp NOT NULL COMMENT '过期时间',
       `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       PRIMARY KEY (`id`),
       UNIQUE KEY `unique_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='幂等记录';
