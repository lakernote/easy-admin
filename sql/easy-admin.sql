/*
 Navicat Premium Data Transfer

 Source Server         : myself
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : laker

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 20/08/2021 12:17:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ext_leave
-- ----------------------------
DROP TABLE IF EXISTS `ext_leave`;
CREATE TABLE `ext_leave`  (
  `leave_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `leave_day` int(2) DEFAULT NULL COMMENT '请假天数',
  `leave_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请假原因',
  `leave_user_id` bigint(20) DEFAULT NULL COMMENT '请假人id',
  `create_by` bigint(1) DEFAULT NULL COMMENT '创建人',
  `create_dept_id` bigint(1) DEFAULT NULL COMMENT '创建人部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `order_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`leave_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ext_leave
-- ----------------------------
INSERT INTO `ext_leave` VALUES (4, 3, '找妹子', 1, 1, 4, '2021-08-19 20:20:49', 'a576a521e1354e60b5126439b8e2ed0d');
INSERT INTO `ext_leave` VALUES (6, 1, 'laoyang', 17, 17, 5, '2021-08-20 12:10:52', '6e349ecb0127445eb16b1991be86183c');

-- ----------------------------
-- Table structure for ext_log
-- ----------------------------
DROP TABLE IF EXISTS `ext_log`;
CREATE TABLE `ext_log`  (
  `log_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'ip地址',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求城市',
  `client` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '浏览器或者app信息',
  `uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求uri',
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求方法',
  `request` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求',
  `response` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '响应',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `cost` int(10) DEFAULT NULL COMMENT '耗时ms',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2666 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `pid` bigint(11) NOT NULL DEFAULT 0,
  `status` tinyint(1) DEFAULT NULL,
  `sort` int(255) DEFAULT NULL,
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (4, '研发部', '发说说', 0, 1, 1);
INSERT INTO `sys_dept` VALUES (5, '测试部', '', 0, 0, 1);
INSERT INTO `sys_dept` VALUES (6, '研发一部-1', '今天天气如何', 4, 0, 2);
INSERT INTO `sys_dept` VALUES (7, '运维部', '', 0, 1, 3);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `dict_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典编码',
  `dict_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典描述',
  `dict_data` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典数据',
  `enable` tinyint(1) DEFAULT NULL COMMENT '字典状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '大法士大夫' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'sex', '性别', '1234333', NULL, 1, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父ID',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单图标',
  `href` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '链接',
  `open_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '链接打开方式',
  `sort` int(11) DEFAULT 0 COMMENT '菜单排序',
  `enable` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态(0:禁用,1:启用)',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `power_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限标识',
  PRIMARY KEY (`menu_id`) USING BTREE,
  INDEX `title`(`title`) USING BTREE,
  INDEX `href`(`href`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 290 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '工作空间', 'layui-icon layui-icon-cellphone', '', '', 0, 1, NULL, NULL, 0, '');
INSERT INTO `sys_menu` VALUES (250, 1, '控制后台', 'layui-icon layui-icon-console', 'view/console/console1.html', '_iframe', 0, 1, NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (251, 0, '系统管理', 'layui-icon layui-icon-set-fill', '', '', 0, 1, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (252, 251, '权限管理', 'layui-icon layui-icon layui-icon layui-icon-face-cry', 'view/system/power.html', '_iframe', 2, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (258, 251, '用户管理', 'layui-icon layui-icon-user', 'view/system/user.html', '_iframe', 1, 1, NULL, NULL, 1, 'user');
INSERT INTO `sys_menu` VALUES (259, 0, '常用组件', 'layui-icon layui-icon-rate-solid', '', '', 2, 1, NULL, NULL, 0, '');
INSERT INTO `sys_menu` VALUES (260, 259, '基础组件', 'layui-icon ', '', '', 1, 1, NULL, NULL, 0, '');
INSERT INTO `sys_menu` VALUES (261, 260, '多选下拉', 'layui-icon ', 'view/document/select.html', '_iframe', 2, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (262, 259, '进阶组件', 'layui-icon ', '', '', 2, 1, NULL, NULL, 0, '');
INSERT INTO `sys_menu` VALUES (263, 262, '数据表格', 'layui-icon ', 'view/document/table.html', '_iframe', 2, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (264, 251, '流程定义', 'layui-icon layui-icon layui-icon-tree', 'view/flow/processList.html', '_iframe', 6, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (265, 251, '流程实例', 'layui-icon ', 'view/flow/orderList.html', '', 9, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (266, 251, '应用监控', 'layui-icon layui-icon layui-icon-auz', 'http://localhost:8080/monitoring', '_iframe', 10, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (268, 251, '接口文档', 'layui-icon layui-icon layui-icon layui-icon ', 'http://localhost:8080/doc.html', '_iframe', 5, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (269, 1, '任务列表', 'layui-icon ', 'view/flow/taskList.html', '_iframe', 1, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (270, 1, '请假申请', 'layui-icon ', 'view/flow/process/leave.html', '_iframe', 4, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (280, 251, '角色管理', 'layui-icon layui-icon ', 'view/system/role.html', '_iframe', 3, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (281, 251, '部门管理', 'layui-icon layui-icon ', 'view/system/deptment.html', '_iframe', 4, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (282, 1, '配套专栏', 'layui-icon layui-icon layui-icon layui-icon ', 'https://blog.csdn.net/abu935009066/category_10817814.html', '_iframe', 3, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (283, 251, 'WebLog', 'layui-icon ', 'view/system/weblog.html', '_iframe', 12, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (284, 251, '字典管理', 'layui-icon layui-icon ', 'view/system/dict.html', '_iframe', 13, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (285, 1, '日志查看', 'layui-icon layui-icon layui-icon ', 'view/ext/log.html', '_iframe', 6, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (286, 258, '查询', 'layui-icon ', '', '', 1, 1, NULL, NULL, 2, 'user-seach');
INSERT INTO `sys_menu` VALUES (287, 251, '任务管理', 'layui-icon ', 'view/system/task.html', '_iframe', 14, 1, NULL, NULL, 1, '');
INSERT INTO `sys_menu` VALUES (288, 285, '查看权限', 'layui-icon ', '', '', 1, 1, NULL, NULL, 2, 'log.list');
INSERT INTO `sys_menu` VALUES (289, 1, '请假管理', 'layui-icon ', 'view/ext/leave.html', '_iframe', 2, 1, NULL, NULL, 1, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名',
  `role_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Key值',
  `details` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `create_time` datetime DEFAULT NULL,
  `role_type` int(1) DEFAULT NULL COMMENT '角色类型，1：菜单权限角色 ，2：数据权限角色',
  `data_type` int(1) DEFAULT NULL COMMENT '数据权限角色的 数据权限类型',
  `data_sql` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色相应的数据权限sql',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (2, '超级管理员', 'superAdmin', '超级管理员很牛逼', 1, NULL, 1, 1, NULL);
INSERT INTO `sys_role` VALUES (3, '普通员工', 'general ', '普通员工2', 0, NULL, 1, 1, NULL);
INSERT INTO `sys_role` VALUES (5, '管理员', 'admin', '', 1, NULL, 1, 1, NULL);
INSERT INTO `sys_role` VALUES (6, '数据权限普通角色本人', 'gg', '数据权限普通角色本人', 1, NULL, 2, 3, NULL);
INSERT INTO `sys_role` VALUES (7, '数据权限ALL', '数据权限', '数据权限', 1, NULL, 2, 1, NULL);

-- ----------------------------
-- Table structure for sys_role_power
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_power`;
CREATE TABLE `sys_role_power`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `power_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_power
-- ----------------------------
INSERT INTO `sys_role_power` VALUES (27, 2, 1);
INSERT INTO `sys_role_power` VALUES (28, 2, 250);
INSERT INTO `sys_role_power` VALUES (29, 2, 269);
INSERT INTO `sys_role_power` VALUES (30, 2, 270);
INSERT INTO `sys_role_power` VALUES (31, 2, 282);
INSERT INTO `sys_role_power` VALUES (32, 2, 285);
INSERT INTO `sys_role_power` VALUES (33, 2, 251);
INSERT INTO `sys_role_power` VALUES (34, 2, 252);
INSERT INTO `sys_role_power` VALUES (35, 2, 264);
INSERT INTO `sys_role_power` VALUES (36, 2, 265);
INSERT INTO `sys_role_power` VALUES (89, 5, 1);
INSERT INTO `sys_role_power` VALUES (90, 5, 250);
INSERT INTO `sys_role_power` VALUES (91, 5, 269);
INSERT INTO `sys_role_power` VALUES (92, 5, 270);
INSERT INTO `sys_role_power` VALUES (93, 5, 282);
INSERT INTO `sys_role_power` VALUES (94, 5, 285);
INSERT INTO `sys_role_power` VALUES (95, 5, 288);
INSERT INTO `sys_role_power` VALUES (96, 5, 289);
INSERT INTO `sys_role_power` VALUES (97, 5, 251);
INSERT INTO `sys_role_power` VALUES (98, 5, 252);
INSERT INTO `sys_role_power` VALUES (99, 5, 258);
INSERT INTO `sys_role_power` VALUES (100, 5, 264);
INSERT INTO `sys_role_power` VALUES (101, 5, 265);
INSERT INTO `sys_role_power` VALUES (102, 5, 266);
INSERT INTO `sys_role_power` VALUES (103, 5, 268);
INSERT INTO `sys_role_power` VALUES (104, 5, 280);
INSERT INTO `sys_role_power` VALUES (105, 5, 281);
INSERT INTO `sys_role_power` VALUES (106, 5, 283);
INSERT INTO `sys_role_power` VALUES (107, 5, 284);

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task`  (
  `task_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务的编码 必须全局唯一',
  `task_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务的名称',
  `task_class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务的类名称',
  `task_cron` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务的cron表达式',
  `create_time` datetime DEFAULT NULL COMMENT '任务创建时间',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `task_state` int(1) DEFAULT NULL COMMENT '任务状态',
  PRIMARY KEY (`task_id`) USING BTREE,
  UNIQUE INDEX `task_code`(`task_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_task
-- ----------------------------
INSERT INTO `sys_task` VALUES (10, 'job2', '第二个任务', 'com.laker.admin.module.task.core.impl.Test2Job', '1/3 * * * * ? ', NULL, 1, 0);
INSERT INTO `sys_task` VALUES (11, 'job1', '第一个任务', 'com.laker.admin.module.task.core.impl.TestJob', '1/3 * * * * ?', NULL, 1, 0);

-- ----------------------------
-- Table structure for sys_tasklog
-- ----------------------------
DROP TABLE IF EXISTS `sys_tasklog`;
CREATE TABLE `sys_tasklog`  (
  `tasklog_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `task_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务编码',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '任务结束时间',
  `status` int(255) DEFAULT NULL COMMENT '状态正常，异常',
  `cost` int(10) DEFAULT NULL COMMENT '耗时 ms',
  `thread_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '线程名称',
  PRIMARY KEY (`tasklog_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dept_id` bigint(20) DEFAULT NULL,
  `sex` int(2) DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `enable` int(2) DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '30a9c7081b257f80a3f672452decc16dafe717cc54ac510afb77257fb6ee3702', '超级管理员', 4, 1, '11111111111', 1, '935009@98.com', '2021-08-15 11:02:15');
INSERT INTO `sys_user` VALUES (16, 'laker', '4b15d2b3b671209e01202331881af5a6044d342dc624d29a53ed6b4402af6d61', '老李', 4, 1, '1', 1, '', '2021-08-09 18:25:32');
INSERT INTO `sys_user` VALUES (17, 'yang', '4b15d2b3b671209e01202331881af5a6044d342dc624d29a53ed6b4402af6d61', '陈总', 5, 1, '', 1, '', '2021-08-10 10:24:23');
INSERT INTO `sys_user` VALUES (18, 'zhang', '4b15d2b3b671209e01202331881af5a6044d342dc624d29a53ed6b4402af6d61', '张总', 5, 2, '', 1, '', '2021-08-10 10:24:38');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (24, 22, 2);
INSERT INTO `sys_user_role` VALUES (25, 22, 3);
INSERT INTO `sys_user_role` VALUES (28, 23, 2);
INSERT INTO `sys_user_role` VALUES (30, 18, 2);
INSERT INTO `sys_user_role` VALUES (31, 18, 3);
INSERT INTO `sys_user_role` VALUES (47, 16, 2);
INSERT INTO `sys_user_role` VALUES (48, 16, 3);
INSERT INTO `sys_user_role` VALUES (49, 16, 5);
INSERT INTO `sys_user_role` VALUES (50, 16, 6);
INSERT INTO `sys_user_role` VALUES (54, 1, 2);
INSERT INTO `sys_user_role` VALUES (55, 1, 3);
INSERT INTO `sys_user_role` VALUES (56, 1, 5);
INSERT INTO `sys_user_role` VALUES (57, 17, 2);
INSERT INTO `sys_user_role` VALUES (58, 17, 5);
INSERT INTO `sys_user_role` VALUES (59, 17, 7);

-- ----------------------------
-- Table structure for wf_hist_order
-- ----------------------------
DROP TABLE IF EXISTS `wf_hist_order`;
CREATE TABLE `wf_hist_order`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `process_Id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程定义ID',
  `order_State` tinyint(1) NOT NULL COMMENT '状态',
  `creator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发起人',
  `create_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发起时间',
  `end_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '完成时间',
  `expire_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '期望完成时间',
  `priority` tinyint(1) DEFAULT NULL COMMENT '优先级',
  `parent_Id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父流程ID',
  `order_No` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程实例编号',
  `variable` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '附属变量json存储',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_HIST_ORDER_PROCESSID`(`process_Id`) USING BTREE,
  INDEX `IDX_HIST_ORDER_NO`(`order_No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '历史流程实例表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_hist_order
-- ----------------------------
INSERT INTO `wf_hist_order` VALUES ('12aea136c0e142708e3a83bca50b9c3a', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, 'yang', '2021-08-12 17:17:00', NULL, NULL, NULL, NULL, '20210812-17:17:00-148-497', '{\"day\":4,\"leaveReason\":\"24538\",\"user1\":\"yang\",\"user2\":\"yang\",\"user3\":\"zhang\"}');
INSERT INTO `wf_hist_order` VALUES ('1938cf20827a49739e388ba12c434ee3', '6d8fa9bd5833446ab2c7c8c799d55d30', 0, '1', '2021-08-19 20:09:19', '2021-08-20 12:09:54', NULL, NULL, NULL, '20210819-20:09:19-440-111', '{\"user1\":\"1\",\"user2\":\"17\",\"day\":null,\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('2938b3d58ebe453abe2ad2ae95bd77e6', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, 'laker', '2021-08-12 11:31:59', NULL, NULL, NULL, NULL, '20210812-11:31:59-487-128', '{\"day\":3,\"leaveReason\":\"发起请假\",\"user1\":\"laker\",\"user2\":\"yang\",\"user3\":\"zhang\"}');
INSERT INTO `wf_hist_order` VALUES ('3381c01f35654a1182ca87cd42d82063', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, '1', '2021-08-16 17:31:10', NULL, NULL, NULL, NULL, '20210816-17:31:10-632-695', '{\"day\":23,\"leaveReason\":\"gaga\",\"user1\":\"1\",\"user2\":\"yang\",\"user3\":\"zhang\"}');
INSERT INTO `wf_hist_order` VALUES ('4202e1bd4e314038a3b26c2079f119ac', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, '1', '2021-08-19 20:07:20', NULL, NULL, NULL, NULL, '20210819-20:07:21-002-338', '{\"user1\":\"1\",\"user2\":\"17\",\"day\":3,\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('6d0b4e44a05d4ef3a529e0a5a222d1a6', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, '16', '2021-08-17 12:51:04', NULL, NULL, NULL, NULL, '20210817-12:51:04-587-237', '{\"day\":5,\"leaveReason\":\"adsfafadf\",\"user1\":\"16\",\"user2\":\"17\",\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('6e349ecb0127445eb16b1991be86183c', '6d8fa9bd5833446ab2c7c8c799d55d30', 0, '17', '2021-08-20 12:10:52', '2021-08-20 12:11:07', NULL, NULL, NULL, '20210820-12:10:52-808-980', '{\"user1\":\"17\",\"user2\":\"17\",\"day\":1,\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('7e89a54ca83a4becb522c8c0addbcf73', '6d8fa9bd5833446ab2c7c8c799d55d30', 0, '16', '2021-08-20 09:35:00', '2021-08-20 12:09:54', NULL, NULL, NULL, '20210820-09:35:00-806-758', '{\"user1\":\"16\",\"user2\":\"17\",\"day\":2,\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('943cbbc547824f538fecc75dc503ed61', '6d8fa9bd5833446ab2c7c8c799d55d30', 0, '16', '2021-08-17 12:55:38', '2021-08-17 12:56:35', NULL, NULL, NULL, '20210817-12:55:38-651-801', '{\"day\":5,\"leaveReason\":\"cesfsfa\",\"user1\":\"16\",\"user2\":\"17\",\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('a576a521e1354e60b5126439b8e2ed0d', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, '1', '2021-08-19 20:20:49', NULL, NULL, NULL, NULL, '20210819-20:20:49-821-90', '{\"user1\":\"1\",\"user2\":\"17\",\"day\":3,\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('bd81a2b7bd3b4d03a9a2bba0ff6a5a53', '6d8fa9bd5833446ab2c7c8c799d55d30', 0, '1', '2021-08-19 20:08:35', '2021-08-20 12:09:55', NULL, NULL, NULL, '20210819-20:08:35-967-274', '{\"user1\":\"1\",\"user2\":\"17\",\"day\":null,\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('cc837aa0e732448fb72143569189a4e8', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, '1', '2021-08-17 10:17:55', NULL, NULL, NULL, NULL, '20210817-10:17:55-035-663', '{\"day\":5,\"leaveReason\":\"324\",\"user1\":\"1\",\"user2\":\"yang\",\"user3\":\"zhang\"}');
INSERT INTO `wf_hist_order` VALUES ('dabb3d6899a84775a6de6998ec3e3240', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, '16', '2021-08-17 11:12:29', NULL, NULL, NULL, NULL, '20210817-11:12:29-974-492', '{\"day\":3,\"leaveReason\":\"测试请假3天\",\"user1\":\"16\",\"user2\":\"yang\",\"user3\":\"zhang\"}');

-- ----------------------------
-- Table structure for wf_hist_task
-- ----------------------------
DROP TABLE IF EXISTS `wf_hist_task`;
CREATE TABLE `wf_hist_task`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `order_Id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程实例ID',
  `task_Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `display_Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务显示名称',
  `task_Type` tinyint(1) NOT NULL COMMENT '任务类型',
  `perform_Type` tinyint(1) DEFAULT NULL COMMENT '参与类型',
  `task_State` tinyint(1) NOT NULL COMMENT '任务状态',
  `operator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务处理人',
  `create_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务创建时间',
  `finish_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务完成时间',
  `expire_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务期望完成时间',
  `action_Url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务处理url',
  `parent_Task_Id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父任务ID',
  `variable` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '附属变量json存储',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_HIST_TASK_ORDER`(`order_Id`) USING BTREE,
  INDEX `IDX_HIST_TASK_TASKNAME`(`task_Name`) USING BTREE,
  INDEX `IDX_HIST_TASK_PARENTTASK`(`parent_Task_Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '历史任务表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_hist_task
-- ----------------------------
INSERT INTO `wf_hist_task` VALUES ('07677acf9e254e91b2ac127878f64ea0', '6d0b4e44a05d4ef3a529e0a5a222d1a6', 'approveDept', '部门经理审批', 0, 0, 0, '17', '2021-08-17 12:51:04', '2021-08-17 12:51:55', NULL, '', 'fd3a81ab60724a5bbba98a675a7e72f6', '{}');
INSERT INTO `wf_hist_task` VALUES ('16369ae12a9740cd8c669336c5632a6e', 'a576a521e1354e60b5126439b8e2ed0d', 'approveDept', '部门经理审批', 0, 0, 0, '17', '2021-08-19 20:20:49', '2021-08-20 12:09:54', NULL, '', '2180350ae6604ebc9533d3f4ce457fa4', '{}');
INSERT INTO `wf_hist_task` VALUES ('16d1c3ebd3ea4e0d81bbead73286a4dd', '943cbbc547824f538fecc75dc503ed61', 'apply', '请假申请2', 0, 0, 0, '16', '2021-08-17 12:55:38', '2021-08-17 12:55:38', NULL, '', 'start', '{\"day\":5,\"leaveReason\":\"cesfsfa\",\"user1\":\"16\",\"user2\":\"17\",\"user3\":\"18\",\"S-ACTOR\":\"16\"}');
INSERT INTO `wf_hist_task` VALUES ('208bf5598ce249e6ad65fa0ed1ca94b7', '12aea136c0e142708e3a83bca50b9c3a', 'apply', '请假申请', 0, 0, 0, 'yang', '2021-08-12 17:17:00', '2021-08-12 17:17:00', NULL, '', 'start', '{\"day\":4,\"leaveReason\":\"24538\",\"user1\":\"yang\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"yang\"}');
INSERT INTO `wf_hist_task` VALUES ('2180350ae6604ebc9533d3f4ce457fa4', 'a576a521e1354e60b5126439b8e2ed0d', 'apply', '请假申请2', 0, 0, 0, '1', '2021-08-19 20:20:49', '2021-08-19 20:20:49', NULL, '', 'start', '{\"user1\":\"1\",\"user2\":\"17\",\"S-ACTOR\":\"1\",\"day\":3,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('243036b8c7ff49138adbd554ec6dfb6f', 'bd81a2b7bd3b4d03a9a2bba0ff6a5a53', 'apply', '请假申请2', 0, 0, 0, '1', '2021-08-19 20:08:35', '2021-08-19 20:08:35', NULL, '', 'start', '{\"user1\":\"1\",\"user2\":\"17\",\"S-ACTOR\":\"1\",\"day\":null,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('2c3937c59df04691bf52cb4e5f5ab4fa', '4202e1bd4e314038a3b26c2079f119ac', 'approveDept', '部门经理审批', 0, 0, 0, '17', '2021-08-19 20:07:21', '2021-08-20 12:09:55', NULL, '', 'df5ef7b9514e492cbcff3d52f59c0ce3', '{}');
INSERT INTO `wf_hist_task` VALUES ('2f0dc046e8cb4770ab34bd12064a6c7b', '1938cf20827a49739e388ba12c434ee3', 'apply', '请假申请2', 0, 0, 0, '1', '2021-08-19 20:09:19', '2021-08-19 20:09:19', NULL, '', 'start', '{\"user1\":\"1\",\"user2\":\"17\",\"S-ACTOR\":\"1\",\"day\":null,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('4693283691fc41a0b06746006064e5ee', '2938b3d58ebe453abe2ad2ae95bd77e6', 'apply', '请假申请', 0, 0, 0, 'laker', '2021-08-12 11:31:59', '2021-08-12 11:31:59', NULL, '', 'start', '{\"day\":3,\"leaveReason\":\"发起请假\",\"user1\":\"laker\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"laker\"}');
INSERT INTO `wf_hist_task` VALUES ('4f0f776c5645467b90987d54a2a8adbc', 'cc837aa0e732448fb72143569189a4e8', 'apply', '请假申请2', 0, 0, 0, '1', '2021-08-17 10:17:55', '2021-08-17 10:17:55', NULL, '', 'start', '{\"day\":5,\"leaveReason\":\"324\",\"user1\":\"1\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"1\"}');
INSERT INTO `wf_hist_task` VALUES ('72645b90dacc45449993e6784eb61ca6', '1938cf20827a49739e388ba12c434ee3', 'approveDept', '部门经理审批', 0, 0, 0, '17', '2021-08-19 20:09:19', '2021-08-20 12:09:54', NULL, '', '2f0dc046e8cb4770ab34bd12064a6c7b', '{}');
INSERT INTO `wf_hist_task` VALUES ('8bc2e2ac852f4718959f7b7133a07bbc', 'bd81a2b7bd3b4d03a9a2bba0ff6a5a53', 'approveDept', '部门经理审批', 0, 0, 0, '17', '2021-08-19 20:08:35', '2021-08-20 12:09:55', NULL, '', '243036b8c7ff49138adbd554ec6dfb6f', '{}');
INSERT INTO `wf_hist_task` VALUES ('997e05acadde407095731d4f0f31728b', '3381c01f35654a1182ca87cd42d82063', 'apply', '请假申请2', 0, 0, 0, '1', '2021-08-16 17:31:10', '2021-08-16 17:31:10', NULL, '', 'start', '{\"day\":23,\"leaveReason\":\"gaga\",\"user1\":\"1\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"1\"}');
INSERT INTO `wf_hist_task` VALUES ('abbacc65bb1b4251a3fc22b8d0d95507', '943cbbc547824f538fecc75dc503ed61', 'approveBoss', '总经理审批', 0, 0, 0, '18', '2021-08-17 12:56:07', '2021-08-17 12:56:34', NULL, '', 'ea58fa2f7ff848bcb7a66b953e8d3a38', '{}');
INSERT INTO `wf_hist_task` VALUES ('b0c9debfa0bb4ebe9f10542536c03197', '6e349ecb0127445eb16b1991be86183c', 'apply', '请假申请2', 0, 0, 0, '17', '2021-08-20 12:10:52', '2021-08-20 12:10:52', NULL, '', 'start', '{\"user1\":\"17\",\"user2\":\"17\",\"S-ACTOR\":\"17\",\"day\":1,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('bd39bcbe266144ceb2aba6dbff14ffd7', '7e89a54ca83a4becb522c8c0addbcf73', 'apply', '请假申请2', 0, 0, 0, '16', '2021-08-20 09:35:00', '2021-08-20 09:35:00', NULL, '', 'start', '{\"user1\":\"16\",\"user2\":\"17\",\"S-ACTOR\":\"16\",\"day\":2,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('cd4149a5abe440aab3c8875209078613', '6e349ecb0127445eb16b1991be86183c', 'approveDept', '部门经理审批', 0, 0, 0, '17', '2021-08-20 12:10:52', '2021-08-20 12:11:07', NULL, '', 'b0c9debfa0bb4ebe9f10542536c03197', '{}');
INSERT INTO `wf_hist_task` VALUES ('ce7d34f4244742a4b020922112231e7d', '2938b3d58ebe453abe2ad2ae95bd77e6', 'approveDept', '部门经理审批', 0, 0, 0, 'yang', '2021-08-12 11:31:59', '2021-08-16 15:47:10', NULL, '', '4693283691fc41a0b06746006064e5ee', '{\"rejectReason\":null}');
INSERT INTO `wf_hist_task` VALUES ('dde5dc577d4b4369950bcf393acdd53b', '12aea136c0e142708e3a83bca50b9c3a', 'approveDept', '部门经理审批', 0, 0, 0, 'yang', '2021-08-12 17:17:00', '2021-08-12 17:18:34', NULL, '', '208bf5598ce249e6ad65fa0ed1ca94b7', '{}');
INSERT INTO `wf_hist_task` VALUES ('df5ef7b9514e492cbcff3d52f59c0ce3', '4202e1bd4e314038a3b26c2079f119ac', 'apply', '请假申请2', 0, 0, 0, '1', '2021-08-19 20:07:21', '2021-08-19 20:07:21', NULL, '', 'start', '{\"user1\":\"1\",\"user2\":\"17\",\"S-ACTOR\":\"1\",\"day\":3,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('e2bdb19450da4132a7916bb3fa2409ff', 'dabb3d6899a84775a6de6998ec3e3240', 'apply', '请假申请2', 0, 0, 0, '16', '2021-08-17 11:12:30', '2021-08-17 11:12:30', NULL, '', 'start', '{\"day\":3,\"leaveReason\":\"测试请假3天\",\"user1\":\"16\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"16\"}');
INSERT INTO `wf_hist_task` VALUES ('ea58fa2f7ff848bcb7a66b953e8d3a38', '943cbbc547824f538fecc75dc503ed61', 'approveDept', '部门经理审批', 0, 0, 0, '17', '2021-08-17 12:55:38', '2021-08-17 12:56:07', NULL, '', '16d1c3ebd3ea4e0d81bbead73286a4dd', '{}');
INSERT INTO `wf_hist_task` VALUES ('f27218f91f694b578a13267305b6e841', '7e89a54ca83a4becb522c8c0addbcf73', 'approveDept', '部门经理审批', 0, 0, 0, '17', '2021-08-20 09:35:00', '2021-08-20 12:09:54', NULL, '', 'bd39bcbe266144ceb2aba6dbff14ffd7', '{}');
INSERT INTO `wf_hist_task` VALUES ('fd3a81ab60724a5bbba98a675a7e72f6', '6d0b4e44a05d4ef3a529e0a5a222d1a6', 'apply', '请假申请2', 0, 0, 0, '16', '2021-08-17 12:51:04', '2021-08-17 12:51:04', NULL, '', 'start', '{\"day\":5,\"leaveReason\":\"adsfafadf\",\"user1\":\"16\",\"user2\":\"17\",\"user3\":\"18\",\"S-ACTOR\":\"16\"}');

-- ----------------------------
-- Table structure for wf_hist_task_actor
-- ----------------------------
DROP TABLE IF EXISTS `wf_hist_task_actor`;
CREATE TABLE `wf_hist_task_actor`  (
  `task_Id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务ID',
  `actor_Id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参与者ID',
  INDEX `IDX_HIST_TASKACTOR_TASK`(`task_Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '历史任务参与者表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_hist_task_actor
-- ----------------------------
INSERT INTO `wf_hist_task_actor` VALUES ('4693283691fc41a0b06746006064e5ee', 'laker');
INSERT INTO `wf_hist_task_actor` VALUES ('208bf5598ce249e6ad65fa0ed1ca94b7', 'yang');
INSERT INTO `wf_hist_task_actor` VALUES ('dde5dc577d4b4369950bcf393acdd53b', 'yang');
INSERT INTO `wf_hist_task_actor` VALUES ('ce7d34f4244742a4b020922112231e7d', 'yang');
INSERT INTO `wf_hist_task_actor` VALUES ('997e05acadde407095731d4f0f31728b', '1');
INSERT INTO `wf_hist_task_actor` VALUES ('4f0f776c5645467b90987d54a2a8adbc', '1');
INSERT INTO `wf_hist_task_actor` VALUES ('e2bdb19450da4132a7916bb3fa2409ff', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('fd3a81ab60724a5bbba98a675a7e72f6', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('07677acf9e254e91b2ac127878f64ea0', '17');
INSERT INTO `wf_hist_task_actor` VALUES ('16d1c3ebd3ea4e0d81bbead73286a4dd', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('ea58fa2f7ff848bcb7a66b953e8d3a38', '17');
INSERT INTO `wf_hist_task_actor` VALUES ('abbacc65bb1b4251a3fc22b8d0d95507', '18');
INSERT INTO `wf_hist_task_actor` VALUES ('df5ef7b9514e492cbcff3d52f59c0ce3', '1');
INSERT INTO `wf_hist_task_actor` VALUES ('243036b8c7ff49138adbd554ec6dfb6f', '1');
INSERT INTO `wf_hist_task_actor` VALUES ('2f0dc046e8cb4770ab34bd12064a6c7b', '1');
INSERT INTO `wf_hist_task_actor` VALUES ('2180350ae6604ebc9533d3f4ce457fa4', '1');
INSERT INTO `wf_hist_task_actor` VALUES ('bd39bcbe266144ceb2aba6dbff14ffd7', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('f27218f91f694b578a13267305b6e841', '17');
INSERT INTO `wf_hist_task_actor` VALUES ('16369ae12a9740cd8c669336c5632a6e', '17');
INSERT INTO `wf_hist_task_actor` VALUES ('72645b90dacc45449993e6784eb61ca6', '17');
INSERT INTO `wf_hist_task_actor` VALUES ('8bc2e2ac852f4718959f7b7133a07bbc', '17');
INSERT INTO `wf_hist_task_actor` VALUES ('2c3937c59df04691bf52cb4e5f5ab4fa', '17');
INSERT INTO `wf_hist_task_actor` VALUES ('b0c9debfa0bb4ebe9f10542536c03197', '17');
INSERT INTO `wf_hist_task_actor` VALUES ('cd4149a5abe440aab3c8875209078613', '17');

-- ----------------------------
-- Table structure for wf_order
-- ----------------------------
DROP TABLE IF EXISTS `wf_order`;
CREATE TABLE `wf_order`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `parent_Id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父流程ID',
  `process_Id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程定义ID',
  `creator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发起人',
  `create_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发起时间',
  `expire_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '期望完成时间',
  `last_Update_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上次更新时间',
  `last_Updator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上次更新人',
  `priority` tinyint(1) DEFAULT NULL COMMENT '优先级',
  `parent_Node_Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父流程依赖的节点名称',
  `order_No` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程实例编号',
  `variable` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '附属变量json存储',
  `version` int(3) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_ORDER_PROCESSID`(`process_Id`) USING BTREE,
  INDEX `IDX_ORDER_NO`(`order_No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程实例表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_order
-- ----------------------------
INSERT INTO `wf_order` VALUES ('12aea136c0e142708e3a83bca50b9c3a', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', 'yang', '2021-08-12 17:17:00', NULL, '2021-08-12 17:18:34', 'yang', NULL, NULL, '20210812-17:17:00-148-497', '{\"day\":4,\"leaveReason\":\"24538\",\"user1\":\"yang\",\"user2\":\"yang\",\"user3\":\"zhang\"}', 2);
INSERT INTO `wf_order` VALUES ('2938b3d58ebe453abe2ad2ae95bd77e6', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', 'laker', '2021-08-12 11:31:59', NULL, '2021-08-16 15:47:10', 'yang', NULL, NULL, '20210812-11:31:59-487-128', '{\"day\":3,\"leaveReason\":\"发起请假\",\"user1\":\"laker\",\"user2\":\"yang\",\"user3\":\"zhang\"}', 2);
INSERT INTO `wf_order` VALUES ('3381c01f35654a1182ca87cd42d82063', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', '1', '2021-08-16 17:31:10', NULL, '2021-08-16 17:31:14', '1', NULL, NULL, '20210816-17:31:10-632-695', '{\"day\":23,\"leaveReason\":\"gaga\",\"user1\":\"1\",\"user2\":\"yang\",\"user3\":\"zhang\"}', 1);
INSERT INTO `wf_order` VALUES ('4202e1bd4e314038a3b26c2079f119ac', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', '1', '2021-08-19 20:07:20', NULL, '2021-08-20 12:09:55', '17', NULL, NULL, '20210819-20:07:21-002-338', '{\"user1\":\"1\",\"user2\":\"17\",\"day\":3,\"user3\":\"18\"}', 2);
INSERT INTO `wf_order` VALUES ('6d0b4e44a05d4ef3a529e0a5a222d1a6', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', '16', '2021-08-17 12:51:04', NULL, '2021-08-17 12:51:55', '17', NULL, NULL, '20210817-12:51:04-587-237', '{\"day\":5,\"leaveReason\":\"adsfafadf\",\"user1\":\"16\",\"user2\":\"17\",\"user3\":\"18\"}', 2);
INSERT INTO `wf_order` VALUES ('a576a521e1354e60b5126439b8e2ed0d', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', '1', '2021-08-19 20:20:49', NULL, '2021-08-20 12:09:54', '17', NULL, NULL, '20210819-20:20:49-821-90', '{\"user1\":\"1\",\"user2\":\"17\",\"day\":3,\"user3\":\"18\"}', 2);
INSERT INTO `wf_order` VALUES ('cc837aa0e732448fb72143569189a4e8', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', '1', '2021-08-17 10:17:55', NULL, '2021-08-17 10:17:55', '1', NULL, NULL, '20210817-10:17:55-035-663', '{\"day\":5,\"leaveReason\":\"324\",\"user1\":\"1\",\"user2\":\"yang\",\"user3\":\"zhang\"}', 1);
INSERT INTO `wf_order` VALUES ('dabb3d6899a84775a6de6998ec3e3240', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', '16', '2021-08-17 11:12:29', NULL, '2021-08-17 11:12:30', '16', NULL, NULL, '20210817-11:12:29-974-492', '{\"day\":3,\"leaveReason\":\"测试请假3天\",\"user1\":\"16\",\"user2\":\"yang\",\"user3\":\"zhang\"}', 1);

-- ----------------------------
-- Table structure for wf_process
-- ----------------------------
DROP TABLE IF EXISTS `wf_process`;
CREATE TABLE `wf_process`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程名称',
  `display_Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程显示名称',
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程类型',
  `instance_Url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '实例url',
  `state` tinyint(1) DEFAULT NULL COMMENT '流程是否可用',
  `content` longblob COMMENT '流程模型定义',
  `version` int(2) DEFAULT NULL COMMENT '版本',
  `create_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_PROCESS_NAME`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程定义表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_process
-- ----------------------------
INSERT INTO `wf_process` VALUES ('6d8fa9bd5833446ab2c7c8c799d55d30', 'leave', '请假流程测试', NULL, '', 1, 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D226E6F223F3E0A3C70726F63657373206E616D653D226C656176652220646973706C61794E616D653D22E8AFB7E58187E6B581E7A88BE6B58BE8AF9522203E0A3C7374617274206C61796F75743D2232342C3132342C35302C353022206E616D653D227374617274312220646973706C61794E616D653D2273746172743122203E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D226170706C7922206E616D653D227472616E736974696F6E3122202F3E0A3C2F73746172743E0A3C656E64206C61796F75743D223537302C3132342C35302C353022206E616D653D22656E64312220646973706C61794E616D653D22656E643122203E0A3C2F656E643E0A3C7461736B206C61796F75743D223131372C3132322C3130302C353022206E616D653D226170706C792220646973706C61794E616D653D22E8AFB7E58187E794B3E8AFB732222061737369676E65653D22757365723122207461736B547970653D224D616A6F722220706572666F726D547970653D22414E5922203E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D22617070726F76654465707422206E616D653D227472616E736974696F6E3222202F3E0A3C2F7461736B3E0A3C7461736B206C61796F75743D223237322C3132322C3130302C353022206E616D653D22617070726F7665446570742220646973706C61794E616D653D22E983A8E997A8E7BB8FE79086E5AEA1E689B9222061737369676E65653D22757365723222207461736B547970653D224D616A6F722220706572666F726D547970653D22414E5922203E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D226465636973696F6E3122206E616D653D227472616E736974696F6E3322202F3E0A3C2F7461736B3E0A3C6465636973696F6E206C61796F75743D223432362C3132342C35302C353022206E616D653D226465636973696F6E312220657870723D2223646179202667743B2032203F20277472616E736974696F6E3527203A20277472616E736974696F6E34272220646973706C61794E616D653D226465636973696F6E3122203E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D22656E643122206E616D653D227472616E736974696F6E342220646973706C61794E616D653D22266C743B3D32E5A4A922202F3E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D22617070726F7665426F737322206E616D653D227472616E736974696F6E352220646973706C61794E616D653D222667743B32E5A4A922202F3E0A3C2F6465636973696F6E3E0A3C7461736B206C61796F75743D223430342C3233312C3130302C353022206E616D653D22617070726F7665426F73732220646973706C61794E616D653D22E680BBE7BB8FE79086E5AEA1E689B9222061737369676E65653D22757365723322207461736B547970653D224D616A6F722220706572666F726D547970653D22414E5922203E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D22656E643122206E616D653D227472616E736974696F6E3622202F3E0A3C2F7461736B3E0A3C2F70726F636573733E, 2, '2021-08-11 19:06:37', NULL);
INSERT INTO `wf_process` VALUES ('a7a5d0b6a4e542ce99308bebeedb1452', 'test', '测试单点流程', NULL, '', 1, 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D226E6F223F3E0A3C70726F63657373206E616D653D22746573742220646973706C61794E616D653D22E6B58BE8AF95E58D95E782B9E6B581E7A88B22203E0A3C7374617274206C61796F75743D223131302C3139362C35302C353022206E616D653D22737461727422203E0A3C7472616E736974696F6E206F66667365743D22302C2D31302220746F3D22726563743322206E616D653D22706174683422202F3E0A3C2F73746172743E0A3C656E64206C61796F75743D223437312C3139392C35302C353022206E616D653D22656E6422203E0A3C2F656E643E0A3C7461736B206C61796F75743D223235332C3139382C3130302C353022206E616D653D2272656374332220646973706C61794E616D653D22726563743322203E0A3C7472616E736974696F6E206F66667365743D22302C2D31302220746F3D22656E6422206E616D653D22706174683522202F3E0A3C2F7461736B3E0A3C2F70726F636573733E, 0, '2021-08-18 16:42:25', NULL);

-- ----------------------------
-- Table structure for wf_task
-- ----------------------------
DROP TABLE IF EXISTS `wf_task`;
CREATE TABLE `wf_task`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `order_Id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程实例ID',
  `task_Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `display_Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务显示名称',
  `task_Type` tinyint(1) NOT NULL COMMENT '任务类型',
  `perform_Type` tinyint(1) DEFAULT NULL COMMENT '参与类型',
  `operator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务处理人',
  `create_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务创建时间',
  `finish_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务完成时间',
  `expire_Time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务期望完成时间',
  `action_Url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务处理的url',
  `parent_Task_Id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父任务ID',
  `variable` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '附属变量json存储',
  `version` tinyint(1) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_TASK_ORDER`(`order_Id`) USING BTREE,
  INDEX `IDX_TASK_TASKNAME`(`task_Name`) USING BTREE,
  INDEX `IDX_TASK_PARENTTASK`(`parent_Task_Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '任务表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_task
-- ----------------------------
INSERT INTO `wf_task` VALUES ('1d0e94562ecb4aa4bfa1d6b60550182a', 'dabb3d6899a84775a6de6998ec3e3240', 'approveDept', '部门经理审批', 0, 0, NULL, '2021-08-17 11:12:30', NULL, NULL, '', 'e2bdb19450da4132a7916bb3fa2409ff', '{\"day\":3,\"leaveReason\":\"测试请假3天\",\"user1\":\"16\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"yang\"}', 0);
INSERT INTO `wf_task` VALUES ('4290a498e5dc488e99edf7a677a11377', 'a576a521e1354e60b5126439b8e2ed0d', 'approveBoss', '总经理审批', 0, 0, NULL, '2021-08-20 12:09:54', NULL, NULL, '', '16369ae12a9740cd8c669336c5632a6e', '{\"user1\":\"1\",\"user2\":\"17\",\"S-ACTOR\":\"18\",\"day\":3,\"user3\":\"18\"}', 0);
INSERT INTO `wf_task` VALUES ('5489c4d51d4a4e95b40d716bf6c0ccaa', '12aea136c0e142708e3a83bca50b9c3a', 'approveBoss', '总经理审批', 0, 0, NULL, '2021-08-12 17:18:34', NULL, NULL, '', 'dde5dc577d4b4369950bcf393acdd53b', '{\"user1\":\"yang\",\"user2\":\"yang\",\"leaveReason\":\"24538\",\"S-ACTOR\":\"approveBoss.operator\",\"day\":4,\"user3\":\"zhang\"}', 0);
INSERT INTO `wf_task` VALUES ('57a8b088f4554f22afe1488bd8865dbe', 'cc837aa0e732448fb72143569189a4e8', 'approveDept', '部门经理审批', 0, 0, NULL, '2021-08-17 10:17:55', NULL, NULL, '', '4f0f776c5645467b90987d54a2a8adbc', '{\"day\":5,\"leaveReason\":\"324\",\"user1\":\"1\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"yang\"}', 0);
INSERT INTO `wf_task` VALUES ('6ecaf3f51e994b4ebe3aa393e15acfd2', '4202e1bd4e314038a3b26c2079f119ac', 'approveBoss', '总经理审批', 0, 0, NULL, '2021-08-20 12:09:55', NULL, NULL, '', '2c3937c59df04691bf52cb4e5f5ab4fa', '{\"user1\":\"1\",\"user2\":\"17\",\"S-ACTOR\":\"18\",\"day\":3,\"user3\":\"18\"}', 0);
INSERT INTO `wf_task` VALUES ('9c0c8e5abf1347c0a5c55f38cf8fbf2b', '3381c01f35654a1182ca87cd42d82063', 'approveDept', '部门经理审批', 0, 0, NULL, '2021-08-16 17:31:14', NULL, NULL, '', '997e05acadde407095731d4f0f31728b', '{\"day\":23,\"leaveReason\":\"gaga\",\"user1\":\"1\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"yang\"}', 0);
INSERT INTO `wf_task` VALUES ('b791e76f2b374fbd9a539a9d5590f2d9', '6d0b4e44a05d4ef3a529e0a5a222d1a6', 'approveBoss', '总经理审批', 0, 0, NULL, '2021-08-17 12:51:55', NULL, NULL, '', '07677acf9e254e91b2ac127878f64ea0', '{\"user1\":\"16\",\"user2\":\"17\",\"leaveReason\":\"adsfafadf\",\"S-ACTOR\":\"approveBoss.operator\",\"day\":5,\"user3\":\"18\"}', 0);
INSERT INTO `wf_task` VALUES ('c12287f093b24889b3514406da4db7ae', '2938b3d58ebe453abe2ad2ae95bd77e6', 'apply', '请假申请', 0, 0, 'laker', '2021-08-16 15:47:10', NULL, NULL, '', 'start', '{\"day\":3,\"leaveReason\":\"发起请假\",\"user1\":\"laker\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"laker\"}', 0);

-- ----------------------------
-- Table structure for wf_task_actor
-- ----------------------------
DROP TABLE IF EXISTS `wf_task_actor`;
CREATE TABLE `wf_task_actor`  (
  `task_Id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务ID',
  `actor_Id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参与者ID',
  INDEX `IDX_TASKACTOR_TASK`(`task_Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '任务参与者表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_task_actor
-- ----------------------------
INSERT INTO `wf_task_actor` VALUES ('5489c4d51d4a4e95b40d716bf6c0ccaa', 'approveBoss.operator');
INSERT INTO `wf_task_actor` VALUES ('c12287f093b24889b3514406da4db7ae', 'laker');
INSERT INTO `wf_task_actor` VALUES ('9c0c8e5abf1347c0a5c55f38cf8fbf2b', 'yang');
INSERT INTO `wf_task_actor` VALUES ('57a8b088f4554f22afe1488bd8865dbe', 'yang');
INSERT INTO `wf_task_actor` VALUES ('1d0e94562ecb4aa4bfa1d6b60550182a', 'yang');
INSERT INTO `wf_task_actor` VALUES ('b791e76f2b374fbd9a539a9d5590f2d9', 'approveBoss.operator');
INSERT INTO `wf_task_actor` VALUES ('4290a498e5dc488e99edf7a677a11377', '18');
INSERT INTO `wf_task_actor` VALUES ('6ecaf3f51e994b4ebe3aa393e15acfd2', '18');

SET FOREIGN_KEY_CHECKS = 1;
