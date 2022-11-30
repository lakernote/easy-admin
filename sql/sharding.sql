
CREATE TABLE `ext_log_0` (
                             `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
                             `ip` varchar(20) DEFAULT NULL COMMENT 'ip地址',
                             `city` varchar(255) DEFAULT NULL COMMENT '请求城市',
                             `client` varchar(500) DEFAULT NULL COMMENT '浏览器或者app信息',
                             `uri` varchar(255) DEFAULT NULL COMMENT '请求uri',
                             `method` varchar(255) DEFAULT NULL COMMENT '请求方法',
                             `request` varchar(500) DEFAULT NULL COMMENT '请求',
                             `response` varchar(500) DEFAULT NULL COMMENT '响应',
                             `status` tinyint(1) DEFAULT NULL COMMENT '状态',
                             `cost` int(10) DEFAULT NULL COMMENT '耗时ms',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='日志';

CREATE TABLE `ext_log_1` (
                             `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
                             `ip` varchar(20) DEFAULT NULL COMMENT 'ip地址',
                             `city` varchar(255) DEFAULT NULL COMMENT '请求城市',
                             `client` varchar(500) DEFAULT NULL COMMENT '浏览器或者app信息',
                             `uri` varchar(255) DEFAULT NULL COMMENT '请求uri',
                             `method` varchar(255) DEFAULT NULL COMMENT '请求方法',
                             `request` varchar(500) DEFAULT NULL COMMENT '请求',
                             `response` varchar(500) DEFAULT NULL COMMENT '响应',
                             `status` tinyint(1) DEFAULT NULL COMMENT '状态',
                             `cost` int(10) DEFAULT NULL COMMENT '耗时ms',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='日志';