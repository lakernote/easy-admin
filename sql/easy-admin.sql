/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : laker

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 16/12/2021 15:25:28
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
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ext_leave
-- ----------------------------
INSERT INTO `ext_leave` VALUES (14, 3, '正常', 16, 16, 14, '2021-08-22 23:59:08', '4aacacb31f964104b50b8cc814d807ac');
INSERT INTO `ext_leave` VALUES (15, 5, '测绘', 16, 16, 14, '2021-08-22 23:59:18', '2a3773645b214ea0b8fc48d6784e52e9');
INSERT INTO `ext_leave` VALUES (16, 234234, '234234', 16, 16, 14, '2021-08-23 00:18:39', '7f0b9b62c76d44258d62eab833e7fa69');
INSERT INTO `ext_leave` VALUES (17, 23, '4', 16, 16, 14, '2021-08-26 23:44:38', 'cc3e93c80eed4227af20f07325937451');
INSERT INTO `ext_leave` VALUES (18, 34, '32', 16, 16, 14, '2021-08-26 23:50:38', '9cf10ed2054646708b2f38a0f3239f40');
INSERT INTO `ext_leave` VALUES (19, 214, '234', 1, 1, 14, '2021-10-14 22:51:58', '7bf249c813054c2fa768b2ccdae76a9c');

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
) ENGINE = InnoDB AUTO_INCREMENT = 8929 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ext_log
-- ----------------------------
INSERT INTO `ext_log` VALUES (8501, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"b826c2ab-e5e1-4d2b-81eb-8a8e12bb4862\",\"success\":true}', 1, 36, '2021-10-20 21:02:06');
INSERT INTO `ext_log` VALUES (8502, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 57, '2021-10-20 21:02:06');
INSERT INTO `ext_log` VALUES (8503, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":3}],\"requestId\":\"4224a985-a6f6-40ee-836e-f195215f8546\",\"success\":true,\"count\":10}', 1, 36, '2021-10-20 21:02:08');
INSERT INTO `ext_log` VALUES (8504, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":4}],\"requestId\":\"1dc0e7a1-5dd1-4d4c-9915-f5d6a2f3a84c\",\"success\":true}', 1, 19, '2021-10-20 21:02:08');
INSERT INTO `ext_log` VALUES (8505, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 66, '2021-10-20 21:02:12');
INSERT INTO `ext_log` VALUES (8506, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 7, '2021-10-20 21:02:19');
INSERT INTO `ext_log` VALUES (8507, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"550dd9a5-2feb-46f4-acca-0564bf8f53d3\",\"success\":true}', 1, 35, '2021-10-20 21:06:32');
INSERT INTO `ext_log` VALUES (8508, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 24, '2021-10-20 21:06:32');
INSERT INTO `ext_log` VALUES (8509, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 21, '2021-10-20 21:06:33');
INSERT INTO `ext_log` VALUES (8510, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":10}],\"requestId\":\"11b79a28-f26f-4a47-9d2f-f25a79f8be15\",\"success\":true,\"count\":10}', 1, 13, '2021-10-20 21:06:34');
INSERT INTO `ext_log` VALUES (8511, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":11}],\"requestId\":\"c0cddc6f-a323-461a-be89-99048e99cf7b\",\"success\":true}', 1, 12, '2021-10-20 21:06:34');
INSERT INTO `ext_log` VALUES (8512, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/287', 'SysMenuController.get(..)', '[287]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":287,\"pid\":1,\"title\":\"定时任务\",\"icon\":\"layui-icon \",\"href\":\"view/system/task.html\",\"openType\":\"_iframe\",\"sort\":7,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":null,\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"99e6ef01-1016-4eec-854e-0138f618257f\",\"success\":true}', 1, 12, '2021-10-20 21:06:41');
INSERT INTO `ext_log` VALUES (8513, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 10, '2021-10-20 21:06:41');
INSERT INTO `ext_log` VALUES (8514, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"088986f5-b055-49e7-b630-7dde4c944e26\",\"success\":true}', 1, 9, '2021-10-20 21:09:50');
INSERT INTO `ext_log` VALUES (8515, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 6, '2021-10-20 21:09:50');
INSERT INTO `ext_log` VALUES (8516, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 7, '2021-10-20 21:09:53');
INSERT INTO `ext_log` VALUES (8517, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":17}],\"requestId\":\"786d0a4d-2a0b-42e9-8d80-8f46dceef59c\",\"success\":true,\"count\":10}', 1, 6, '2021-10-20 21:09:53');
INSERT INTO `ext_log` VALUES (8518, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":18}],\"requestId\":\"c331e8da-a643-43d3-ac41-99b4ba703438\",\"success\":true}', 1, 6, '2021-10-20 21:09:53');
INSERT INTO `ext_log` VALUES (8519, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"21725568-6065-47a9-94ef-b515642104e1\",\"success\":true}', 1, 26, '2021-10-20 21:10:50');
INSERT INTO `ext_log` VALUES (8520, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 24, '2021-10-20 21:10:50');
INSERT INTO `ext_log` VALUES (8521, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 6, '2021-10-20 21:10:51');
INSERT INTO `ext_log` VALUES (8522, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":22}],\"requestId\":\"3f865703-2a57-4820-a3fb-27f9ad7ecc04\",\"success\":true,\"count\":10}', 1, 7, '2021-10-20 21:10:51');
INSERT INTO `ext_log` VALUES (8523, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":23}],\"requestId\":\"85de01df-8061-4330-a8c2-f034ceccd3b0\",\"success\":true}', 1, 9, '2021-10-20 21:10:51');

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
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (5, '测试部', '', 0, 0, 1);
INSERT INTO `sys_dept` VALUES (6, '研发一部-1', '今天天气如何', 4, 1, 2);
INSERT INTO `sys_dept` VALUES (7, '运维部', '', 0, 0, 3);
INSERT INTO `sys_dept` VALUES (8, '1', '1', 6, 1, 1);
INSERT INTO `sys_dept` VALUES (9, '', '', 0, 0, NULL);
INSERT INTO `sys_dept` VALUES (10, '2', '2', 8, 1, 2);
INSERT INTO `sys_dept` VALUES (11, '3', '3', 10, 1, 3);
INSERT INTO `sys_dept` VALUES (12, '', '', 0, 0, NULL);
INSERT INTO `sys_dept` VALUES (13, '', '', 0, 0, NULL);
INSERT INTO `sys_dept` VALUES (14, '业务部', '乱吹的业务部', 0, 1, 2);
INSERT INTO `sys_dept` VALUES (15, '研发部', '闷头猛干', 0, 1, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '大法士大夫' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (20, '111111', '12', '1', NULL, 1, NULL);

-- ----------------------------
-- Table structure for sys_power
-- ----------------------------
DROP TABLE IF EXISTS `sys_power`;
CREATE TABLE `sys_power`  (
  `menu_id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父ID',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单图标',
  `href` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '链接',
  `open_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '链接打开方式',
  `sort` int(11) DEFAULT 0 COMMENT '菜单排序',
  `enable` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态(0:禁用,1:启用)',
  `remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `type` int(11) DEFAULT NULL COMMENT '权限类型1目录2菜单3接口4数据',
  `power_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限标识，数据权限例ExtLeaveMapper.selectPage',
  `data_filter_type` int(1) DEFAULT NULL COMMENT '数据权限过滤类型，ALL,DEPT,SELF',
  PRIMARY KEY (`menu_id`) USING BTREE,
  INDEX `title`(`title`) USING BTREE,
  INDEX `href`(`href`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 299 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单权限资源表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_power
-- ----------------------------
INSERT INTO `sys_power` VALUES (1, 0, '工作空间', 'layui-icon layui-icon-cellphone', '', '', 0, 1, NULL, NULL, 0, '', 1);
INSERT INTO `sys_power` VALUES (251, 0, '系统管理', 'layui-icon layui-icon-set-fill', '', '', 0, 1, NULL, NULL, 0, NULL, 1);
INSERT INTO `sys_power` VALUES (252, 251, '权限管理', 'layui-icon layui-icon layui-icon layui-icon-face-cry', 'view/system/power.html', '_iframe', 2, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (258, 251, '用户管理', 'layui-icon layui-icon-user', 'view/system/user.html', '_iframe', 1, 1, NULL, NULL, 1, 'user', 1);
INSERT INTO `sys_power` VALUES (261, 260, '多选下拉', 'layui-icon ', 'view/document/select.html', '_iframe', 2, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (263, 262, '数据表格', 'layui-icon ', 'view/document/table.html', '_iframe', 2, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (264, 251, '流程定义', 'layui-icon layui-icon layui-icon-tree', 'view/flow/processList.html', '_iframe', 6, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (265, 251, '流程实例', 'layui-icon ', 'view/flow/orderList.html', '', 9, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (266, 251, '应用监控', 'layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon-auz', 'http://localhost:8080/monitoring', '_iframe', 10, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (268, 251, '接口文档', 'layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon ', '/doc.html', '_iframe', 5, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (269, 1, '任务列表', 'layui-icon ', 'view/flow/taskList.html', '_iframe', 1, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (270, 1, '请假申请', 'layui-icon layui-icon ', 'view/ext/leave.html', '_iframe', 4, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (280, 251, '角色管理', 'layui-icon layui-icon ', 'view/system/role.html', '_iframe', 3, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (281, 251, '部门管理', 'layui-icon layui-icon ', 'view/system/deptment.html', '_iframe', 4, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (282, 1, '配套专栏', 'layui-icon layui-icon layui-icon layui-icon ', 'https://blog.csdn.net/abu935009066/category_10817814.html', '_iframe', 3, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (283, 251, 'WebLog', 'layui-icon ', 'view/system/weblog.html', '_iframe', 12, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (284, 251, '字典管理', 'layui-icon layui-icon ', 'view/system/dict.html', '_iframe', 13, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (285, 1, '日志查看', 'layui-icon layui-icon layui-icon ', 'view/ext/log.html', '_iframe', 6, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (286, 258, '查询', 'layui-icon ', '', '', 1, 0, NULL, NULL, 2, 'user-seach', 1);
INSERT INTO `sys_power` VALUES (287, 1, '定时任务', 'layui-icon ', 'view/system/task.html', '_iframe', 7, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (288, 285, '日志查看', 'layui-icon ', '', '', 1, 1, NULL, NULL, 2, 'log.list', 1);
INSERT INTO `sys_power` VALUES (289, 0, '源码下载', 'layui-icon layui-icon-praise', '', '', 1, 1, NULL, NULL, 0, '', 1);
INSERT INTO `sys_power` VALUES (290, 289, '码云地址', 'layui-icon layui-icon ', 'https://gitee.com/lakernote/easy-admin', '_blank', 1, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (291, 1, '在线用户', 'layui-icon ', 'view/system/onlineUser.html', '_iframe', 7, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (292, 1, 'NginxUI', 'layui-icon ', 'view/system/nginx.html', '_iframe', 1, 1, NULL, NULL, 1, '', 1);
INSERT INTO `sys_power` VALUES (293, 283, '查看', 'layui-icon ', '', '', 1, 1, NULL, NULL, 2, 'weblog.list', 1);
INSERT INTO `sys_power` VALUES (294, 270, '列表个人', 'layui-icon ', '', '', 1, 1, NULL, NULL, 3, 'ExtLeaveMapper.selectPage', 3);
INSERT INTO `sys_power` VALUES (295, 270, '列表全部', 'layui-icon ', '', '', 2, 1, NULL, NULL, 3, 'ExtLeaveMapper.selectPage', 1);
INSERT INTO `sys_power` VALUES (296, 270, '列表部门', 'layui-icon ', '', '', 2, 1, NULL, NULL, 3, 'ExtLeaveMapper.selectPage', 2);
INSERT INTO `sys_power` VALUES (298, 269, '修改密码', 'layui-icon layui-icon ', '', '', 2, 1, NULL, NULL, 2, 'user.update.pwd', 1);

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
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (5, '管理员', 'admin', '', 1, NULL, 1);
INSERT INTO `sys_role` VALUES (6, '领导', 'leader', '', 1, NULL, 1);
INSERT INTO `sys_role` VALUES (10, '数据角色领导', '', '', 1, NULL, 2);
INSERT INTO `sys_role` VALUES (11, '数据角色员工', '', '', 1, NULL, 2);

-- ----------------------------
-- Table structure for sys_role_power
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_power`;
CREATE TABLE `sys_role_power`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `power_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 391 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_power
-- ----------------------------
INSERT INTO `sys_role_power` VALUES (217, 6, 1);
INSERT INTO `sys_role_power` VALUES (218, 6, 269);
INSERT INTO `sys_role_power` VALUES (219, 6, 282);
INSERT INTO `sys_role_power` VALUES (220, 6, 289);
INSERT INTO `sys_role_power` VALUES (221, 6, 290);
INSERT INTO `sys_role_power` VALUES (249, 9, 294);
INSERT INTO `sys_role_power` VALUES (250, 11, 294);
INSERT INTO `sys_role_power` VALUES (251, 10, 296);
INSERT INTO `sys_role_power` VALUES (367, 5, 1);
INSERT INTO `sys_role_power` VALUES (368, 5, 269);
INSERT INTO `sys_role_power` VALUES (369, 5, 298);
INSERT INTO `sys_role_power` VALUES (370, 5, 270);
INSERT INTO `sys_role_power` VALUES (371, 5, 282);
INSERT INTO `sys_role_power` VALUES (372, 5, 285);
INSERT INTO `sys_role_power` VALUES (373, 5, 288);
INSERT INTO `sys_role_power` VALUES (374, 5, 287);
INSERT INTO `sys_role_power` VALUES (375, 5, 291);
INSERT INTO `sys_role_power` VALUES (376, 5, 251);
INSERT INTO `sys_role_power` VALUES (377, 5, 252);
INSERT INTO `sys_role_power` VALUES (378, 5, 258);
INSERT INTO `sys_role_power` VALUES (379, 5, 286);
INSERT INTO `sys_role_power` VALUES (380, 5, 264);
INSERT INTO `sys_role_power` VALUES (381, 5, 265);
INSERT INTO `sys_role_power` VALUES (382, 5, 266);
INSERT INTO `sys_role_power` VALUES (383, 5, 268);
INSERT INTO `sys_role_power` VALUES (384, 5, 280);
INSERT INTO `sys_role_power` VALUES (385, 5, 281);
INSERT INTO `sys_role_power` VALUES (386, 5, 283);
INSERT INTO `sys_role_power` VALUES (387, 5, 293);
INSERT INTO `sys_role_power` VALUES (388, 5, 284);
INSERT INTO `sys_role_power` VALUES (389, 5, 289);
INSERT INTO `sys_role_power` VALUES (390, 5, 290);

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
-- Records of sys_tasklog
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '30a9c7081b257f80a3f672452decc16dafe717cc54ac510afb77257fb6ee3702', '超级管理员', 14, 1, '11111111111', 1, '935009@98.com', '2021-08-15 11:02:15');
INSERT INTO `sys_user` VALUES (16, 'laker', '4b15d2b3b671209e01202331881af5a6044d342dc624d29a53ed6b4402af6d61', '李哥', 14, 1, '1', 1, '', '2021-08-09 18:25:32');
INSERT INTO `sys_user` VALUES (17, 'yang', '4b15d2b3b671209e01202331881af5a6044d342dc624d29a53ed6b4402af6d61', '陈总', 14, 1, '', 1, '', '2021-08-10 10:24:23');
INSERT INTO `sys_user` VALUES (18, 'zhang', '4b15d2b3b671209e01202331881af5a6044d342dc624d29a53ed6b4402af6d61', '张总', 15, 2, '', 1, '', '2021-08-10 10:24:38');
INSERT INTO `sys_user` VALUES (24, 'test', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '测试用户', 15, 1, '18256079743', 1, '98@qq.com', '2021-10-21 10:07:07');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (7, NULL, 2);
INSERT INTO `sys_user_role` VALUES (16, 19, 3);
INSERT INTO `sys_user_role` VALUES (17, NULL, 2);
INSERT INTO `sys_user_role` VALUES (19, NULL, 2);
INSERT INTO `sys_user_role` VALUES (24, 22, 2);
INSERT INTO `sys_user_role` VALUES (25, 22, 3);
INSERT INTO `sys_user_role` VALUES (28, 23, 2);
INSERT INTO `sys_user_role` VALUES (58, 1, 5);
INSERT INTO `sys_user_role` VALUES (61, 18, 6);
INSERT INTO `sys_user_role` VALUES (62, 18, 7);
INSERT INTO `sys_user_role` VALUES (71, 17, 6);
INSERT INTO `sys_user_role` VALUES (72, 17, 10);
INSERT INTO `sys_user_role` VALUES (77, 16, 5);
INSERT INTO `sys_user_role` VALUES (78, 16, 10);
INSERT INTO `sys_user_role` VALUES (80, 24, 6);

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
INSERT INTO `wf_hist_order` VALUES ('2a3773645b214ea0b8fc48d6784e52e9', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, '16', '2021-08-22 23:59:17', NULL, NULL, NULL, NULL, '老李-2021-08-22 23:59:17的请假申请', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-22 23:59:17的请假申请\",\"day\":5,\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('4aacacb31f964104b50b8cc814d807ac', '6d8fa9bd5833446ab2c7c8c799d55d30', 0, '16', '2021-08-22 23:59:08', '2021-08-23 00:00:24', NULL, NULL, NULL, '老李-2021-08-22 23:59:07的请假申请', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-22 23:59:07的请假申请\",\"day\":3,\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('7bf249c813054c2fa768b2ccdae76a9c', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, '1', '2021-10-14 22:51:57', NULL, NULL, NULL, NULL, '超级管理员-2021-10-14 22:51:57的请假申请', '{\"user1\":\"1\",\"user2\":\"17\",\"snaker.orderNo\":\"超级管理员-2021-10-14 22:51:57的请假申请\",\"day\":214,\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('7f0b9b62c76d44258d62eab833e7fa69', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, '16', '2021-08-23 00:18:39', NULL, NULL, NULL, NULL, '老李-2021-08-23 00:18:39的请假申请', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-23 00:18:39的请假申请\",\"day\":234234,\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('9b257877ada14ef09cb80527912f33e8', '5aa44819897241ee9c7618f620f5a98d', 0, '', '2021-09-06 15:19:19', '2021-09-06 15:19:19', NULL, NULL, NULL, '20210906-15:19:19-641-254', '{\"name\":\"laker\"}');
INSERT INTO `wf_hist_order` VALUES ('9cf10ed2054646708b2f38a0f3239f40', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, '16', '2021-08-26 23:50:38', NULL, NULL, NULL, NULL, '老李-2021-08-26 23:50:38的请假申请', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-26 23:50:38的请假申请\",\"day\":34,\"user3\":\"18\"}');
INSERT INTO `wf_hist_order` VALUES ('cc3e93c80eed4227af20f07325937451', '6d8fa9bd5833446ab2c7c8c799d55d30', 1, '16', '2021-08-26 23:44:38', NULL, NULL, NULL, NULL, '老李-2021-08-26 23:44:38的请假申请', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-26 23:44:38的请假申请\",\"day\":23,\"user3\":\"18\"}');

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
INSERT INTO `wf_hist_task` VALUES ('039022baeb2147f6abde941cc0de3976', '2a3773645b214ea0b8fc48d6784e52e9', 'apply', '请假申请', 0, 0, 0, '16', '2021-08-23 00:17:38', '2021-08-23 00:17:46', NULL, '', '39b69df8a57a45a69c89b715712132c4', '{}');
INSERT INTO `wf_hist_task` VALUES ('0ae77bfcb09c4472902f071173fbb5a9', '2a3773645b214ea0b8fc48d6784e52e9', 'approveDept', '部门经理审批', 0, 0, 0, '17', '2021-08-22 23:59:18', '2021-08-22 23:59:39', NULL, '', 'bfc6bf3135b341469c7b2694952b256a', '{}');
INSERT INTO `wf_hist_task` VALUES ('168f2a2f0442419faf38f13043834d27', '4aacacb31f964104b50b8cc814d807ac', 'apply', '请假申请', 0, 0, 0, '16', '2021-08-22 23:59:08', '2021-08-22 23:59:08', NULL, '', 'start', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-22 23:59:07的请假申请\",\"S-ACTOR\":\"16\",\"day\":3,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('29ff16f12f3942a4adfa1ccdb0c33302', 'cc3e93c80eed4227af20f07325937451', 'apply', '请假申请', 0, 0, 0, '16', '2021-08-26 23:44:38', '2021-08-26 23:44:38', NULL, '', 'start', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-26 23:44:38的请假申请\",\"S-ACTOR\":\"16\",\"day\":23,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('2b51086e44bc4aa99b1de2294708a8a3', '4aacacb31f964104b50b8cc814d807ac', 'approveBoss', '总经理审批', 0, 0, 0, '18', '2021-08-22 23:59:39', '2021-08-23 00:00:24', NULL, '', 'a0e1be72e3314c0597b62498b52e66f8', '{}');
INSERT INTO `wf_hist_task` VALUES ('30002727f4c14f81a6cf0d9b4795933f', '7bf249c813054c2fa768b2ccdae76a9c', 'apply', '请假申请', 0, 0, 0, '1', '2021-10-14 22:51:57', '2021-10-14 22:51:58', NULL, '', 'start', '{\"user1\":\"1\",\"user2\":\"17\",\"snaker.orderNo\":\"超级管理员-2021-10-14 22:51:57的请假申请\",\"S-ACTOR\":\"1\",\"day\":214,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('35243861537146b787633824334edc28', '9cf10ed2054646708b2f38a0f3239f40', 'apply', '请假申请', 0, 0, 0, '16', '2021-08-26 23:50:38', '2021-08-26 23:50:38', NULL, '', 'start', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-26 23:50:38的请假申请\",\"S-ACTOR\":\"16\",\"day\":34,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('39b69df8a57a45a69c89b715712132c4', '2a3773645b214ea0b8fc48d6784e52e9', 'approveBoss', '总经理审批', 0, 0, 0, '18', '2021-08-22 23:59:39', '2021-08-23 00:07:37', NULL, '', '0ae77bfcb09c4472902f071173fbb5a9', '{}');
INSERT INTO `wf_hist_task` VALUES ('3ef19ef8edcc4724bdf063675ebe447c', '2a3773645b214ea0b8fc48d6784e52e9', 'approveDept', '部门经理审批', 0, 0, 0, '17', '2021-08-23 00:17:46', '2021-08-23 00:18:15', NULL, '', '039022baeb2147f6abde941cc0de3976', '{}');
INSERT INTO `wf_hist_task` VALUES ('557fe529573f4b48b1bc0fe54d79c999', '7f0b9b62c76d44258d62eab833e7fa69', 'apply', '请假申请', 0, 0, 0, '16', '2021-08-23 00:18:39', '2021-08-23 00:18:39', NULL, '', 'start', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-23 00:18:39的请假申请\",\"S-ACTOR\":\"16\",\"day\":234234,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('5f4c2db6f7a84bf3ac72d6c6d57dfbc1', '9b257877ada14ef09cb80527912f33e8', 'rect4', 'rect4', 2, NULL, 0, NULL, '2021-09-06 15:19:19', '2021-09-06 15:19:19', NULL, NULL, 'start', '{\"name\":\"laker\"}');
INSERT INTO `wf_hist_task` VALUES ('6631c7a664514642b0036a8ab41a4f5d', '2a3773645b214ea0b8fc48d6784e52e9', 'apply', '请假申请', 0, 0, 0, '16', '2021-08-23 00:07:37', '2021-08-23 00:08:00', NULL, '', '39b69df8a57a45a69c89b715712132c4', '{}');
INSERT INTO `wf_hist_task` VALUES ('9566df8655f44676bd840f5a3536a267', '7f0b9b62c76d44258d62eab833e7fa69', 'apply', '请假申请', 0, 0, 0, '16', '2021-08-23 00:18:44', '2021-09-01 23:32:21', NULL, '', 'start', '{}');
INSERT INTO `wf_hist_task` VALUES ('a0e1be72e3314c0597b62498b52e66f8', '4aacacb31f964104b50b8cc814d807ac', 'approveDept', '部门经理审批', 0, 0, 0, '17', '2021-08-22 23:59:08', '2021-08-22 23:59:39', NULL, '', '168f2a2f0442419faf38f13043834d27', '{}');
INSERT INTO `wf_hist_task` VALUES ('a5f243b52c304f4fac3b42ec537b87ec', '9b257877ada14ef09cb80527912f33e8', 'rect2', 'rect2', 2, NULL, 0, NULL, '2021-09-06 15:19:19', '2021-09-06 15:19:19', NULL, NULL, 'start', '{\"name\":\"laker\"}');
INSERT INTO `wf_hist_task` VALUES ('bfc6bf3135b341469c7b2694952b256a', '2a3773645b214ea0b8fc48d6784e52e9', 'apply', '请假申请', 0, 0, 0, '16', '2021-08-22 23:59:17', '2021-08-22 23:59:18', NULL, '', 'start', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-22 23:59:17的请假申请\",\"S-ACTOR\":\"16\",\"day\":5,\"user3\":\"18\"}');
INSERT INTO `wf_hist_task` VALUES ('d8a6ebad8d3c45dcba9170939345d9c5', 'cc3e93c80eed4227af20f07325937451', 'apply', '请假申请', 0, 0, 0, '16', '2021-09-01 23:21:52', '2021-09-01 23:32:22', NULL, '', 'start', '{}');

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
INSERT INTO `wf_hist_task_actor` VALUES ('168f2a2f0442419faf38f13043834d27', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('bfc6bf3135b341469c7b2694952b256a', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('0ae77bfcb09c4472902f071173fbb5a9', '17');
INSERT INTO `wf_hist_task_actor` VALUES ('a0e1be72e3314c0597b62498b52e66f8', '17');
INSERT INTO `wf_hist_task_actor` VALUES ('2b51086e44bc4aa99b1de2294708a8a3', '18');
INSERT INTO `wf_hist_task_actor` VALUES ('39b69df8a57a45a69c89b715712132c4', '18');
INSERT INTO `wf_hist_task_actor` VALUES ('6631c7a664514642b0036a8ab41a4f5d', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('039022baeb2147f6abde941cc0de3976', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('3ef19ef8edcc4724bdf063675ebe447c', '17');
INSERT INTO `wf_hist_task_actor` VALUES ('557fe529573f4b48b1bc0fe54d79c999', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('29ff16f12f3942a4adfa1ccdb0c33302', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('35243861537146b787633824334edc28', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('9566df8655f44676bd840f5a3536a267', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('d8a6ebad8d3c45dcba9170939345d9c5', '16');
INSERT INTO `wf_hist_task_actor` VALUES ('30002727f4c14f81a6cf0d9b4795933f', '1');

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
INSERT INTO `wf_order` VALUES ('2a3773645b214ea0b8fc48d6784e52e9', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', '16', '2021-08-22 23:59:17', NULL, '2021-08-23 00:18:15', '17', NULL, NULL, '老李-2021-08-22 23:59:17的请假申请', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-22 23:59:17的请假申请\",\"day\":5,\"user3\":\"18\"}', 6);
INSERT INTO `wf_order` VALUES ('7bf249c813054c2fa768b2ccdae76a9c', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', '1', '2021-10-14 22:51:57', NULL, '2021-10-14 22:51:58', '1', NULL, NULL, '超级管理员-2021-10-14 22:51:57的请假申请', '{\"user1\":\"1\",\"user2\":\"17\",\"snaker.orderNo\":\"超级管理员-2021-10-14 22:51:57的请假申请\",\"day\":214,\"user3\":\"18\"}', 1);
INSERT INTO `wf_order` VALUES ('7f0b9b62c76d44258d62eab833e7fa69', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', '16', '2021-08-23 00:18:39', NULL, '2021-09-01 23:32:21', '16', NULL, NULL, '老李-2021-08-23 00:18:39的请假申请', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-23 00:18:39的请假申请\",\"day\":234234,\"user3\":\"18\"}', 2);
INSERT INTO `wf_order` VALUES ('9cf10ed2054646708b2f38a0f3239f40', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', '16', '2021-08-26 23:50:38', NULL, '2021-08-26 23:50:38', '16', NULL, NULL, '老李-2021-08-26 23:50:38的请假申请', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-26 23:50:38的请假申请\",\"day\":34,\"user3\":\"18\"}', 1);
INSERT INTO `wf_order` VALUES ('cc3e93c80eed4227af20f07325937451', NULL, '6d8fa9bd5833446ab2c7c8c799d55d30', '16', '2021-08-26 23:44:38', NULL, '2021-09-01 23:32:22', '16', NULL, NULL, '老李-2021-08-26 23:44:38的请假申请', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-26 23:44:38的请假申请\",\"day\":23,\"user3\":\"18\"}', 2);

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
INSERT INTO `wf_process` VALUES ('5aa44819897241ee9c7618f620f5a98d', '自定义', 'diy', NULL, '', 0, 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D226E6F223F3E0A3C70726F63657373206E616D653D22E887AAE5AE9AE4B9892220646973706C61794E616D653D2264697922203E0A3C7374617274206C61796F75743D2236382C3133302C35302C353022206E616D653D22737461727422203E0A3C7472616E736974696F6E206F66667365743D22302C2D31302220746F3D22726563743222206E616D653D22706174683322202F3E0A3C2F73746172743E0A3C637573746F6D206C61796F75743D223232302C3133312C3130302C353022206E616D653D2272656374322220646973706C61794E616D653D22E887AAE5AE9AE4B989E5A484E79086E599A82220636C617A7A3D22636F6D2E6C616B65722E61646D696E2E6672616D65776F726B2E6578742E666C6F772E45617379437573746F6D48616E646C657222203E0A3C7472616E736974696F6E206F66667365743D22302C2D31302220746F3D22726563743422206E616D653D22706174683922202F3E0A3C2F637573746F6D3E0A3C637573746F6D206C61796F75743D223433362C3133312C3130302C353022206E616D653D2272656374342220646973706C61794E616D653D22E887AAE5AE9AE4B989E5A484E79086E599A82220636C617A7A3D22636F6D2E6C616B65722E61646D696E2E6672616D65776F726B2E6578742E666C6F772E45617379437573746F6D48616E646C657222203E0A3C7472616E736974696F6E206F66667365743D22302C2D31302220746F3D22656E6422206E616D653D22706174683722202F3E0A3C2F637573746F6D3E0A3C656E64206C61796F75743D223632302C3133302C35302C353022206E616D653D22656E6422203E0A3C2F656E643E0A3C2F70726F636573733E, 0, '2021-09-06 14:57:45', NULL);
INSERT INTO `wf_process` VALUES ('6d8fa9bd5833446ab2c7c8c799d55d30', 'leave', '请假流程', NULL, '', 1, 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D226E6F223F3E0A3C70726F63657373206E616D653D226C656176652220646973706C61794E616D653D22E8AFB7E58187E6B581E7A88B22203E0A3C7374617274206C61796F75743D2232342C3132342C35302C353022206E616D653D227374617274312220646973706C61794E616D653D2273746172743122203E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D226170706C7922206E616D653D227472616E736974696F6E3122202F3E0A3C2F73746172743E0A3C656E64206C61796F75743D223537302C3132342C35302C353022206E616D653D22656E64312220646973706C61794E616D653D22656E643122203E0A3C2F656E643E0A3C7461736B206C61796F75743D223131372C3132322C3130302C353022206E616D653D226170706C792220646973706C61794E616D653D22E8AFB7E58187E794B3E8AFB7222061737369676E65653D227573657231222061737369676E6565446973706C61793D223122207461736B547970653D224D616A6F722220706572666F726D547970653D22414E5922203E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D22617070726F76654465707422206E616D653D227472616E736974696F6E3222202F3E0A3C2F7461736B3E0A3C7461736B206C61796F75743D223237322C3132322C3130302C353022206E616D653D22617070726F7665446570742220646973706C61794E616D653D22E983A8E997A8E7BB8FE79086E5AEA1E689B9222061737369676E65653D22757365723222207461736B547970653D224D616A6F722220706572666F726D547970653D22414E5922203E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D226465636973696F6E3122206E616D653D227472616E736974696F6E3322202F3E0A3C2F7461736B3E0A3C6465636973696F6E206C61796F75743D223432362C3132342C35302C353022206E616D653D226465636973696F6E312220657870723D2223646179202667743B2032203F20277472616E736974696F6E3527203A20277472616E736974696F6E34272220646973706C61794E616D653D226465636973696F6E3122203E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D22656E643122206E616D653D227472616E736974696F6E342220646973706C61794E616D653D22266C743B3D32E5A4A922202F3E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D22617070726F7665426F737322206E616D653D227472616E736974696F6E352220646973706C61794E616D653D222667743B32E5A4A922202F3E0A3C2F6465636973696F6E3E0A3C7461736B206C61796F75743D223430342C3233312C3130302C353022206E616D653D22617070726F7665426F73732220646973706C61794E616D653D22E680BBE7BB8FE79086E5AEA1E689B9222061737369676E65653D22757365723322207461736B547970653D224D616A6F722220706572666F726D547970653D22414E5922203E0A3C7472616E736974696F6E206F66667365743D22302C302220746F3D22656E643122206E616D653D227472616E736974696F6E3622202F3E0A3C2F7461736B3E0A3C2F70726F636573733E, 2, '2021-08-11 19:06:37', NULL);
INSERT INTO `wf_process` VALUES ('a7a5d0b6a4e542ce99308bebeedb1452', 'test', '测试单点流程', NULL, '', 0, 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D226E6F223F3E0A3C70726F63657373206E616D653D22746573742220646973706C61794E616D653D22E6B58BE8AF95E58D95E782B9E6B581E7A88B22203E0A3C7461736B206C61796F75743D223235332C3139382C3130302C353022206E616D653D223636362220646973706C61794E616D653D227265637433222061737369676E65653D223122207461736B547970653D224D616A6F722220706572666F726D547970653D22414E5922203E0A3C7472616E736974696F6E206F66667365743D22302C2D31302220746F3D22656E6422206E616D653D22706174683522202F3E0A3C2F7461736B3E0A3C7374617274206C61796F75743D223131302C3139362C35302C353022206E616D653D22737461727422203E0A3C7472616E736974696F6E206F66667365743D22302C2D31302220746F3D2236363622206E616D653D22706174683422202F3E0A3C2F73746172743E0A3C656E64206C61796F75743D223437312C3139392C35302C353022206E616D653D22656E6422203E0A3C2F656E643E0A3C2F70726F636573733E, 0, '2021-08-18 16:42:25', NULL);

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
INSERT INTO `wf_task` VALUES ('366fb54bf32047b3b3296764863a2b71', 'cc3e93c80eed4227af20f07325937451', 'approveDept', '部门经理审批', 0, 0, NULL, '2021-09-01 23:32:22', NULL, NULL, '', 'd8a6ebad8d3c45dcba9170939345d9c5', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-26 23:44:38的请假申请\",\"S-ACTOR\":\"17\",\"day\":23,\"user3\":\"18\"}', 0);
INSERT INTO `wf_task` VALUES ('512d9e14b8004f009a693bb10ff99705', '7bf249c813054c2fa768b2ccdae76a9c', 'approveDept', '部门经理审批', 0, 0, NULL, '2021-10-14 22:51:58', NULL, NULL, '', '30002727f4c14f81a6cf0d9b4795933f', '{\"user1\":\"1\",\"user2\":\"17\",\"snaker.orderNo\":\"超级管理员-2021-10-14 22:51:57的请假申请\",\"S-ACTOR\":\"17\",\"day\":214,\"user3\":\"18\"}', 0);
INSERT INTO `wf_task` VALUES ('72ca6873d466470486986d8202d5d4bd', '7f0b9b62c76d44258d62eab833e7fa69', 'approveDept', '部门经理审批', 0, 0, NULL, '2021-09-01 23:32:21', NULL, NULL, '', '9566df8655f44676bd840f5a3536a267', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-23 00:18:39的请假申请\",\"S-ACTOR\":\"17\",\"day\":234234,\"user3\":\"18\"}', 0);
INSERT INTO `wf_task` VALUES ('dcf1d671aa2e41288aa77bdcb820aeb7', '9cf10ed2054646708b2f38a0f3239f40', 'approveDept', '部门经理审批', 0, 0, NULL, '2021-08-26 23:50:38', NULL, NULL, '', '35243861537146b787633824334edc28', '{\"user1\":\"16\",\"user2\":\"17\",\"snaker.orderNo\":\"老李-2021-08-26 23:50:38的请假申请\",\"S-ACTOR\":\"17\",\"day\":34,\"user3\":\"18\"}', 0);
INSERT INTO `wf_task` VALUES ('e619ab25c38d42d49e41a02886b1ea6a', '2a3773645b214ea0b8fc48d6784e52e9', 'approveDept', '部门经理审批', 0, 0, '17', '2021-08-23 00:19:54', NULL, NULL, '', '039022baeb2147f6abde941cc0de3976', '{}', 0);

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
INSERT INTO `wf_task_actor` VALUES ('e619ab25c38d42d49e41a02886b1ea6a', '17');
INSERT INTO `wf_task_actor` VALUES ('dcf1d671aa2e41288aa77bdcb820aeb7', '17');
INSERT INTO `wf_task_actor` VALUES ('72ca6873d466470486986d8202d5d4bd', '17');
INSERT INTO `wf_task_actor` VALUES ('366fb54bf32047b3b3296764863a2b71', '17');
INSERT INTO `wf_task_actor` VALUES ('512d9e14b8004f009a693bb10ff99705', '17');

SET FOREIGN_KEY_CHECKS = 1;
