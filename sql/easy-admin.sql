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

 Date: 16/08/2021 15:42:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ext_log
-- ----------------------------
DROP TABLE IF EXISTS `ext_log`;
CREATE TABLE `ext_log`  (
  `log_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'ip地址',
  `client` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '浏览器或者app信息',
  `request` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求',
  `response` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '响应',
  `cost` int(10) DEFAULT NULL COMMENT '耗时ms',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日志' ROW_FORMAT = Compact;

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
INSERT INTO `sys_dept` VALUES (6, '研发一部-1', '今天天气如何', 4, 1, 2);
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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '大法士大夫' ROW_FORMAT = Compact;

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
) ENGINE = InnoDB AUTO_INCREMENT = 286 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单表' ROW_FORMAT = Compact;

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
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (2, '超级管理员', 'admin', '超级管理员很牛逼2', 1, NULL);
INSERT INTO `sys_role` VALUES (3, '普通员工', 'general ', '普通员工2', 0, NULL);

-- ----------------------------
-- Table structure for sys_role_power
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_power`;
CREATE TABLE `sys_role_power`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `power_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_power
-- ----------------------------
INSERT INTO `sys_role_power` VALUES (4, 2, 1);
INSERT INTO `sys_role_power` VALUES (5, 2, 250);
INSERT INTO `sys_role_power` VALUES (6, 2, 269);
INSERT INTO `sys_role_power` VALUES (7, 2, 270);

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
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (16, 'laker', '123456', '老李', 4, 1, '1', 1, '', '2021-08-09 18:25:32');
INSERT INTO `sys_user` VALUES (17, 'yang', '123456', '陈阳', 5, 1, '', 1, '', '2021-08-10 10:24:23');
INSERT INTO `sys_user` VALUES (18, 'zhang', '123456', '张总', 5, 2, '', 1, '', '2021-08-10 10:24:38');
INSERT INTO `sys_user` VALUES (23, 'admin', '123456', '超级管理员', 4, 1, '11111111111', 1, '935009@98.com', '2021-08-15 11:02:15');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (7, NULL, 2);
INSERT INTO `sys_user_role` VALUES (16, 19, 3);
INSERT INTO `sys_user_role` VALUES (17, NULL, 2);
INSERT INTO `sys_user_role` VALUES (19, NULL, 2);
INSERT INTO `sys_user_role` VALUES (24, 22, 2);
INSERT INTO `sys_user_role` VALUES (25, 22, 3);
INSERT INTO `sys_user_role` VALUES (27, 16, 2);
INSERT INTO `sys_user_role` VALUES (28, 23, 2);
INSERT INTO `sys_user_role` VALUES (29, 17, 2);
INSERT INTO `sys_user_role` VALUES (30, 18, 2);
INSERT INTO `sys_user_role` VALUES (31, 18, 3);

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
INSERT INTO `wf_hist_order` VALUES ('2938b3d58ebe453abe2ad2ae95bd77e6', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, 'laker', '2021-08-12 11:31:59', NULL, NULL, NULL, NULL, '20210812-11:31:59-487-128', '{\"day\":3,\"leaveReason\":\"发起请假\",\"user1\":\"laker\",\"user2\":\"yang\",\"user3\":\"zhang\"}');

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
INSERT INTO `wf_hist_task` VALUES ('208bf5598ce249e6ad65fa0ed1ca94b7', '12aea136c0e142708e3a83bca50b9c3a', 'apply', '请假申请', 0, 0, 0, 'yang', '2021-08-12 17:17:00', '2021-08-12 17:17:00', NULL, '', 'start', '{\"day\":4,\"leaveReason\":\"24538\",\"user1\":\"yang\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"yang\"}');
INSERT INTO `wf_hist_task` VALUES ('4693283691fc41a0b06746006064e5ee', '2938b3d58ebe453abe2ad2ae95bd77e6', 'apply', '请假申请', 0, 0, 0, 'laker', '2021-08-12 11:31:59', '2021-08-12 11:31:59', NULL, '', 'start', '{\"day\":3,\"leaveReason\":\"发起请假\",\"user1\":\"laker\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"laker\"}');
INSERT INTO `wf_hist_task` VALUES ('dde5dc577d4b4369950bcf393acdd53b', '12aea136c0e142708e3a83bca50b9c3a', 'approveDept', '部门经理审批', 0, 0, 0, 'yang', '2021-08-12 17:17:00', '2021-08-12 17:18:34', NULL, '', '208bf5598ce249e6ad65fa0ed1ca94b7', '{}');

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
INSERT INTO `wf_order` VALUES ('2938b3d58ebe453abe2ad2ae95bd77e6', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', 'laker', '2021-08-12 11:31:59', NULL, '2021-08-12 11:31:59', 'laker', NULL, NULL, '20210812-11:31:59-487-128', '{\"day\":3,\"leaveReason\":\"发起请假\",\"user1\":\"laker\",\"user2\":\"yang\",\"user3\":\"zhang\"}', 1);

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
INSERT INTO `wf_process` VALUES ('6d8fa9bd5833446ab2c7c8c799d55d30', 'leave', '请假流程测试', NULL, '', 1, 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D226E6F223F3E0D0A0D0A3C70726F6365737320646973706C61794E616D653D22E8AFB7E58187E6B581E7A88BE6B58BE8AF952220696E7374616E636555726C3D2222206E616D653D226C65617665223E0D0A202020203C737461727420646973706C61794E616D653D2273746172743122206C61796F75743D2232342C3132342C2D312C2D3122206E616D653D22737461727431223E0D0A20202020202020203C7472616E736974696F6E20673D2222206E616D653D227472616E736974696F6E3122206F66667365743D22302C302220746F3D226170706C79222F3E0D0A202020203C2F73746172743E0D0A202020203C656E6420646973706C61794E616D653D22656E643122206C61796F75743D223537302C3132342C2D312C2D3122206E616D653D22656E6431222F3E0D0A0D0A202020203C7461736B2061737369676E65653D2275736572312220646973706C61794E616D653D22E8AFB7E58187E794B3E8AFB72220666F726D3D2222206C61796F75743D223131372C3132322C2D312C2D3122206E616D653D226170706C79220D0A20202020202020202020706572666F726D547970653D22414E59223E0D0A20202020202020203C7472616E736974696F6E20673D2222206E616D653D227472616E736974696F6E3222206F66667365743D22302C302220746F3D22617070726F766544657074222F3E0D0A202020203C2F7461736B3E0D0A202020203C7461736B2061737369676E65653D2275736572322220646973706C61794E616D653D22E983A8E997A8E7BB8FE79086E5AEA1E689B92220666F726D3D2222206C61796F75743D223237322C3132322C2D312C2D31220D0A202020202020202020206E616D653D22617070726F7665446570742220706572666F726D547970653D22414E59223E0D0A20202020202020203C7472616E736974696F6E20673D2222206E616D653D227472616E736974696F6E3322206F66667365743D22302C302220746F3D226465636973696F6E31222F3E0D0A202020203C2F7461736B3E0D0A202020203C6465636973696F6E20646973706C61794E616D653D226465636973696F6E312220657870723D2223646179202667743B2032203F20277472616E736974696F6E3527203A20277472616E736974696F6E342722206C61796F75743D223432362C3132342C2D312C2D31220D0A20202020202020202020202020206E616D653D226465636973696F6E31223E0D0A20202020202020203C7472616E736974696F6E20646973706C61794E616D653D22266C743B3D32E5A4A92220673D2222206E616D653D227472616E736974696F6E3422206F66667365743D22302C302220746F3D22656E6431222F3E0D0A20202020202020203C7472616E736974696F6E20646973706C61794E616D653D222667743B32E5A4A92220673D2222206E616D653D227472616E736974696F6E3522206F66667365743D22302C302220746F3D22617070726F7665426F7373222F3E0D0A202020203C2F6465636973696F6E3E0D0A202020203C7461736B2061737369676E65653D22617070726F7665426F73732E6F70657261746F722220646973706C61794E616D653D22E680BBE7BB8FE79086E5AEA1E689B92220666F726D3D2222206C61796F75743D223430342C3233312C2D312C2D31220D0A202020202020202020206E616D653D22617070726F7665426F73732220706572666F726D547970653D22414E59223E0D0A20202020202020203C7472616E736974696F6E20673D2222206E616D653D227472616E736974696F6E3622206F66667365743D22302C302220746F3D22656E6431222F3E0D0A202020203C2F7461736B3E0D0A3C2F70726F636573733E, 2, '2021-08-11 19:06:37', NULL);
INSERT INTO `wf_process` VALUES ('e318b419e37c4f79a0a0381a9ae0f8c3', 'faf', 'adf', NULL, '', 0, 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D226E6F223F3E0A3C70726F63657373206E616D653D226661662220646973706C61794E616D653D2261646622203E0A3C7374617274206C61796F75743D2239382C3135342C35302C353022206E616D653D22737461727422203E0A3C2F73746172743E0A3C656E64206C61796F75743D223430322C3136362C35302C353022206E616D653D22656E6422203E0A3C2F656E643E0A3C2F70726F636573733E, 0, '2021-08-16 00:06:25', NULL);

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
INSERT INTO `wf_task` VALUES ('5489c4d51d4a4e95b40d716bf6c0ccaa', '12aea136c0e142708e3a83bca50b9c3a', 'approveBoss', '总经理审批', 0, 0, NULL, '2021-08-12 17:18:34', NULL, NULL, '', 'dde5dc577d4b4369950bcf393acdd53b', '{\"user1\":\"yang\",\"user2\":\"yang\",\"leaveReason\":\"24538\",\"S-ACTOR\":\"approveBoss.operator\",\"day\":4,\"user3\":\"zhang\"}', 0);
INSERT INTO `wf_task` VALUES ('ce7d34f4244742a4b020922112231e7d', '2938b3d58ebe453abe2ad2ae95bd77e6', 'approveDept', '部门经理审批', 0, 0, NULL, '2021-08-12 11:31:59', NULL, NULL, '', '4693283691fc41a0b06746006064e5ee', '{\"day\":3,\"leaveReason\":\"发起请假\",\"user1\":\"laker\",\"user2\":\"yang\",\"user3\":\"zhang\",\"S-ACTOR\":\"yang\"}', 0);

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
INSERT INTO `wf_task_actor` VALUES ('ce7d34f4244742a4b020922112231e7d', 'yang');
INSERT INTO `wf_task_actor` VALUES ('5489c4d51d4a4e95b40d716bf6c0ccaa', 'approveBoss.operator');

SET FOREIGN_KEY_CHECKS = 1;
