DROP TABLE IF EXISTS `wx_user`;
-- 用户表
CREATE TABLE `wx_user`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `open_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL,
    `union_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL,
    `session_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `role_type`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `nick_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `phone`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `avatar`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `create_time` datetime                                                DEFAULT NULL,
    `update_time` datetime                                                DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `open_id` (`open_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
