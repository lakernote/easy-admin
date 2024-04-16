SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for ext_log
-- ----------------------------
DROP TABLE IF EXISTS `ext_log`;
CREATE TABLE `ext_log`
(
    `log_id`      bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     bigint(20)                                              NULL DEFAULT NULL COMMENT '用户id',
    `ip`          varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT 'ip地址',
    `city`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求城市',
    `client`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器或者app信息',
    `uri`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求uri',
    `method`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
    `request`     varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求',
    `response`    varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '响应',
    `status`      tinyint(1)                                              NULL DEFAULT NULL COMMENT '状态',
    `cost`        int(10)                                                 NULL DEFAULT NULL COMMENT '耗时ms',
    `create_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9227
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '日志'
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `dept_id`   bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT,
    `dept_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `address`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `pid`       bigint(11)                                              NOT NULL DEFAULT 0,
    `status`    tinyint(1)                                              NULL     DEFAULT NULL,
    `sort`      int(255)                                                NULL     DEFAULT NULL,
    PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept`
VALUES (14, '业务部', '乱吹的业务部', 0, 1, 2);
INSERT INTO `sys_dept`
VALUES (15, '研发部', '闷头猛干', 0, 1, 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `dict_id`     bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `dict_code`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典编码',
    `dict_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典名称',
    `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典描述',
    `dict_data`   varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典数据',
    `enable`      tinyint(1)                                              NULL DEFAULT NULL COMMENT '字典状态',
    `create_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '系统字典表'
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`
(
    `file_id`     bigint(11)                                              NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(11)                                              NULL DEFAULT NULL,
    `nick_name`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `file_path`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
    `file_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
    `create_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 23
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '文件表'
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file`
VALUES (22, 16, '李哥', 'http://localhost:8080/oss-file/偶松.jpg', '偶松.jpg', '2022-04-06 22:34:05');

-- ----------------------------
-- Table structure for sys_power
-- ----------------------------
DROP TABLE IF EXISTS `sys_power`;
CREATE TABLE `sys_power`
(
    `menu_id`          bigint(11) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid`              bigint(11) UNSIGNED                                     NOT NULL DEFAULT 0 COMMENT '父ID',
    `title`            varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '名称',
    `icon`             varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单图标',
    `href`             varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '链接',
    `open_type`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '链接打开方式',
    `sort`             int(11)                                                 NULL     DEFAULT 0 COMMENT '菜单排序',
    `enable`           tinyint(1) UNSIGNED                                     NOT NULL DEFAULT 1 COMMENT '状态(0:禁用,1:启用)',
    `remark`           varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '备注信息',
    `create_time`      datetime(0)                                             NULL     DEFAULT NULL COMMENT '创建时间',
    `type`             int(11)                                                 NULL     DEFAULT NULL COMMENT '权限类型1目录2菜单3接口4数据',
    `power_code`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '权限标识，数据权限例ExtLeaveMapper.selectPage',
    `data_filter_type` int(1)                                                  NULL     DEFAULT NULL COMMENT '数据权限过滤类型，ALL,DEPT,SELF',
    PRIMARY KEY (`menu_id`) USING BTREE,
    INDEX `title` (`title`) USING BTREE,
    INDEX `href` (`href`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 302
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '系统菜单权限资源表'
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_power
-- ----------------------------
INSERT INTO `sys_power`
VALUES (1, 0, '工作空间', 'layui-icon layui-icon-cellphone', '', '', 0, 1, NULL, NULL, 0, '', 1);
INSERT INTO `sys_power`
VALUES (251, 0, '系统管理', 'layui-icon layui-icon-set-fill', '', '', 0, 1, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_power`
VALUES (252, 251, '权限管理', 'layui-icon layui-icon layui-icon layui-icon-face-cry', 'view/system/power.html',
        '_iframe', 2, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power`
VALUES (258, 251, '用户管理', 'layui-icon layui-icon-user', 'view/system/user.html', '_iframe', 1, 1, NULL, NULL, 1,
        'user', 1);
INSERT INTO `sys_power`
VALUES (266, 251, '应用监控',
        'layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon-auz',
        'http://localhost:8080/monitoring', '_iframe', 10, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power`
VALUES (268, 251, '接口文档',
        'layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon ', '/doc.html',
        '_iframe', 5, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power`
VALUES (280, 251, '角色管理', 'layui-icon layui-icon ', 'view/system/role.html', '_iframe', 3, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power`
VALUES (281, 251, '部门管理', 'layui-icon layui-icon ', 'view/system/deptment.html', '_iframe', 4, 1, NULL, NULL, 1, '',
        1);
INSERT INTO `sys_power`
VALUES (282, 1, '配套专栏', 'layui-icon layui-icon layui-icon layui-icon ',
        'https://blog.csdn.net/abu935009066/category_10817814.html', '_iframe', 3, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power`
VALUES (283, 251, 'WebLog', 'layui-icon ', 'view/system/weblog.html', '_iframe', 12, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power`
VALUES (284, 251, '字典管理', 'layui-icon layui-icon ', 'view/system/dict.html', '_iframe', 13, 1, NULL, NULL, 1, '',
        1);
INSERT INTO `sys_power`
VALUES (285, 1, '日志查看', 'layui-icon layui-icon layui-icon ', 'view/ext/log.html', '_iframe', 6, 1, NULL, NULL, 1,
        '', 1);
INSERT INTO `sys_power`
VALUES (286, 258, '查询', 'layui-icon ', '', '', 1, 0, NULL, NULL, 2, 'user-seach', 1);
INSERT INTO `sys_power`
VALUES (287, 1, '定时任务', 'layui-icon ', 'view/system/task.html', '_iframe', 7, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power`
VALUES (288, 285, '日志查看', 'layui-icon ', '', '', 1, 1, NULL, NULL, 2, 'log.list', 1);
INSERT INTO `sys_power`
VALUES (289, 0, '源码下载', 'layui-icon layui-icon-praise', '', '', 1, 1, NULL, NULL, 0, '', 1);
INSERT INTO `sys_power`
VALUES (290, 289, '码云地址', 'layui-icon layui-icon ', 'https://gitee.com/lakernote/easy-admin', '_blank', 1, 1, NULL,
        NULL, 1, '', 1);
INSERT INTO `sys_power`
VALUES (291, 1, '在线用户', 'layui-icon ', 'view/system/onlineUser.html', '_iframe', 7, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power`
VALUES (293, 283, '查看', 'layui-icon ', '', '', 1, 1, NULL, NULL, 2, 'weblog.list', 1);
INSERT INTO `sys_power`
VALUES (294, 270, '列表个人', 'layui-icon ', '', '', 1, 1, NULL, NULL, 3, 'ExtLeaveMapper.selectPage', 3);
INSERT INTO `sys_power`
VALUES (295, 270, '列表全部', 'layui-icon ', '', '', 2, 1, NULL, NULL, 3, 'ExtLeaveMapper.selectPage', 1);
INSERT INTO `sys_power`
VALUES (296, 270, '列表部门', 'layui-icon ', '', '', 2, 1, NULL, NULL, 3, 'ExtLeaveMapper.selectPage', 2);
INSERT INTO `sys_power`
VALUES (298, 269, '修改密码', 'layui-icon layui-icon ', '', '', 2, 1, NULL, NULL, 2, 'user.update.pwd', 1);
INSERT INTO `sys_power`
VALUES (301, 1, '文件管理', 'layui-icon ', 'view/sys/file.html', '_iframe', 15, 1, NULL, NULL, 1, '', 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`     bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT,
    `role_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
    `role_code`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Key值',
    `details`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
    `enable`      tinyint(1)                                              NULL DEFAULT NULL COMMENT '是否可用',
    `create_time` datetime(0)                                             NULL DEFAULT NULL,
    `role_type`   int(1)                                                  NULL DEFAULT NULL COMMENT '角色类型，1：菜单权限角色 ，2：数据权限角色',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (5, '管理员', 'admin', '', 1, NULL, 1);
INSERT INTO `sys_role`
VALUES (6, '领导', 'leader', '', 1, NULL, 1);
INSERT INTO `sys_role`
VALUES (10, '数据角色领导', '', '', 1, NULL, 2);
INSERT INTO `sys_role`
VALUES (11, '数据角色员工', '', '', 1, NULL, 2);

-- ----------------------------
-- Table structure for sys_role_power
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_power`;
CREATE TABLE `sys_role_power`
(
    `id`       bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `role_id`  bigint(20)          NULL DEFAULT NULL,
    `power_id` bigint(20)          NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 419
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_power
-- ----------------------------
INSERT INTO `sys_role_power`
VALUES (217, 6, 1);
INSERT INTO `sys_role_power`
VALUES (218, 6, 269);
INSERT INTO `sys_role_power`
VALUES (219, 6, 282);
INSERT INTO `sys_role_power`
VALUES (220, 6, 289);
INSERT INTO `sys_role_power`
VALUES (221, 6, 290);
INSERT INTO `sys_role_power`
VALUES (249, 9, 294);
INSERT INTO `sys_role_power`
VALUES (250, 11, 294);
INSERT INTO `sys_role_power`
VALUES (251, 10, 296);
INSERT INTO `sys_role_power`
VALUES (391, 5, 1);
INSERT INTO `sys_role_power`
VALUES (392, 5, 269);
INSERT INTO `sys_role_power`
VALUES (393, 5, 298);
INSERT INTO `sys_role_power`
VALUES (394, 5, 270);
INSERT INTO `sys_role_power`
VALUES (395, 5, 282);
INSERT INTO `sys_role_power`
VALUES (396, 5, 285);
INSERT INTO `sys_role_power`
VALUES (397, 5, 288);
INSERT INTO `sys_role_power`
VALUES (398, 5, 287);
INSERT INTO `sys_role_power`
VALUES (399, 5, 291);
INSERT INTO `sys_role_power`
VALUES (400, 5, 292);
INSERT INTO `sys_role_power`
VALUES (401, 5, 300);
INSERT INTO `sys_role_power`
VALUES (402, 5, 301);
INSERT INTO `sys_role_power`
VALUES (403, 5, 251);
INSERT INTO `sys_role_power`
VALUES (404, 5, 252);
INSERT INTO `sys_role_power`
VALUES (405, 5, 258);
INSERT INTO `sys_role_power`
VALUES (406, 5, 286);
INSERT INTO `sys_role_power`
VALUES (407, 5, 264);
INSERT INTO `sys_role_power`
VALUES (408, 5, 265);
INSERT INTO `sys_role_power`
VALUES (409, 5, 266);
INSERT INTO `sys_role_power`
VALUES (410, 5, 268);
INSERT INTO `sys_role_power`
VALUES (411, 5, 280);
INSERT INTO `sys_role_power`
VALUES (412, 5, 281);
INSERT INTO `sys_role_power`
VALUES (413, 5, 283);
INSERT INTO `sys_role_power`
VALUES (414, 5, 293);
INSERT INTO `sys_role_power`
VALUES (415, 5, 284);
INSERT INTO `sys_role_power`
VALUES (416, 5, 299);
INSERT INTO `sys_role_power`
VALUES (417, 5, 289);
INSERT INTO `sys_role_power`
VALUES (418, 5, 290);

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task`
(
    `task_id`         bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `task_code`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务的编码 必须全局唯一',
    `task_name`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务的名称',
    `task_class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务的类名称',
    `task_cron`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务的cron表达式',
    `create_time`     datetime(0)                                             NULL DEFAULT NULL COMMENT '任务创建时间',
    `enable`          tinyint(1)                                              NULL DEFAULT NULL COMMENT '是否启用',
    `task_state`      int(1)                                                  NULL DEFAULT NULL COMMENT '任务状态',
    PRIMARY KEY (`task_id`) USING BTREE,
    UNIQUE INDEX `task_code` (`task_code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_task
-- ----------------------------
INSERT INTO `sys_task`
VALUES (10, 'job2', '第二个任务', 'com.laker.admin.module.task.core.impl.Test2Job', '1/3 * * * * ? ', NULL, 1, 0);
INSERT INTO `sys_task`
VALUES (11, 'job1', '第一个任务', 'com.laker.admin.module.task.core.impl.TestJob', '1/3 * * * * ?', NULL, 1, 0);

-- ----------------------------
-- Table structure for sys_tasklog
-- ----------------------------
DROP TABLE IF EXISTS `sys_tasklog`;
CREATE TABLE `sys_tasklog`
(
    `tasklog_id`  bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT,
    `task_code`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务编码',
    `start_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '任务开始时间',
    `end_time`    datetime(0)                                             NULL DEFAULT NULL COMMENT '任务结束时间',
    `status`      int(255)                                                NULL DEFAULT NULL COMMENT '状态正常，异常',
    `cost`        int(10)                                                 NULL DEFAULT NULL COMMENT '耗时 ms',
    `thread_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '线程名称',
    PRIMARY KEY (`tasklog_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_tasklog
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`     bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT,
    `user_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `password`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `nick_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `dept_id`     bigint(20)                                              NULL DEFAULT NULL,
    `sex`         int(2)                                                  NULL DEFAULT NULL,
    `phone`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `enable`      int(2)                                                  NULL DEFAULT NULL,
    `email`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `avatar`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0)                                             NULL DEFAULT NULL,
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE INDEX `user_name` (`user_name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 25
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 'admin', '30a9c7081b257f80a3f672452decc16dafe717cc54ac510afb77257fb6ee3702', '超级管理员', 14, 1,
        '11111111111', 1, '935009@98.com', '', '2021-08-15 11:02:15');
INSERT INTO `sys_user`
VALUES (16, 'laker', '4b15d2b3b671209e01202331881af5a6044d342dc624d29a53ed6b4402af6d61', '李哥', 14, 1, '1', 1, '', '',
        '2021-08-09 18:25:32');
INSERT INTO `sys_user`
VALUES (17, 'yang', '4b15d2b3b671209e01202331881af5a6044d342dc624d29a53ed6b4402af6d61', '陈总', 14, 1, '', 1, '', '',
        '2021-08-10 10:24:23');
INSERT INTO `sys_user`
VALUES (18, 'zhang', '4b15d2b3b671209e01202331881af5a6044d342dc624d29a53ed6b4402af6d61', '张总', 15, 2, '', 1, '', '',
        '2021-08-10 10:24:38');
INSERT INTO `sys_user`
VALUES (24, 'test', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '测试用户', 15, 1,
        '18256079743', 1, '98@qq.com', '', '2021-10-21 10:07:07');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20)          NULL DEFAULT NULL,
    `role_id` bigint(20)          NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 81
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`
VALUES (7, NULL, 2);
INSERT INTO `sys_user_role`
VALUES (16, 19, 3);
INSERT INTO `sys_user_role`
VALUES (17, NULL, 2);
INSERT INTO `sys_user_role`
VALUES (19, NULL, 2);
INSERT INTO `sys_user_role`
VALUES (24, 22, 2);
INSERT INTO `sys_user_role`
VALUES (25, 22, 3);
INSERT INTO `sys_user_role`
VALUES (28, 23, 2);
INSERT INTO `sys_user_role`
VALUES (58, 1, 5);
INSERT INTO `sys_user_role`
VALUES (61, 18, 6);
INSERT INTO `sys_user_role`
VALUES (62, 18, 7);
INSERT INTO `sys_user_role`
VALUES (71, 17, 6);
INSERT INTO `sys_user_role`
VALUES (72, 17, 10);
INSERT INTO `sys_user_role`
VALUES (77, 16, 5);
INSERT INTO `sys_user_role`
VALUES (78, 16, 10);
INSERT INTO `sys_user_role`
VALUES (80, 24, 6);

SET FOREIGN_KEY_CHECKS = 1;
