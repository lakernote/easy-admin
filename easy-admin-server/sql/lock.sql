CREATE TABLE `distribute_lock` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                   `lock_key` varchar(50) NOT NULL COMMENT '锁记录key',
                                   `token` varchar(50) NOT NULL COMMENT '锁的token，防止误删其他人的锁',
                                   `thread_id` varchar(50) NOT NULL COMMENT '获取锁的线程id',
                                   `expire` bigint(20) NOT NULL COMMENT '锁的失效时间，时间戳',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `distribute_lock_UN` (`lock_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分布式锁';
