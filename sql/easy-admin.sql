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

 Date: 21/10/2021 11:44:51
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
INSERT INTO `ext_log` VALUES (8500, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"admin\",\"captchaCode\":\"3\",\"uid\":\"36469a8ebc5f4868885078457b2bb661\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"41433f8d1df44c5f996d0213dc8d2cd5\",\"isLogin\":true,\"loginId\":\"1\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"070727ae-dca8-424b-941a-3b2c4f6a0a53\",\"success\":true}', 1, 258, '2021-10-20 21:02:03');
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
INSERT INTO `ext_log` VALUES (8524, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"e6aa3b16-c921-44f3-8b23-28df112d9e37\",\"success\":true}', 1, 7, '2021-10-20 21:10:59');
INSERT INTO `ext_log` VALUES (8525, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 5, '2021-10-20 21:10:59');
INSERT INTO `ext_log` VALUES (8526, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 9, '2021-10-20 21:10:59');
INSERT INTO `ext_log` VALUES (8527, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":27}],\"requestId\":\"9a6432e8-a88a-44d0-9205-e2acfb7adfd7\",\"success\":true,\"count\":10}', 1, 9, '2021-10-20 21:11:00');
INSERT INTO `ext_log` VALUES (8528, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":28}],\"requestId\":\"88495840-ee33-4867-8907-04e820bf1a42\",\"success\":true}', 1, 11, '2021-10-20 21:11:00');
INSERT INTO `ext_log` VALUES (8529, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 5, '2021-10-20 21:11:46');
INSERT INTO `ext_log` VALUES (8530, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu', 'SysMenuController.saveOrUpdate(..)', '[{\"menuId\":null,\"pid\":1,\"title\":\"CSDN页面\",\"icon\":\"layui-icon \",\"href\":\"https://blog.csdn.net/abu935009066\",\"openType\":\"_iframe\",\"sort\":0,\"enable\":null,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"1d959bad-b176-4feb-be77-0538443d0452\",\"success\":true}', 1, 24, '2021-10-20 21:12:08');
INSERT INTO `ext_log` VALUES (8531, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 24, '2021-10-20 21:12:09');
INSERT INTO `ext_log` VALUES (8532, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 8, '2021-10-20 21:12:17');
INSERT INTO `ext_log` VALUES (8533, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"eaf92d64-823a-4f84-a069-25e66f8ffc20\",\"success\":true}', 1, 16, '2021-10-20 21:13:42');
INSERT INTO `ext_log` VALUES (8534, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 11, '2021-10-20 21:13:42');
INSERT INTO `ext_log` VALUES (8535, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 6, '2021-10-20 21:13:43');
INSERT INTO `ext_log` VALUES (8536, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":36}],\"requestId\":\"be686242-9db7-419f-8d21-bee4c39351fc\",\"success\":true,\"count\":10}', 1, 4, '2021-10-20 21:13:44');
INSERT INTO `ext_log` VALUES (8537, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":37}],\"requestId\":\"60efc7f6-8a56-4605-bc98-fa64f58de7c1\",\"success\":true}', 1, 5, '2021-10-20 21:13:44');
INSERT INTO `ext_log` VALUES (8538, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"ee1e5431-e5f8-46e0-bcdf-1baa5e0fb667\",\"success\":true}', 1, 11, '2021-10-20 21:13:47');
INSERT INTO `ext_log` VALUES (8539, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 9, '2021-10-20 21:13:47');
INSERT INTO `ext_log` VALUES (8540, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 13, '2021-10-20 21:13:48');
INSERT INTO `ext_log` VALUES (8541, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":41}],\"requestId\":\"57e26ca8-2f27-46a3-9c1c-73f1f6db49a0\",\"success\":true,\"count\":10}', 1, 4, '2021-10-20 21:13:49');
INSERT INTO `ext_log` VALUES (8542, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":41}],\"requestId\":\"1333a452-f23b-4b1e-a66d-3f1ceaa08912\",\"success\":true}', 1, 5, '2021-10-20 21:13:49');
INSERT INTO `ext_log` VALUES (8543, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 4, '2021-10-20 21:13:50');
INSERT INTO `ext_log` VALUES (8544, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"b968be39-bd42-4d13-a0ba-38a718311ed0\",\"success\":true}', 1, 9, '2021-10-20 21:13:57');
INSERT INTO `ext_log` VALUES (8545, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 8, '2021-10-20 21:13:57');
INSERT INTO `ext_log` VALUES (8546, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 6, '2021-10-20 21:13:58');
INSERT INTO `ext_log` VALUES (8547, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":47}],\"requestId\":\"96461b67-9eee-4dce-9405-43a645d2686f\",\"success\":true,\"count\":10}', 1, 15, '2021-10-20 21:13:59');
INSERT INTO `ext_log` VALUES (8548, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":48}],\"requestId\":\"12e87699-b02a-4cc4-93f5-ce4bdec60b72\",\"success\":true}', 1, 19, '2021-10-20 21:13:59');
INSERT INTO `ext_log` VALUES (8549, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"0d118b34-9062-408f-9dfa-e6375c13b22e\",\"success\":true}', 1, 13, '2021-10-20 21:14:10');
INSERT INTO `ext_log` VALUES (8550, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 10, '2021-10-20 21:14:10');
INSERT INTO `ext_log` VALUES (8551, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 15, '2021-10-20 21:14:11');
INSERT INTO `ext_log` VALUES (8552, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":52}],\"requestId\":\"49e9be6b-2f8d-4d0c-9199-563f53369cb5\",\"success\":true,\"count\":10}', 1, 11, '2021-10-20 21:14:11');
INSERT INTO `ext_log` VALUES (8553, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":53}],\"requestId\":\"875bd4df-c874-4c4a-b6a0-1358ec19ad9f\",\"success\":true}', 1, 9, '2021-10-20 21:14:11');
INSERT INTO `ext_log` VALUES (8554, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"6f377ae4-ffa6-45bb-aee8-288572c8aa44\",\"success\":true}', 1, 10, '2021-10-20 21:14:26');
INSERT INTO `ext_log` VALUES (8555, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 5, '2021-10-20 21:14:26');
INSERT INTO `ext_log` VALUES (8556, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 18, '2021-10-20 21:14:28');
INSERT INTO `ext_log` VALUES (8557, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":57}],\"requestId\":\"0ed9c5e2-594e-4a7f-b422-d840c6273f24\",\"success\":true,\"count\":10}', 1, 12, '2021-10-20 21:14:29');
INSERT INTO `ext_log` VALUES (8558, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":58}],\"requestId\":\"b7cf3122-36e7-428f-ac13-5378d3f8b9ab\",\"success\":true}', 1, 12, '2021-10-20 21:14:29');
INSERT INTO `ext_log` VALUES (8559, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 24, '2021-10-20 21:16:14');
INSERT INTO `ext_log` VALUES (8560, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/getRolePower?roleId=5', 'SysRoleController.getRolePower(..)', '[5]', NULL, 1, 21, '2021-10-20 21:16:22');
INSERT INTO `ext_log` VALUES (8561, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/5', 'SysRoleController.get(..)', '[5]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"roleId\":5,\"roleName\":\"管理员\",\"roleCode\":\"admin\",\"details\":\"\",\"enable\":true,\"createTime\":null,\"roleType\":1,\"checked\":false},\"requestId\":\"1045818d-58ed-46d3-8de7-b937d999d8ec\",\"success\":true}', 1, 3, '2021-10-20 21:16:25');
INSERT INTO `ext_log` VALUES (8562, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/5', 'SysRoleController.get(..)', '[5]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"roleId\":5,\"roleName\":\"管理员\",\"roleCode\":\"admin\",\"details\":\"\",\"enable\":true,\"createTime\":null,\"roleType\":1,\"checked\":false},\"requestId\":\"573aaf4b-9028-4353-86ff-b8596818984a\",\"success\":true}', 1, 4, '2021-10-20 21:16:33');
INSERT INTO `ext_log` VALUES (8563, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/getRolePower?roleId=5', 'SysRoleController.getRolePower(..)', '[5]', NULL, 1, 16, '2021-10-20 21:16:38');
INSERT INTO `ext_log` VALUES (8564, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/saveRolePower', 'SysRoleController.saveRolePower(..)', '[5,\"1,269,270,282,285,288,287,291,297,251,252,258,286,264,265,266,268,280,281,283,284,289,290\"]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"30993321-8f46-4585-ad11-4edf941700d5\",\"success\":true}', 1, 26, '2021-10-20 21:20:57');
INSERT INTO `ext_log` VALUES (8565, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/data', 'SysDeptController.data(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 15, '2021-10-20 21:21:01');
INSERT INTO `ext_log` VALUES (8566, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 15, '2021-10-20 21:21:06');
INSERT INTO `ext_log` VALUES (8567, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 15, '2021-10-20 21:21:06');
INSERT INTO `ext_log` VALUES (8568, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/16', 'SysUserController.get(..)', '[16]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":null,\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"a55b04be-1e0f-4af8-a18a-e7f9fe1852ad\",\"success\":true}', 1, 9, '2021-10-20 21:21:09');
INSERT INTO `ext_log` VALUES (8569, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/getRoles?userId=16', 'SysUserController.edit(..)', '[16]', NULL, 1, 29, '2021-10-20 21:21:09');
INSERT INTO `ext_log` VALUES (8570, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 8, '2021-10-20 21:21:09');
INSERT INTO `ext_log` VALUES (8571, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"327a4555-b8c2-4a38-91a8-652dbf9d0dc6\",\"success\":true}', 1, 3, '2021-10-20 21:24:56');
INSERT INTO `ext_log` VALUES (8572, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"laker\",\"captchaCode\":\"4\",\"uid\":\"d5ae754c93f14ab7b87d08e833c9be6c\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"1059364ff4b04035b311f18ddc10dfd3\",\"isLogin\":true,\"loginId\":\"16\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"a8391536-99ee-4446-983a-00a91775340d\",\"success\":true}', 1, 48, '2021-10-20 21:25:03');
INSERT INTO `ext_log` VALUES (8573, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"bdf1e095-b685-41e4-a4db-1aa8fb2f276b\",\"success\":true}', 1, 6, '2021-10-20 21:25:05');
INSERT INTO `ext_log` VALUES (8574, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 16, '2021-10-20 21:25:05');
INSERT INTO `ext_log` VALUES (8575, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":75}],\"requestId\":\"ed013d27-4615-4e1f-94fd-f5d6ba40bb7f\",\"success\":true,\"count\":10}', 1, 4, '2021-10-20 21:25:06');
INSERT INTO `ext_log` VALUES (8576, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":76}],\"requestId\":\"96a0d7a7-eba9-4710-87b1-a10fc8df8ee1\",\"success\":true}', 1, 4, '2021-10-20 21:25:06');
INSERT INTO `ext_log` VALUES (8577, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"492e3012-94c8-407a-9fc0-aac001132312\",\"success\":true}', 1, 4, '2021-10-20 21:31:37');
INSERT INTO `ext_log` VALUES (8578, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"admin\",\"captchaCode\":\"6\",\"uid\":\"69af6b5abbaa48b1b0286a863892d23d\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"ac973b1fa4174067bb555288f01fc952\",\"isLogin\":true,\"loginId\":\"1\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"e42f1ac1-b990-4ff6-b03c-4bf6cfa9ed00\",\"success\":true}', 1, 58, '2021-10-20 21:31:43');
INSERT INTO `ext_log` VALUES (8579, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"df3bcb72-1762-4b0b-9e65-c61e9fea6c46\",\"success\":true}', 1, 4, '2021-10-20 21:31:44');
INSERT INTO `ext_log` VALUES (8580, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-20 21:31:44');
INSERT INTO `ext_log` VALUES (8581, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":81}],\"requestId\":\"a730dd9f-4411-4034-9839-f3ea646cb9c7\",\"success\":true,\"count\":10}', 1, 3, '2021-10-20 21:31:45');
INSERT INTO `ext_log` VALUES (8582, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":82}],\"requestId\":\"600a5e4c-a24f-46d5-90d1-a4714dc56190\",\"success\":true}', 1, 3, '2021-10-20 21:31:45');
INSERT INTO `ext_log` VALUES (8583, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 3, '2021-10-20 21:31:47');
INSERT INTO `ext_log` VALUES (8584, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 4, '2021-10-20 21:31:47');
INSERT INTO `ext_log` VALUES (8585, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 8, '2021-10-20 21:31:50');
INSERT INTO `ext_log` VALUES (8586, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/297', 'SysMenuController.delete(..)', '[297]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"e96e023d-683b-436d-9e9d-8a559642770e\",\"success\":true}', 1, 20, '2021-10-20 21:31:55');
INSERT INTO `ext_log` VALUES (8587, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-20 21:33:58');
INSERT INTO `ext_log` VALUES (8588, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/293', 'SysMenuController.get(..)', '[293]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":293,\"pid\":283,\"title\":\"查看\",\"icon\":\"layui-icon \",\"href\":\"\",\"openType\":\"\",\"sort\":1,\"enable\":true,\"remark\":null,\"type\":2,\"powerCode\":\"weblog.list\",\"dataFilterType\":null,\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"3aae05b6-3ca0-4f58-b7aa-d2b3cec2ce85\",\"success\":true}', 1, 4, '2021-10-20 21:38:26');
INSERT INTO `ext_log` VALUES (8589, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 5, '2021-10-20 21:38:26');
INSERT INTO `ext_log` VALUES (8590, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 30, '2021-10-20 21:42:10');
INSERT INTO `ext_log` VALUES (8591, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/getRolePower?roleId=5', 'SysRoleController.getRolePower(..)', '[5]', NULL, 1, 23, '2021-10-20 21:42:12');
INSERT INTO `ext_log` VALUES (8592, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/saveRolePower', 'SysRoleController.saveRolePower(..)', '[5,\"1,269,270,282,285,288,287,291,251,252,258,286,264,265,266,268,280,281,283,293,284,289,290\"]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"eddd43eb-7d88-444c-ad40-475acbd5063a\",\"success\":true}', 1, 35, '2021-10-20 21:44:40');
INSERT INTO `ext_log` VALUES (8593, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/getRolePower?roleId=5', 'SysRoleController.getRolePower(..)', '[5]', NULL, 1, 12, '2021-10-20 21:46:07');
INSERT INTO `ext_log` VALUES (8594, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/saveRolePower', 'SysRoleController.saveRolePower(..)', '[5,\"1,269,270,282,285,288,287,291,251,252,258,286,264,265,266,268,280,281,283,284,289,290\"]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"f1923537-afb8-46ad-ad07-857e18374f0b\",\"success\":true}', 1, 29, '2021-10-20 21:46:12');
INSERT INTO `ext_log` VALUES (8595, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"beb48907-1a2c-4993-9b94-083f2e1ee486\",\"success\":true}', 1, 3, '2021-10-20 21:46:15');
INSERT INTO `ext_log` VALUES (8596, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"laker\",\"captchaCode\":\"14\",\"uid\":\"0f51e91f5ead485e9198d34ebb2e2e23\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"c8d45cee8dfb4b548021e777c554dbf1\",\"isLogin\":true,\"loginId\":\"16\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"121d2792-9e9a-43ea-9764-14d2eb24a9b4\",\"success\":true}', 1, 67, '2021-10-20 21:46:20');
INSERT INTO `ext_log` VALUES (8597, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"6bf0c32c-786a-40cc-a127-dd0d27a48cfd\",\"success\":true}', 1, 6, '2021-10-20 21:46:22');
INSERT INTO `ext_log` VALUES (8598, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 14, '2021-10-20 21:46:22');
INSERT INTO `ext_log` VALUES (8599, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":99}],\"requestId\":\"6a767ce0-a9f2-46a6-b6f5-4b32effa6704\",\"success\":true,\"count\":10}', 1, 5, '2021-10-20 21:46:23');
INSERT INTO `ext_log` VALUES (8600, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":100}],\"requestId\":\"40df80c8-eb11-4810-8a47-b0073eadd99b\",\"success\":true}', 1, 12, '2021-10-20 21:46:23');
INSERT INTO `ext_log` VALUES (8601, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"1219204e-c9a2-4bfc-ade2-baf923b6d40b\",\"success\":true}', 1, 4, '2021-10-20 21:47:21');
INSERT INTO `ext_log` VALUES (8602, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"admin\",\"captchaCode\":\"-1\",\"uid\":\"83c42d0bd06f4511af2f4d226e208ffb\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"063c17b3e3884bdaac5aa41d34aff0b0\",\"isLogin\":true,\"loginId\":\"1\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"6c682467-9bec-4dde-9d89-bb39e96b31e5\",\"success\":true}', 1, 43, '2021-10-20 21:47:28');
INSERT INTO `ext_log` VALUES (8603, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"77191f4c-0465-493f-a08a-3a9824cc5b7c\",\"success\":true}', 1, 12, '2021-10-20 21:47:30');
INSERT INTO `ext_log` VALUES (8604, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 6, '2021-10-20 21:47:30');
INSERT INTO `ext_log` VALUES (8605, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":105}],\"requestId\":\"a80b060c-07a5-46eb-8f88-7a1cbdb0c6f0\",\"success\":true,\"count\":10}', 1, 3, '2021-10-20 21:47:31');
INSERT INTO `ext_log` VALUES (8606, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":106}],\"requestId\":\"839d72ab-d23e-4671-a4bf-b3ec2fa2289d\",\"success\":true}', 1, 6, '2021-10-20 21:47:31');
INSERT INTO `ext_log` VALUES (8607, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 4, '2021-10-20 21:47:33');
INSERT INTO `ext_log` VALUES (8608, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 26, '2021-10-20 21:47:35');
INSERT INTO `ext_log` VALUES (8609, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/getRolePower?roleId=5', 'SysRoleController.getRolePower(..)', '[5]', NULL, 1, 11, '2021-10-20 21:47:39');
INSERT INTO `ext_log` VALUES (8610, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/saveRolePower', 'SysRoleController.saveRolePower(..)', '[5,\"1,269,270,282,285,288,287,291,251,252,258,286,264,265,266,268,280,281,283,293,284,289,290\"]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"3e8e6f57-1b1f-4e85-af30-c956f435ec41\",\"success\":true}', 1, 36, '2021-10-20 21:47:46');
INSERT INTO `ext_log` VALUES (8611, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"b6081784-6701-426c-add7-8e003dbbb169\",\"success\":true}', 1, 3, '2021-10-20 21:47:50');
INSERT INTO `ext_log` VALUES (8612, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"laker\",\"captchaCode\":\"-6\",\"uid\":\"a71ade23bc714fb98d71cd8c4939ab9b\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"fecd765fdf68407b964fcddaabc4c0e7\",\"isLogin\":true,\"loginId\":\"16\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"5d4317ba-435c-4263-9b5f-63eb47b00fd1\",\"success\":true}', 1, 42, '2021-10-20 21:47:56');
INSERT INTO `ext_log` VALUES (8613, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"6c599e93-1c0c-48bd-ba73-37fe4af05a73\",\"success\":true}', 1, 4, '2021-10-20 21:47:58');
INSERT INTO `ext_log` VALUES (8614, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 10, '2021-10-20 21:47:58');
INSERT INTO `ext_log` VALUES (8615, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":115}],\"requestId\":\"6691ebc6-acc1-422e-b8b8-c5b6c5c8c7b0\",\"success\":true,\"count\":10}', 1, 7, '2021-10-20 21:47:58');
INSERT INTO `ext_log` VALUES (8616, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":116}],\"requestId\":\"b55a94a7-bc05-4a7c-b77a-df892f433449\",\"success\":true}', 1, 9, '2021-10-20 21:47:58');
INSERT INTO `ext_log` VALUES (8617, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/leave?page=1&limit=10', 'ExtLeaveController.pageAll(..)', '[1,10]', NULL, 1, 256, '2021-10-20 21:50:01');
INSERT INTO `ext_log` VALUES (8618, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"24a08e26-359a-4c11-8740-1045db050b24\",\"success\":true}', 1, 4, '2021-10-20 21:50:08');
INSERT INTO `ext_log` VALUES (8619, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"admin\",\"captchaCode\":\"2\",\"uid\":\"6a032a70196540a194ebe1651c046129\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"9119b94f3ed243ec8302847d4943b8c7\",\"isLogin\":true,\"loginId\":\"1\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"2676e4d8-b464-44ce-8bb9-4eb588c2e5fa\",\"success\":true}', 1, 73, '2021-10-20 21:50:34');
INSERT INTO `ext_log` VALUES (8620, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"079a79f5-8933-4cb6-b6d9-6b299db08d2b\",\"success\":true}', 1, 5, '2021-10-20 21:50:36');
INSERT INTO `ext_log` VALUES (8621, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 4, '2021-10-20 21:50:36');
INSERT INTO `ext_log` VALUES (8622, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":122}],\"requestId\":\"3da9eb81-485c-4629-a8e3-f81f90a0d005\",\"success\":true,\"count\":10}', 1, 13, '2021-10-20 21:50:38');
INSERT INTO `ext_log` VALUES (8623, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":123}],\"requestId\":\"1e580d4a-4088-4d38-ad37-6fd36dec4246\",\"success\":true}', 1, 7, '2021-10-20 21:50:38');
INSERT INTO `ext_log` VALUES (8624, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 27, '2021-10-20 21:51:03');
INSERT INTO `ext_log` VALUES (8625, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 16, '2021-10-20 21:51:03');
INSERT INTO `ext_log` VALUES (8626, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 15, '2021-10-20 21:51:03');
INSERT INTO `ext_log` VALUES (8627, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"29a58f85-2ec2-46ea-9bd1-1b3b47d574bc\",\"success\":true}', 1, 17, '2021-10-20 21:52:10');
INSERT INTO `ext_log` VALUES (8628, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 11, '2021-10-20 21:52:10');
INSERT INTO `ext_log` VALUES (8629, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 6, '2021-10-20 21:52:11');
INSERT INTO `ext_log` VALUES (8630, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 3, '2021-10-20 21:52:11');
INSERT INTO `ext_log` VALUES (8631, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 9, '2021-10-20 21:52:11');
INSERT INTO `ext_log` VALUES (8632, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":132}],\"requestId\":\"23ec97ce-b98c-43c3-b5cf-1a5c5b10ae0f\",\"success\":true,\"count\":10}', 1, 10, '2021-10-20 21:52:12');
INSERT INTO `ext_log` VALUES (8633, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":133}],\"requestId\":\"577a7420-7f44-4aa5-9179-cd757f306489\",\"success\":true}', 1, 11, '2021-10-20 21:52:12');
INSERT INTO `ext_log` VALUES (8634, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"93f4e470-adf9-4823-bbf9-f93e6109658b\",\"success\":true}', 1, 9, '2021-10-20 21:53:19');
INSERT INTO `ext_log` VALUES (8635, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 5, '2021-10-20 21:53:19');
INSERT INTO `ext_log` VALUES (8636, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 3, '2021-10-20 21:53:20');
INSERT INTO `ext_log` VALUES (8637, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 3, '2021-10-20 21:53:20');
INSERT INTO `ext_log` VALUES (8638, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 6, '2021-10-20 21:53:20');
INSERT INTO `ext_log` VALUES (8639, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":139}],\"requestId\":\"19800e3b-43ce-4be9-acba-af0f0b652456\",\"success\":true,\"count\":10}', 1, 9, '2021-10-20 21:53:20');
INSERT INTO `ext_log` VALUES (8640, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":140}],\"requestId\":\"9898db54-4be6-4efb-9e4a-33b3ba3d3e82\",\"success\":true}', 1, 11, '2021-10-20 21:53:20');
INSERT INTO `ext_log` VALUES (8641, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"5a317989-fe4d-4dd4-9195-1269af89f913\",\"success\":true}', 1, 3, '2021-10-20 21:53:23');
INSERT INTO `ext_log` VALUES (8642, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 4, '2021-10-20 21:53:23');
INSERT INTO `ext_log` VALUES (8643, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 5, '2021-10-20 21:53:24');
INSERT INTO `ext_log` VALUES (8644, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 5, '2021-10-20 21:53:24');
INSERT INTO `ext_log` VALUES (8645, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 5, '2021-10-20 21:53:24');
INSERT INTO `ext_log` VALUES (8646, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":146}],\"requestId\":\"6d4e2605-1732-42f9-8feb-bb3a2f69cc87\",\"success\":true,\"count\":10}', 1, 12, '2021-10-20 21:53:25');
INSERT INTO `ext_log` VALUES (8647, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":147}],\"requestId\":\"3de60df1-3dae-45b4-bd8e-d42cba2fcb27\",\"success\":true}', 1, 17, '2021-10-20 21:53:25');
INSERT INTO `ext_log` VALUES (8648, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"fda6c065-d0e7-4f27-a967-d502012c57e2\",\"success\":true}', 1, 5, '2021-10-20 21:53:34');
INSERT INTO `ext_log` VALUES (8649, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 4, '2021-10-20 21:53:34');
INSERT INTO `ext_log` VALUES (8650, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 3, '2021-10-20 21:53:35');
INSERT INTO `ext_log` VALUES (8651, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 5, '2021-10-20 21:53:35');
INSERT INTO `ext_log` VALUES (8652, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 4, '2021-10-20 21:53:35');
INSERT INTO `ext_log` VALUES (8653, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":153}],\"requestId\":\"bb2772cc-14e1-4998-8af6-e35309439e8a\",\"success\":true,\"count\":10}', 1, 15, '2021-10-20 21:53:36');
INSERT INTO `ext_log` VALUES (8654, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":154}],\"requestId\":\"997ce697-3e1f-4cf4-8cf4-27726f594fcf\",\"success\":true}', 1, 15, '2021-10-20 21:53:36');
INSERT INTO `ext_log` VALUES (8655, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"66c8ba52-50ac-4a7a-ac16-6a8188ab9168\",\"success\":true}', 1, 10, '2021-10-20 21:54:18');
INSERT INTO `ext_log` VALUES (8656, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 6, '2021-10-20 21:54:18');
INSERT INTO `ext_log` VALUES (8657, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 15, '2021-10-20 21:54:19');
INSERT INTO `ext_log` VALUES (8658, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 14, '2021-10-20 21:54:19');
INSERT INTO `ext_log` VALUES (8659, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 12, '2021-10-20 21:54:19');
INSERT INTO `ext_log` VALUES (8660, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":160}],\"requestId\":\"80b1e8da-5b9f-4ef4-a426-236eb668d88a\",\"success\":true,\"count\":10}', 1, 8, '2021-10-20 21:54:19');
INSERT INTO `ext_log` VALUES (8661, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":161}],\"requestId\":\"cfc5a0ac-3b25-4cdf-ad18-fccf0b660625\",\"success\":true}', 1, 7, '2021-10-20 21:54:19');
INSERT INTO `ext_log` VALUES (8662, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"8b52221e-4e4f-480e-9d5e-e75589901f7e\",\"success\":true}', 1, 9, '2021-10-20 21:55:04');
INSERT INTO `ext_log` VALUES (8663, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 8, '2021-10-20 21:55:04');
INSERT INTO `ext_log` VALUES (8664, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 7, '2021-10-20 21:55:06');
INSERT INTO `ext_log` VALUES (8665, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 14, '2021-10-20 21:55:06');
INSERT INTO `ext_log` VALUES (8666, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 16, '2021-10-20 21:55:06');
INSERT INTO `ext_log` VALUES (8667, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":167}],\"requestId\":\"c33bbdc6-4769-4022-a0d1-053f3f41caec\",\"success\":true,\"count\":10}', 1, 5, '2021-10-20 21:55:06');
INSERT INTO `ext_log` VALUES (8668, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":168}],\"requestId\":\"0057e914-b9df-46e3-b5ea-b542bc4f28e5\",\"success\":true}', 1, 3, '2021-10-20 21:55:06');
INSERT INTO `ext_log` VALUES (8669, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/294', 'SysMenuController.get(..)', '[294]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":294,\"pid\":270,\"title\":\"列表个人\",\"icon\":\"layui-icon \",\"href\":\"\",\"openType\":\"\",\"sort\":1,\"enable\":true,\"remark\":null,\"type\":3,\"powerCode\":\"ExtLeaveMapper.selectPage\",\"dataFilterType\":\"SELF\",\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"804cdf82-8c2a-4687-b996-2d60196fc7aa\",\"success\":true}', 1, 2, '2021-10-20 21:56:09');
INSERT INTO `ext_log` VALUES (8670, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-20 21:56:09');
INSERT INTO `ext_log` VALUES (8671, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 10, '2021-10-20 22:06:08');
INSERT INTO `ext_log` VALUES (8672, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/11', 'SysRoleController.get(..)', '[11]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"roleId\":11,\"roleName\":\"数据角色普通\",\"roleCode\":\"\",\"details\":\"\",\"enable\":true,\"createTime\":null,\"roleType\":2,\"checked\":false},\"requestId\":\"c944e7e2-6c98-40d9-ac5d-4d5711a12373\",\"success\":true}', 1, 9, '2021-10-20 22:06:14');
INSERT INTO `ext_log` VALUES (8673, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role', 'SysRoleController.saveOrUpdate(..)', '[{\"roleId\":11,\"roleName\":\"数据角色员工\",\"roleCode\":\"\",\"details\":\"\",\"enable\":true,\"createTime\":null,\"roleType\":2,\"checked\":false}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"061a7425-75cf-4196-bc0d-f893d32d1924\",\"success\":true}', 1, 24, '2021-10-20 22:06:21');
INSERT INTO `ext_log` VALUES (8674, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 27, '2021-10-20 22:06:22');
INSERT INTO `ext_log` VALUES (8675, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"laker\",\"captchaCode\":\"13\",\"uid\":\"048df820428640d99dca06299a020282\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"9faf68a5d76042f88364490e077e2ba1\",\"isLogin\":true,\"loginId\":\"16\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"92e1f32d-1c0f-40ea-a730-fc8a59c3664d\",\"success\":true}', 1, 66, '2021-10-20 22:21:55');
INSERT INTO `ext_log` VALUES (8676, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"31b53b5e-7e94-4a48-afb8-0ebd747f6951\",\"success\":true}', 1, 9, '2021-10-20 22:21:57');
INSERT INTO `ext_log` VALUES (8677, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 19, '2021-10-20 22:21:57');
INSERT INTO `ext_log` VALUES (8678, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 9, '2021-10-20 22:21:59');
INSERT INTO `ext_log` VALUES (8679, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 15, '2021-10-20 22:21:59');
INSERT INTO `ext_log` VALUES (8680, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 11, '2021-10-20 22:21:59');
INSERT INTO `ext_log` VALUES (8681, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 8, '2021-10-20 22:21:59');
INSERT INTO `ext_log` VALUES (8682, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":182}],\"requestId\":\"a4effcaa-52db-4ed6-9bf8-256dff02a0da\",\"success\":true,\"count\":10}', 1, 6, '2021-10-20 22:22:00');
INSERT INTO `ext_log` VALUES (8683, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":183}],\"requestId\":\"76e47041-d464-4ac2-9799-876fffbbdfb2\",\"success\":true}', 1, 6, '2021-10-20 22:22:00');
INSERT INTO `ext_log` VALUES (8684, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role/getRolePower?roleId=11', 'SysRoleController.getRolePower(..)', '[11]', NULL, 1, 26, '2021-10-20 22:22:02');
INSERT INTO `ext_log` VALUES (8685, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/16', 'SysUserController.get(..)', '[16]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":null,\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"7a71961f-c0f7-4f5b-bae1-22e0f814c500\",\"success\":true}', 1, 3, '2021-10-20 22:24:12');
INSERT INTO `ext_log` VALUES (8686, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/getRoles?userId=16', 'SysUserController.edit(..)', '[16]', NULL, 1, 7, '2021-10-20 22:24:12');
INSERT INTO `ext_log` VALUES (8687, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 5, '2021-10-20 22:24:12');
INSERT INTO `ext_log` VALUES (8688, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"b18e1ce0-5b98-4276-b34a-248b10551cd5\",\"success\":true}', 1, 14, '2021-10-20 22:26:59');
INSERT INTO `ext_log` VALUES (8689, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"admin\",\"captchaCode\":\"9\",\"uid\":\"c6df52fe01ab45bca29d9a64127dbd27\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"78add8f0921242d2950c51c6c5fa36d3\",\"isLogin\":true,\"loginId\":\"1\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"7e966aa9-b2be-4077-ad25-31828960b2cc\",\"success\":true}', 1, 58, '2021-10-20 22:27:05');
INSERT INTO `ext_log` VALUES (8690, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"36e38632-f203-4e9c-ab00-1d3995d07d66\",\"success\":true}', 1, 11, '2021-10-20 22:27:07');
INSERT INTO `ext_log` VALUES (8691, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 8, '2021-10-20 22:27:07');
INSERT INTO `ext_log` VALUES (8692, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":192}],\"requestId\":\"12622dbd-5e8a-4a9a-8d95-5dcb8973eb31\",\"success\":true,\"count\":10}', 1, 12, '2021-10-20 22:27:08');
INSERT INTO `ext_log` VALUES (8693, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":193}],\"requestId\":\"88958298-e150-4c32-9ce6-d78b055140a2\",\"success\":true}', 1, 16, '2021-10-20 22:27:08');
INSERT INTO `ext_log` VALUES (8694, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 11, '2021-10-20 22:27:10');
INSERT INTO `ext_log` VALUES (8695, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 12, '2021-10-20 22:27:10');
INSERT INTO `ext_log` VALUES (8696, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/data', 'SysDeptController.data(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 19, '2021-10-20 22:27:12');
INSERT INTO `ext_log` VALUES (8697, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 6, '2021-10-20 22:27:40');
INSERT INTO `ext_log` VALUES (8698, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/16', 'SysUserController.get(..)', '[16]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":null,\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"0c9f7c40-c315-47dc-9489-2e2014f9a85e\",\"success\":true}', 1, 2, '2021-10-20 22:27:49');
INSERT INTO `ext_log` VALUES (8699, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/getRoles?userId=16', 'SysUserController.edit(..)', '[16]', NULL, 1, 5, '2021-10-20 22:27:49');
INSERT INTO `ext_log` VALUES (8700, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 2, '2021-10-20 22:27:49');
INSERT INTO `ext_log` VALUES (8701, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user', 'SysUserController.saveOrUpdate(..)', '[{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"1\",\"enable\":null,\"email\":\"\",\"createTime\":null,\"roleIds\":\"5,11\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"6215c3e2-a927-4e72-96e9-289552ec0ecc\",\"success\":true}', 1, 23, '2021-10-20 22:27:52');
INSERT INTO `ext_log` VALUES (8702, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 14, '2021-10-20 22:27:53');
INSERT INTO `ext_log` VALUES (8703, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"4351335b-958f-4943-a0f9-f14779f9cd76\",\"success\":true}', 1, 4, '2021-10-20 22:27:56');
INSERT INTO `ext_log` VALUES (8704, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"laker\",\"captchaCode\":\"14\",\"uid\":\"df8595496e044acd90fbea09fef413be\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"7a3e7977c3924f1e83606331de3bd490\",\"isLogin\":true,\"loginId\":\"16\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"baadcb2b-7b6c-4f05-834b-e873ce763856\",\"success\":true}', 1, 34, '2021-10-20 22:28:02');
INSERT INTO `ext_log` VALUES (8705, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"a8ce5fc6-04ad-4e40-bdd3-4afa880e9b7f\",\"success\":true}', 1, 14, '2021-10-20 22:28:04');
INSERT INTO `ext_log` VALUES (8706, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 19, '2021-10-20 22:28:04');
INSERT INTO `ext_log` VALUES (8707, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":207}],\"requestId\":\"fd2295ff-6621-4acc-92c9-cfd5886e2b30\",\"success\":true,\"count\":10}', 1, 11, '2021-10-20 22:28:05');
INSERT INTO `ext_log` VALUES (8708, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":208}],\"requestId\":\"86b90adc-5615-498d-9bdd-79d9a420112e\",\"success\":true}', 1, 18, '2021-10-20 22:28:05');
INSERT INTO `ext_log` VALUES (8709, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/leave?page=1&limit=10', 'ExtLeaveController.pageAll(..)', '[1,10]', NULL, 1, 420, '2021-10-20 22:28:09');
INSERT INTO `ext_log` VALUES (8710, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"1472331a-bafe-4222-8b55-b68c4d74371d\",\"success\":true}', 1, 2, '2021-10-20 22:29:01');
INSERT INTO `ext_log` VALUES (8711, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"admin\",\"captchaCode\":\"2\",\"uid\":\"94f98ee0e9824a56819b575cd0c97c53\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"ace3b337852348819f578032091bb494\",\"isLogin\":true,\"loginId\":\"1\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"83aaf6ce-a028-4c52-ad91-9d26674e8411\",\"success\":true}', 1, 44, '2021-10-20 22:29:08');
INSERT INTO `ext_log` VALUES (8712, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"3359ae26-1c94-41b5-8156-c3bb523133c0\",\"success\":true}', 1, 17, '2021-10-20 22:29:10');
INSERT INTO `ext_log` VALUES (8713, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 12, '2021-10-20 22:29:10');
INSERT INTO `ext_log` VALUES (8714, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":214}],\"requestId\":\"c9224956-77dd-443d-9d9a-4b40ad5cb9f7\",\"success\":true,\"count\":10}', 1, 13, '2021-10-20 22:29:12');
INSERT INTO `ext_log` VALUES (8715, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":215}],\"requestId\":\"9110acd9-7d1c-4c2f-9834-c4e75eb05faa\",\"success\":true}', 1, 19, '2021-10-20 22:29:12');
INSERT INTO `ext_log` VALUES (8716, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 3, '2021-10-20 22:29:16');
INSERT INTO `ext_log` VALUES (8717, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 3, '2021-10-20 22:29:20');
INSERT INTO `ext_log` VALUES (8718, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 14, '2021-10-20 22:29:20');
INSERT INTO `ext_log` VALUES (8719, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/16', 'SysUserController.get(..)', '[16]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":null,\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"a58abe3b-3924-47b8-8c92-83d2d59bee72\",\"success\":true}', 1, 5, '2021-10-20 22:29:23');
INSERT INTO `ext_log` VALUES (8720, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/getRoles?userId=16', 'SysUserController.edit(..)', '[16]', NULL, 1, 13, '2021-10-20 22:29:23');
INSERT INTO `ext_log` VALUES (8721, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 7, '2021-10-20 22:29:23');
INSERT INTO `ext_log` VALUES (8722, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user', 'SysUserController.saveOrUpdate(..)', '[{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"1\",\"enable\":null,\"email\":\"\",\"createTime\":null,\"roleIds\":\"5,10\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"d6bee547-c571-4bac-bdc9-d6ed59a73b2d\",\"success\":true}', 1, 17, '2021-10-20 22:29:26');
INSERT INTO `ext_log` VALUES (8723, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 12, '2021-10-20 22:29:27');
INSERT INTO `ext_log` VALUES (8724, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"6e8cec66-6a8e-4db2-b539-0dc7985cb3c7\",\"success\":true}', 1, 3, '2021-10-20 22:29:30');
INSERT INTO `ext_log` VALUES (8725, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"laker\",\"captchaCode\":\"7\",\"uid\":\"e598856d073145018d09d0e74545711c\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"98c0a1ae6e6e4845b5da5af7a6f1205d\",\"isLogin\":true,\"loginId\":\"16\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"5d2486fd-b7dd-4eb4-91a0-b8a5a6ec2aa4\",\"success\":true}', 1, 34, '2021-10-20 22:29:35');
INSERT INTO `ext_log` VALUES (8726, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"cca00f03-c235-4653-b701-8fad0a9ef38e\",\"success\":true}', 1, 5, '2021-10-20 22:29:37');
INSERT INTO `ext_log` VALUES (8727, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 9, '2021-10-20 22:29:37');
INSERT INTO `ext_log` VALUES (8728, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":228}],\"requestId\":\"434a18fe-f010-480c-bf92-674c08ab0e31\",\"success\":true,\"count\":10}', 1, 12, '2021-10-20 22:29:39');
INSERT INTO `ext_log` VALUES (8729, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":229}],\"requestId\":\"274fb9d5-0b0f-4fd5-840d-991565b7330c\",\"success\":true}', 1, 7, '2021-10-20 22:29:39');
INSERT INTO `ext_log` VALUES (8730, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/onlineUsers?page=1&limit=10', 'LoginController.onlineUsers(..)', '[1,10]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"loginId\":null,\"userId\":16,\"nickName\":\"李哥\",\"ip\":\"127.0.0.1\",\"os\":\"Windows 10 or Windows Server 2016\",\"city\":\"0.0.内网IP\",\"browser\":\"Chrome\",\"tokenValue\":\"98c0a1ae6e6e4845b5da5af7a6f1205d\",\"loginTime\":\"2021-10-20 22:29:35\",\"lastActivityTime\":\"2021-10-20 22:29:41\"}],\"requestId\":\"b1e76f63-b61f-4bc1-a5b7-0e660ab09dfc\",\"success\":true,\"count\":1}', 1, 41, '2021-10-20 22:29:41');
INSERT INTO `ext_log` VALUES (8731, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/leave?page=1&limit=10', 'ExtLeaveController.pageAll(..)', '[1,10]', NULL, 1, 179, '2021-10-20 22:29:43');
INSERT INTO `ext_log` VALUES (8732, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"ea19498a-fc18-4ab2-afc7-a2946738ddfe\",\"success\":true}', 1, 10, '2021-10-20 22:33:16');
INSERT INTO `ext_log` VALUES (8733, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 17, '2021-10-20 22:33:16');
INSERT INTO `ext_log` VALUES (8734, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/onlineUsers?page=1&limit=10', 'LoginController.onlineUsers(..)', '[1,10]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"loginId\":null,\"userId\":16,\"nickName\":\"李哥\",\"ip\":\"127.0.0.1\",\"os\":\"Windows 10 or Windows Server 2016\",\"city\":\"0.0.内网IP\",\"browser\":\"Chrome\",\"tokenValue\":\"98c0a1ae6e6e4845b5da5af7a6f1205d\",\"loginTime\":\"2021-10-20 22:29:35\",\"lastActivityTime\":\"2021-10-20 22:33:17\"}],\"requestId\":\"3369cea2-3965-4e2c-a011-90bb31cb4ca0\",\"success\":true,\"count\":1}', 1, 20, '2021-10-20 22:33:17');
INSERT INTO `ext_log` VALUES (8735, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/leave?page=1&limit=10', 'ExtLeaveController.pageAll(..)', '[1,10]', NULL, 1, 290, '2021-10-20 22:33:18');
INSERT INTO `ext_log` VALUES (8736, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":236}],\"requestId\":\"36378b77-b414-4d16-9c42-1c7110faf373\",\"success\":true,\"count\":10}', 1, 4, '2021-10-20 22:33:18');
INSERT INTO `ext_log` VALUES (8737, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":237}],\"requestId\":\"dd355780-9485-4ccb-ad87-bca773413499\",\"success\":true}', 1, 3, '2021-10-20 22:33:18');
INSERT INTO `ext_log` VALUES (8738, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":16,\"userName\":\"laker\",\"nickName\":\"李哥\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"1\",\"enable\":1,\"email\":\"\",\"createTime\":\"2021-08-09 18:25:32\",\"roleIds\":null},\"requestId\":\"89e2be2e-3ff4-469c-90a7-7449d7034557\",\"success\":true}', 1, 5, '2021-10-20 22:36:50');
INSERT INTO `ext_log` VALUES (8739, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 9, '2021-10-20 22:36:50');
INSERT INTO `ext_log` VALUES (8740, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/onlineUsers?page=1&limit=10', 'LoginController.onlineUsers(..)', '[1,10]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"loginId\":null,\"userId\":16,\"nickName\":\"李哥\",\"ip\":\"127.0.0.1\",\"os\":\"Windows 10 or Windows Server 2016\",\"city\":\"0.0.内网IP\",\"browser\":\"Chrome\",\"tokenValue\":\"98c0a1ae6e6e4845b5da5af7a6f1205d\",\"loginTime\":\"2021-10-20 22:29:35\",\"lastActivityTime\":\"2021-10-20 22:36:52\"}],\"requestId\":\"aa6b065d-3fcc-410f-8a4e-e3dc7250b23e\",\"success\":true,\"count\":1}', 1, 4, '2021-10-20 22:36:52');
INSERT INTO `ext_log` VALUES (8741, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/leave?page=1&limit=10', 'ExtLeaveController.pageAll(..)', '[1,10]', NULL, 1, 310, '2021-10-20 22:36:53');
INSERT INTO `ext_log` VALUES (8742, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":242}],\"requestId\":\"76d2814c-4bb1-46dd-b4bf-0f80efde0ccc\",\"success\":true,\"count\":10}', 1, 16, '2021-10-20 22:36:53');
INSERT INTO `ext_log` VALUES (8743, 16, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":243}],\"requestId\":\"64df0e17-41c1-4ba7-b3a5-3153a3db0072\",\"success\":true}', 1, 15, '2021-10-20 22:36:53');
INSERT INTO `ext_log` VALUES (8744, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"admin\",\"captchaCode\":\"6\",\"uid\":\"c5fc0a1b7dbd4fa2987b836751a41af7\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"12fab83f0c444e35b9d7bfbb5cd301c7\",\"isLogin\":true,\"loginId\":\"1\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"c2424591-bb04-4c12-a0c2-aacd4a50db33\",\"success\":true}', 1, 72, '2021-10-21 10:05:39');
INSERT INTO `ext_log` VALUES (8745, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"882a8211-0369-498b-abe4-63bb78254a40\",\"success\":true}', 1, 16, '2021-10-21 10:05:40');
INSERT INTO `ext_log` VALUES (8746, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 24, '2021-10-21 10:05:40');
INSERT INTO `ext_log` VALUES (8747, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":247}],\"requestId\":\"a87f1928-bd8d-40d5-9271-df5bacff9772\",\"success\":true,\"count\":10}', 1, 23, '2021-10-21 10:05:43');
INSERT INTO `ext_log` VALUES (8748, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":3}],\"requestId\":\"8ec58164-97fa-4a22-b1ed-b2e200008244\",\"success\":true}', 1, 9, '2021-10-21 10:05:43');
INSERT INTO `ext_log` VALUES (8749, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 18, '2021-10-21 10:06:26');
INSERT INTO `ext_log` VALUES (8750, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 26, '2021-10-21 10:06:26');
INSERT INTO `ext_log` VALUES (8751, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 7, '2021-10-21 10:06:29');
INSERT INTO `ext_log` VALUES (8752, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/getRoles', 'SysUserController.edit(..)', '[null]', NULL, 1, 10, '2021-10-21 10:06:29');
INSERT INTO `ext_log` VALUES (8753, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user', 'SysUserController.saveOrUpdate(..)', '[{\"userId\":null,\"userName\":\"test\",\"nickName\":\"测试用户\",\"deptId\":15,\"dept\":null,\"deptName\":\"研发部\",\"sex\":1,\"phone\":\"18256079743\",\"enable\":1,\"email\":\"98@qq.com\",\"createTime\":null,\"roleIds\":\"5\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"50fbe26a-4776-4eb3-9aca-4dbf28bc4c0f\",\"success\":true}', 1, 28, '2021-10-21 10:07:07');
INSERT INTO `ext_log` VALUES (8754, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 17, '2021-10-21 10:07:08');
INSERT INTO `ext_log` VALUES (8755, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"3f5f8556-3b91-4337-8829-efdf58f7016f\",\"success\":true}', 1, 2, '2021-10-21 10:07:12');
INSERT INTO `ext_log` VALUES (8756, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"test\",\"captchaCode\":\"28\",\"uid\":\"1228e5344fc04dfa99736dd14556af58\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"d05c595811b54776be1f237c183f0d9f\",\"isLogin\":true,\"loginId\":\"24\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"5e4e5c79-c28c-42dc-85ea-0216873620fc\",\"success\":true}', 1, 24, '2021-10-21 10:07:21');
INSERT INTO `ext_log` VALUES (8757, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":24,\"userName\":\"test\",\"nickName\":\"测试用户\",\"deptId\":15,\"dept\":null,\"deptName\":\"研发部\",\"sex\":1,\"phone\":\"18256079743\",\"enable\":1,\"email\":\"98@qq.com\",\"createTime\":\"2021-10-21 10:07:07\",\"roleIds\":null},\"requestId\":\"dac16063-bfaf-473c-8bd2-1755829325df\",\"success\":true}', 1, 6, '2021-10-21 10:07:23');
INSERT INTO `ext_log` VALUES (8758, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 24, '2021-10-21 10:07:23');
INSERT INTO `ext_log` VALUES (8759, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":259}],\"requestId\":\"62e9ff7b-877d-40fa-9c07-199f8a7d72ac\",\"success\":true,\"count\":10}', 1, 4, '2021-10-21 10:07:23');
INSERT INTO `ext_log` VALUES (8760, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":16}],\"requestId\":\"ce6065ce-2bf7-4c93-b844-15ff40519acb\",\"success\":true}', 1, 5, '2021-10-21 10:07:24');
INSERT INTO `ext_log` VALUES (8761, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 9, '2021-10-21 10:11:53');
INSERT INTO `ext_log` VALUES (8762, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 6, '2021-10-21 10:11:53');
INSERT INTO `ext_log` VALUES (8763, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"88e574a6-f87e-4937-92af-23e689d84e33\",\"success\":true}', 1, 2, '2021-10-21 10:11:55');
INSERT INTO `ext_log` VALUES (8764, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"admin\",\"captchaCode\":\"-7\",\"uid\":\"317be591463e4eb8a5a04f260bfdce06\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"7012002b48bc425e9e028fd8e3fed563\",\"isLogin\":true,\"loginId\":\"1\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"c7cf346d-b2b4-4913-b70d-20210c231dc0\",\"success\":true}', 1, 23, '2021-10-21 10:12:02');
INSERT INTO `ext_log` VALUES (8765, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"29f292f1-1663-443e-8691-57f3b6bf3e7c\",\"success\":true}', 1, 6, '2021-10-21 10:12:04');
INSERT INTO `ext_log` VALUES (8766, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-21 10:12:04');
INSERT INTO `ext_log` VALUES (8767, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":267}],\"requestId\":\"5054a444-3532-46cc-900f-507c5728f2e6\",\"success\":true,\"count\":10}', 1, 4, '2021-10-21 10:12:04');
INSERT INTO `ext_log` VALUES (8768, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":24}],\"requestId\":\"c7368825-49d2-44dc-bdf1-89bb8b574943\",\"success\":true}', 1, 5, '2021-10-21 10:12:04');
INSERT INTO `ext_log` VALUES (8769, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 8, '2021-10-21 10:12:07');
INSERT INTO `ext_log` VALUES (8770, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 5, '2021-10-21 10:12:07');
INSERT INTO `ext_log` VALUES (8771, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 6, '2021-10-21 10:12:08');
INSERT INTO `ext_log` VALUES (8772, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/24', 'SysUserController.get(..)', '[24]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":24,\"userName\":\"test\",\"nickName\":\"测试用户\",\"deptId\":15,\"dept\":null,\"deptName\":null,\"sex\":1,\"phone\":\"18256079743\",\"enable\":1,\"email\":\"98@qq.com\",\"createTime\":\"2021-10-21 10:07:07\",\"roleIds\":null},\"requestId\":\"24295d77-6e81-4468-be9e-d7564fa62bf4\",\"success\":true}', 1, 3, '2021-10-21 10:12:12');
INSERT INTO `ext_log` VALUES (8773, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/getRoles?userId=24', 'SysUserController.edit(..)', '[24]', NULL, 1, 25, '2021-10-21 10:12:12');
INSERT INTO `ext_log` VALUES (8774, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 4, '2021-10-21 10:12:12');
INSERT INTO `ext_log` VALUES (8775, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user', 'SysUserController.saveOrUpdate(..)', '[{\"userId\":24,\"userName\":\"test\",\"nickName\":\"测试用户\",\"deptId\":15,\"dept\":null,\"deptName\":\"研发部\",\"sex\":1,\"phone\":\"18256079743\",\"enable\":null,\"email\":\"98@qq.com\",\"createTime\":null,\"roleIds\":\"6\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"cac348eb-81f8-4dbe-ad40-69f640b721c8\",\"success\":true}', 1, 15, '2021-10-21 10:12:16');
INSERT INTO `ext_log` VALUES (8776, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 15, '2021-10-21 10:12:18');
INSERT INTO `ext_log` VALUES (8777, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"bd6fa840-3c64-43a7-b40e-5336427f0a3e\",\"success\":true}', 1, 2, '2021-10-21 10:12:21');
INSERT INTO `ext_log` VALUES (8778, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"test\",\"captchaCode\":\"4\",\"uid\":\"98a335aceb574a6ab994e4cc1eece24f\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"8c8c52b9806b49f58be41be1e67f5ae7\",\"isLogin\":true,\"loginId\":\"24\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"6716ed29-4c79-460f-b555-31385e267e26\",\"success\":true}', 1, 21, '2021-10-21 10:12:26');
INSERT INTO `ext_log` VALUES (8779, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":24,\"userName\":\"test\",\"nickName\":\"测试用户\",\"deptId\":15,\"dept\":null,\"deptName\":\"研发部\",\"sex\":1,\"phone\":\"18256079743\",\"enable\":1,\"email\":\"98@qq.com\",\"createTime\":\"2021-10-21 10:07:07\",\"roleIds\":null},\"requestId\":\"d65591a8-103b-4041-b7e3-ff135f7ddae9\",\"success\":true}', 1, 5, '2021-10-21 10:12:28');
INSERT INTO `ext_log` VALUES (8780, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 11, '2021-10-21 10:12:28');
INSERT INTO `ext_log` VALUES (8781, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":281}],\"requestId\":\"20af6344-4dba-49e2-8d39-990c6350a7e9\",\"success\":true,\"count\":10}', 1, 3, '2021-10-21 10:12:28');
INSERT INTO `ext_log` VALUES (8782, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":38}],\"requestId\":\"93fe441e-f0e7-4785-b3e0-de4e35627e77\",\"success\":true}', 1, 4, '2021-10-21 10:12:28');
INSERT INTO `ext_log` VALUES (8783, 24, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/loginOut', 'LoginController.loginOut()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":null,\"requestId\":\"f2ee5130-41c5-4d9b-8afc-06de69ed1e01\",\"success\":true}', 1, 2, '2021-10-21 10:12:41');
INSERT INTO `ext_log` VALUES (8784, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"admin\",\"captchaCode\":\"18\",\"uid\":\"671cee28a0b54b0d8544b6e32f4397b9\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"fda371bf929c4733b34e71481d706823\",\"isLogin\":true,\"loginId\":\"1\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"487cb5cb-9de0-4ada-9970-57ce8247781a\",\"success\":true}', 1, 20, '2021-10-21 10:13:51');
INSERT INTO `ext_log` VALUES (8785, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"4cfd7d65-1c40-4aaa-a847-4c4a73135550\",\"success\":true}', 1, 5, '2021-10-21 10:13:53');
INSERT INTO `ext_log` VALUES (8786, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 4, '2021-10-21 10:13:53');
INSERT INTO `ext_log` VALUES (8787, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":287}],\"requestId\":\"b6222d7d-9f97-4db5-b6d0-5f3a6047f8cd\",\"success\":true,\"count\":10}', 1, 4, '2021-10-21 10:13:53');
INSERT INTO `ext_log` VALUES (8788, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":44}],\"requestId\":\"a8e9d1cf-16a5-4b9f-8124-f9b623e0ceb5\",\"success\":true}', 1, 4, '2021-10-21 10:13:53');
INSERT INTO `ext_log` VALUES (8789, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 4, '2021-10-21 10:13:56');
INSERT INTO `ext_log` VALUES (8790, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 5, '2021-10-21 10:13:56');
INSERT INTO `ext_log` VALUES (8791, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/flow/process/modelJson?processId=a7a5d0b6a4e542ce99308bebeedb1452', 'SnakerflowFacetsController.getProcess(..)', '[\"a7a5d0b6a4e542ce99308bebeedb1452\"]', NULL, 1, 10, '2021-10-21 10:14:03');
INSERT INTO `ext_log` VALUES (8792, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/getAll', 'SysUserController.getAll()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"name\":\"请选择\",\"value\":\"\"},{\"name\":\"超级管理员\",\"value\":\"1\"},{\"name\":\"李哥\",\"value\":\"16\"},{\"name\":\"陈总\",\"value\":\"17\"},{\"name\":\"张总\",\"value\":\"18\"},{\"name\":\"测试用户\",\"value\":\"24\"}],\"requestId\":\"decb0754-d087-407b-a780-69c23e8e5244\",\"success\":true}', 1, 4, '2021-10-21 10:14:03');
INSERT INTO `ext_log` VALUES (8793, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 8, '2021-10-21 10:16:21');
INSERT INTO `ext_log` VALUES (8794, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 4, '2021-10-21 10:16:24');
INSERT INTO `ext_log` VALUES (8795, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/1', 'SysUserController.get(..)', '[1]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":null,\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"717d08d1-3f7e-4c48-9b2b-d3c40d174546\",\"success\":true}', 1, 3, '2021-10-21 10:16:36');
INSERT INTO `ext_log` VALUES (8796, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/getRoles?userId=1', 'SysUserController.edit(..)', '[1]', NULL, 1, 6, '2021-10-21 10:16:36');
INSERT INTO `ext_log` VALUES (8797, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 3, '2021-10-21 10:16:36');
INSERT INTO `ext_log` VALUES (8798, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user/getRoles', 'SysUserController.edit(..)', '[null]', NULL, 1, 5, '2021-10-21 10:17:01');
INSERT INTO `ext_log` VALUES (8799, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 5, '2021-10-21 10:17:01');
INSERT INTO `ext_log` VALUES (8800, NULL, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/login', 'LoginController.login(..)', '[{\"username\":\"admin\",\"captchaCode\":\"9\",\"uid\":\"ea4afd58bf7540afbae6454e4e27e924\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"tokenName\":\"LakerToken\",\"tokenValue\":\"f7389667f29a4962b123b76839b015e1\",\"isLogin\":true,\"loginId\":\"1\",\"loginType\":\"login\",\"tokenTimeout\":1799,\"sessionTimeout\":1799,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":1800,\"loginDevice\":\"default-device\",\"tag\":null},\"requestId\":\"78f9bb61-e3e4-48bf-b2e5-4616120f9891\",\"success\":true}', 1, 69, '2021-10-21 11:22:56');
INSERT INTO `ext_log` VALUES (8801, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"19c04f45-fe22-4bed-aac8-1f60b9a33202\",\"success\":true}', 1, 14, '2021-10-21 11:22:57');
INSERT INTO `ext_log` VALUES (8802, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 20, '2021-10-21 11:22:57');
INSERT INTO `ext_log` VALUES (8803, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":303}],\"requestId\":\"a1c4388f-efeb-4e35-860f-e0916fe7e9ec\",\"success\":true,\"count\":10}', 1, 15, '2021-10-21 11:22:58');
INSERT INTO `ext_log` VALUES (8804, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":60}],\"requestId\":\"fa36304e-22db-46ec-9c9f-d472ecba2e19\",\"success\":true}', 1, 13, '2021-10-21 11:22:58');
INSERT INTO `ext_log` VALUES (8805, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 15, '2021-10-21 11:23:12');
INSERT INTO `ext_log` VALUES (8806, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 25, '2021-10-21 11:23:12');
INSERT INTO `ext_log` VALUES (8807, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dict?page=1&limit=10', 'SysDictController.pageAll(..)', '[1,10]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"dictId\":20,\"dictCode\":\"111111\",\"dictName\":\"12\",\"description\":\"1\",\"dictData\":null,\"enable\":true,\"createTime\":null}],\"requestId\":\"08c4fcf2-fee4-4788-9fab-0172ef68d10a\",\"success\":true,\"count\":1}', 1, 13, '2021-10-21 11:23:17');
INSERT INTO `ext_log` VALUES (8808, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/data', 'SysDeptController.data(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 8, '2021-10-21 11:23:25');
INSERT INTO `ext_log` VALUES (8809, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 13, '2021-10-21 11:23:27');
INSERT INTO `ext_log` VALUES (8810, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 10, '2021-10-21 11:23:28');
INSERT INTO `ext_log` VALUES (8811, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"http://101.132.189.23:81/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":null,\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"7da777e2-6908-48c2-8aa5-e6da108cb041\",\"success\":true}', 1, 4, '2021-10-21 11:23:45');
INSERT INTO `ext_log` VALUES (8812, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 7, '2021-10-21 11:23:45');
INSERT INTO `ext_log` VALUES (8813, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"db46f459-bae1-48ff-9498-5918cd3e38d4\",\"success\":true}', 1, 6, '2021-10-21 11:26:48');
INSERT INTO `ext_log` VALUES (8814, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 5, '2021-10-21 11:26:48');
INSERT INTO `ext_log` VALUES (8815, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dict?page=1&limit=10', 'SysDictController.pageAll(..)', '[1,10]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"dictId\":20,\"dictCode\":\"111111\",\"dictName\":\"12\",\"description\":\"1\",\"dictData\":null,\"enable\":true,\"createTime\":null}],\"requestId\":\"53fbbc84-0c8c-4a9c-b9e7-c2dd9976c737\",\"success\":true,\"count\":1}', 1, 7, '2021-10-21 11:26:50');
INSERT INTO `ext_log` VALUES (8816, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 7, '2021-10-21 11:26:50');
INSERT INTO `ext_log` VALUES (8817, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 8, '2021-10-21 11:26:50');
INSERT INTO `ext_log` VALUES (8818, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 6, '2021-10-21 11:26:50');
INSERT INTO `ext_log` VALUES (8819, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":319}],\"requestId\":\"cda445d8-7007-45c3-92af-0a10f406f330\",\"success\":true,\"count\":10}', 1, 4, '2021-10-21 11:26:50');
INSERT INTO `ext_log` VALUES (8820, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":76}],\"requestId\":\"f9374e4e-d3b7-4b0a-9a43-f13586fbd90f\",\"success\":true}', 1, 4, '2021-10-21 11:26:50');
INSERT INTO `ext_log` VALUES (8821, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/data', 'SysDeptController.data(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 5, '2021-10-21 11:26:50');
INSERT INTO `ext_log` VALUES (8822, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 7, '2021-10-21 11:26:50');
INSERT INTO `ext_log` VALUES (8823, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"http://101.132.189.23:81/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":null,\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"21a54998-4089-46d4-847a-2a5693e2b475\",\"success\":true}', 1, 3, '2021-10-21 11:27:00');
INSERT INTO `ext_log` VALUES (8824, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 5, '2021-10-21 11:27:00');
INSERT INTO `ext_log` VALUES (8825, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"0e2311b7-b598-4582-bd52-a1d29bcbdd55\",\"success\":true}', 1, 6, '2021-10-21 11:27:17');
INSERT INTO `ext_log` VALUES (8826, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 4, '2021-10-21 11:27:17');
INSERT INTO `ext_log` VALUES (8827, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dict?page=1&limit=10', 'SysDictController.pageAll(..)', '[1,10]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"dictId\":20,\"dictCode\":\"111111\",\"dictName\":\"12\",\"description\":\"1\",\"dictData\":null,\"enable\":true,\"createTime\":null}],\"requestId\":\"837158ab-3efc-40c7-b2f4-b26e4599d9ef\",\"success\":true,\"count\":1}', 1, 6, '2021-10-21 11:27:19');
INSERT INTO `ext_log` VALUES (8828, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/tree', 'SysDeptController.tree(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 5, '2021-10-21 11:27:19');
INSERT INTO `ext_log` VALUES (8829, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/user?page=1&limit=10', 'SysUserController.pageAll(..)', '[1,10,null,null]', NULL, 1, 10, '2021-10-21 11:27:19');
INSERT INTO `ext_log` VALUES (8830, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 6, '2021-10-21 11:27:19');
INSERT INTO `ext_log` VALUES (8831, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 7, '2021-10-21 11:27:19');
INSERT INTO `ext_log` VALUES (8832, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":332}],\"requestId\":\"16ae75df-0b76-42ab-bac2-7ec825463baa\",\"success\":true,\"count\":10}', 1, 9, '2021-10-21 11:27:20');
INSERT INTO `ext_log` VALUES (8833, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":88}],\"requestId\":\"5a02deb4-215c-4a8f-a54f-7192ccded94c\",\"success\":true}', 1, 7, '2021-10-21 11:27:20');
INSERT INTO `ext_log` VALUES (8834, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/data', 'SysDeptController.data(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 4, '2021-10-21 11:27:20');
INSERT INTO `ext_log` VALUES (8835, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"http://101.132.189.23:81/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":null,\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"6b63f8f7-60b8-4e56-9ca3-863a6bf3b89d\",\"success\":true}', 1, 5, '2021-10-21 11:27:26');
INSERT INTO `ext_log` VALUES (8836, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 4, '2021-10-21 11:27:27');
INSERT INTO `ext_log` VALUES (8837, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 5, '2021-10-21 11:29:19');
INSERT INTO `ext_log` VALUES (8838, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"http://101.132.189.23:81/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":null,\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"5e75cee9-eb98-42df-9d5c-656b5bc8328f\",\"success\":true}', 1, 5, '2021-10-21 11:29:25');
INSERT INTO `ext_log` VALUES (8839, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:29:25');
INSERT INTO `ext_log` VALUES (8840, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"0ffb44b3-6876-4d86-b2df-e36a26f58ed9\",\"success\":true}', 1, 6, '2021-10-21 11:30:38');
INSERT INTO `ext_log` VALUES (8841, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-21 11:30:38');
INSERT INTO `ext_log` VALUES (8842, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 5, '2021-10-21 11:30:39');
INSERT INTO `ext_log` VALUES (8843, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":343}],\"requestId\":\"d5fd5f3d-fa84-4f96-84b6-0d18c3a94bd1\",\"success\":true,\"count\":10}', 1, 4, '2021-10-21 11:30:39');
INSERT INTO `ext_log` VALUES (8844, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":100}],\"requestId\":\"a181e1d6-77fe-45dc-a158-d76452849ed8\",\"success\":true}', 1, 5, '2021-10-21 11:30:39');
INSERT INTO `ext_log` VALUES (8845, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"http://101.132.189.23:81/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":null,\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"34a40c14-8681-42ff-b854-45ff7ebfeb0d\",\"success\":true}', 1, 2, '2021-10-21 11:30:43');
INSERT INTO `ext_log` VALUES (8846, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 5, '2021-10-21 11:30:43');
INSERT INTO `ext_log` VALUES (8847, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"64a00bf0-4588-40de-8900-fb526892757f\",\"success\":true}', 1, 6, '2021-10-21 11:31:04');
INSERT INTO `ext_log` VALUES (8848, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-21 11:31:04');
INSERT INTO `ext_log` VALUES (8849, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 3, '2021-10-21 11:31:04');
INSERT INTO `ext_log` VALUES (8850, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":350}],\"requestId\":\"e3ce0f24-6222-44ec-a9c5-a0f9b2d61c10\",\"success\":true,\"count\":10}', 1, 8, '2021-10-21 11:31:05');
INSERT INTO `ext_log` VALUES (8851, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":107}],\"requestId\":\"3fee6fd8-8434-4a56-86cc-6e3211389619\",\"success\":true}', 1, 4, '2021-10-21 11:31:05');
INSERT INTO `ext_log` VALUES (8852, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"http://101.132.189.23:81/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":null,\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"353b4143-b9d3-49cc-b736-d07a2e06c5c8\",\"success\":true}', 1, 3, '2021-10-21 11:31:08');
INSERT INTO `ext_log` VALUES (8853, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 4, '2021-10-21 11:31:08');
INSERT INTO `ext_log` VALUES (8854, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"3c528f6f-93d6-4dc2-b18d-e1130d6d9465\",\"success\":true}', 1, 5, '2021-10-21 11:31:42');
INSERT INTO `ext_log` VALUES (8855, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-21 11:31:42');
INSERT INTO `ext_log` VALUES (8856, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 4, '2021-10-21 11:31:43');
INSERT INTO `ext_log` VALUES (8857, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":357}],\"requestId\":\"b68073fd-a8cf-40d3-989d-63e2105ca000\",\"success\":true,\"count\":10}', 1, 4, '2021-10-21 11:31:43');
INSERT INTO `ext_log` VALUES (8858, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":114}],\"requestId\":\"cbd2f75c-b3ec-4a73-a56f-27a72d2b6a2e\",\"success\":true}', 1, 4, '2021-10-21 11:31:43');
INSERT INTO `ext_log` VALUES (8859, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"http://101.132.189.23:81/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":null,\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"63ff3a8d-e0d3-46a4-9da4-a2c748343738\",\"success\":true}', 1, 2, '2021-10-21 11:31:47');
INSERT INTO `ext_log` VALUES (8860, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:31:47');
INSERT INTO `ext_log` VALUES (8861, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"8dd16bb9-b856-43f3-bad7-ebfd8eeca3d7\",\"success\":true}', 1, 4, '2021-10-21 11:34:41');
INSERT INTO `ext_log` VALUES (8862, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-21 11:34:41');
INSERT INTO `ext_log` VALUES (8863, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 4, '2021-10-21 11:34:42');
INSERT INTO `ext_log` VALUES (8864, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":364}],\"requestId\":\"9d4fe366-6c0e-4630-9bd0-b459c65dacb7\",\"success\":true,\"count\":10}', 1, 4, '2021-10-21 11:34:42');
INSERT INTO `ext_log` VALUES (8865, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":121}],\"requestId\":\"6b2aed25-7230-494b-9415-aa2a13c4631a\",\"success\":true}', 1, 4, '2021-10-21 11:34:42');
INSERT INTO `ext_log` VALUES (8866, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"e17885b9-6da4-417c-99ba-6f6e5bd8d4e2\",\"success\":true}', 1, 6, '2021-10-21 11:35:30');
INSERT INTO `ext_log` VALUES (8867, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-21 11:35:30');
INSERT INTO `ext_log` VALUES (8868, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 3, '2021-10-21 11:35:30');
INSERT INTO `ext_log` VALUES (8869, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":369}],\"requestId\":\"4a0f09f1-36f9-48c8-a1a6-f2412325fc7b\",\"success\":true,\"count\":10}', 1, 5, '2021-10-21 11:35:30');
INSERT INTO `ext_log` VALUES (8870, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":126}],\"requestId\":\"79507248-c28a-460e-8339-a59dacdf9ada\",\"success\":true}', 1, 5, '2021-10-21 11:35:30');
INSERT INTO `ext_log` VALUES (8871, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"http://101.132.189.23:81/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":null,\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"d087bb3a-d838-437c-8787-8afdbf116d5e\",\"success\":true}', 1, 2, '2021-10-21 11:35:35');
INSERT INTO `ext_log` VALUES (8872, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:35:35');
INSERT INTO `ext_log` VALUES (8873, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"326acb71-ddeb-44ee-908a-41bbfa7de3da\",\"success\":true}', 1, 5, '2021-10-21 11:38:20');
INSERT INTO `ext_log` VALUES (8874, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-21 11:38:20');
INSERT INTO `ext_log` VALUES (8875, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 4, '2021-10-21 11:38:20');
INSERT INTO `ext_log` VALUES (8876, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":376}],\"requestId\":\"0f7c76e2-ad35-4d64-916d-ef72de8be274\",\"success\":true,\"count\":10}', 1, 3, '2021-10-21 11:38:20');
INSERT INTO `ext_log` VALUES (8877, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":133}],\"requestId\":\"ed16597a-f102-46da-a611-a23ab189e334\",\"success\":true}', 1, 4, '2021-10-21 11:38:20');
INSERT INTO `ext_log` VALUES (8878, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"http://101.132.189.23:81/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":null,\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"ce03e483-b909-486a-af84-47b9311862e3\",\"success\":true}', 1, 2, '2021-10-21 11:38:24');
INSERT INTO `ext_log` VALUES (8879, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:38:24');
INSERT INTO `ext_log` VALUES (8880, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 4, '2021-10-21 11:39:07');
INSERT INTO `ext_log` VALUES (8881, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"http://101.132.189.23:81/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"d34f785f-deca-4be6-a5cf-8337470aea40\",\"success\":true}', 1, 2, '2021-10-21 11:39:11');
INSERT INTO `ext_log` VALUES (8882, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:39:11');
INSERT INTO `ext_log` VALUES (8883, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu', 'SysMenuController.saveOrUpdate(..)', '[{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":null,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"a40d9937-5164-41dc-8a6f-0f48f12dd41b\",\"success\":true}', 1, 14, '2021-10-21 11:39:16');
INSERT INTO `ext_log` VALUES (8884, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 9, '2021-10-21 11:39:17');
INSERT INTO `ext_log` VALUES (8885, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"73f11817-e063-4c2c-8bd8-ed72f149f5f3\",\"success\":true}', 1, 4, '2021-10-21 11:39:21');
INSERT INTO `ext_log` VALUES (8886, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-21 11:39:21');
INSERT INTO `ext_log` VALUES (8887, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 5, '2021-10-21 11:39:22');
INSERT INTO `ext_log` VALUES (8888, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":388}],\"requestId\":\"2409cded-b9b0-4a04-b67a-b65d734f0a5f\",\"success\":true,\"count\":10}', 1, 4, '2021-10-21 11:39:22');
INSERT INTO `ext_log` VALUES (8889, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":145}],\"requestId\":\"8e0f13d6-e863-41b8-953c-788bb740f0f1\",\"success\":true}', 1, 5, '2021-10-21 11:39:22');
INSERT INTO `ext_log` VALUES (8890, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"45fe711b-8bbb-402f-96c1-e1bc57f600b7\",\"success\":true}', 1, 2, '2021-10-21 11:39:33');
INSERT INTO `ext_log` VALUES (8891, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:39:33');
INSERT INTO `ext_log` VALUES (8892, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu', 'SysMenuController.saveOrUpdate(..)', '[{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":null,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"d9db71a5-1f1f-4298-8eb6-a702d9def2be\",\"success\":true}', 1, 8, '2021-10-21 11:39:39');
INSERT INTO `ext_log` VALUES (8893, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 15, '2021-10-21 11:39:40');
INSERT INTO `ext_log` VALUES (8894, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"6cf03b03-8d2b-42a9-b8ee-a495c7dde254\",\"success\":true}', 1, 5, '2021-10-21 11:39:41');
INSERT INTO `ext_log` VALUES (8895, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-21 11:39:41');
INSERT INTO `ext_log` VALUES (8896, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 3, '2021-10-21 11:39:42');
INSERT INTO `ext_log` VALUES (8897, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":397}],\"requestId\":\"4e1d2628-a6aa-4a16-aef3-f59ba67bd4b4\",\"success\":true,\"count\":10}', 1, 5, '2021-10-21 11:39:42');
INSERT INTO `ext_log` VALUES (8898, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":154}],\"requestId\":\"942731b2-25d5-4f4e-92d5-10c6e85e1361\",\"success\":true}', 1, 5, '2021-10-21 11:39:42');
INSERT INTO `ext_log` VALUES (8899, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 6, '2021-10-21 11:39:50');
INSERT INTO `ext_log` VALUES (8900, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/266', 'SysMenuController.get(..)', '[266]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":266,\"pid\":251,\"title\":\"应用监控\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon-auz\",\"href\":\"http://101.132.189.23:81/monitoring\",\"openType\":\"_iframe\",\"sort\":10,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"7e518704-4618-43af-ac54-241ca3177d3b\",\"success\":true}', 1, 2, '2021-10-21 11:40:20');
INSERT INTO `ext_log` VALUES (8901, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:40:20');
INSERT INTO `ext_log` VALUES (8902, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu', 'SysMenuController.saveOrUpdate(..)', '[{\"menuId\":266,\"pid\":251,\"title\":\"应用监控\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon-auz\",\"href\":\"http://localhost:8080/monitoring\",\"openType\":\"_iframe\",\"sort\":10,\"enable\":null,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"}]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":true,\"requestId\":\"b5cd1a0b-8c56-48e1-88e3-8eb3681da919\",\"success\":true}', 1, 6, '2021-10-21 11:40:33');
INSERT INTO `ext_log` VALUES (8903, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 10, '2021-10-21 11:40:34');
INSERT INTO `ext_log` VALUES (8904, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"42f6d323-d368-41a5-a526-0db5a58c7f91\",\"success\":true}', 1, 5, '2021-10-21 11:40:40');
INSERT INTO `ext_log` VALUES (8905, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-21 11:40:40');
INSERT INTO `ext_log` VALUES (8906, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 4, '2021-10-21 11:40:41');
INSERT INTO `ext_log` VALUES (8907, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 4, '2021-10-21 11:40:41');
INSERT INTO `ext_log` VALUES (8908, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":408}],\"requestId\":\"fd629c0c-817d-43fb-a955-4e211d04287d\",\"success\":true,\"count\":10}', 1, 4, '2021-10-21 11:40:41');
INSERT INTO `ext_log` VALUES (8909, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":165}],\"requestId\":\"179c24db-cad8-41fb-bfa7-78303347ea93\",\"success\":true}', 1, 3, '2021-10-21 11:40:41');
INSERT INTO `ext_log` VALUES (8910, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/281', 'SysMenuController.get(..)', '[281]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":281,\"pid\":251,\"title\":\"部门管理\",\"icon\":\"layui-icon layui-icon \",\"href\":\"view/system/deptment.html\",\"openType\":\"_iframe\",\"sort\":4,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"cee8aad6-b89f-4e3b-932d-50308d35f949\",\"success\":true}', 1, 3, '2021-10-21 11:40:53');
INSERT INTO `ext_log` VALUES (8911, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:40:53');
INSERT INTO `ext_log` VALUES (8912, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/api/v1/userInfo', 'LoginController.userInfo()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"userId\":1,\"userName\":\"admin\",\"nickName\":\"超级管理员\",\"deptId\":14,\"dept\":null,\"deptName\":\"业务部\",\"sex\":1,\"phone\":\"11111111111\",\"enable\":1,\"email\":\"935009@98.com\",\"createTime\":\"2021-08-15 11:02:15\",\"roleIds\":null},\"requestId\":\"9b45d33f-00f0-4d88-984e-b61a545ea1e9\",\"success\":true}', 1, 4, '2021-10-21 11:42:02');
INSERT INTO `ext_log` VALUES (8913, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/tree', 'SysMenuController.tree()', '[]', NULL, 1, 3, '2021-10-21 11:42:02');
INSERT INTO `ext_log` VALUES (8914, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/role?page=1&limit=10', 'SysRoleController.pageAll(..)', '[1,10,null]', NULL, 1, 3, '2021-10-21 11:42:03');
INSERT INTO `ext_log` VALUES (8915, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/list', 'SysMenuController.list()', '[]', NULL, 1, 4, '2021-10-21 11:42:03');
INSERT INTO `ext_log` VALUES (8916, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visitsTop10IP?page=1&limit=10', 'ExtLogController.visitsTop10IP()', '[]', '{\"code\":\"0\",\"msg\":\"\",\"data\":[{\"ip\":\"127.0.0.1\",\"city\":null,\"value\":416}],\"requestId\":\"209b22ae-b51b-4db8-a684-106ffa4dbba9\",\"success\":true,\"count\":10}', 1, 3, '2021-10-21 11:42:03');
INSERT INTO `ext_log` VALUES (8917, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/ext/log/visits7day', 'ExtLogController.visits7day()', '[]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":[{\"date\":\"2021-10-20\",\"value\":244},{\"date\":\"2021-10-21\",\"value\":173}],\"requestId\":\"a7ab3342-8180-44b9-9056-f04d0c90d049\",\"success\":true}', 1, 3, '2021-10-21 11:42:03');
INSERT INTO `ext_log` VALUES (8918, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/280', 'SysMenuController.get(..)', '[280]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":280,\"pid\":251,\"title\":\"角色管理\",\"icon\":\"layui-icon layui-icon \",\"href\":\"view/system/role.html\",\"openType\":\"_iframe\",\"sort\":3,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"0a62f67d-8aea-4f74-b6e7-588b7eaf8a95\",\"success\":true}', 1, 3, '2021-10-21 11:42:06');
INSERT INTO `ext_log` VALUES (8919, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:42:06');
INSERT INTO `ext_log` VALUES (8920, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/265', 'SysMenuController.get(..)', '[265]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":265,\"pid\":251,\"title\":\"流程实例\",\"icon\":\"layui-icon \",\"href\":\"view/flow/orderList.html\",\"openType\":\"\",\"sort\":9,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"0c51561e-d9a8-4549-a147-03c15f19b600\",\"success\":true}', 1, 2, '2021-10-21 11:42:24');
INSERT INTO `ext_log` VALUES (8921, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:42:24');
INSERT INTO `ext_log` VALUES (8922, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/284', 'SysMenuController.get(..)', '[284]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":284,\"pid\":251,\"title\":\"字典管理\",\"icon\":\"layui-icon layui-icon \",\"href\":\"view/system/dict.html\",\"openType\":\"_iframe\",\"sort\":13,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"5466d958-e29d-4490-9568-7bb403d8efe1\",\"success\":true}', 1, 4, '2021-10-21 11:42:30');
INSERT INTO `ext_log` VALUES (8923, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:42:30');
INSERT INTO `ext_log` VALUES (8924, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/268', 'SysMenuController.get(..)', '[268]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":268,\"pid\":251,\"title\":\"接口文档\",\"icon\":\"layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon \",\"href\":\"/doc.html\",\"openType\":\"_iframe\",\"sort\":5,\"enable\":true,\"remark\":null,\"type\":1,\"powerCode\":\"\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"59b97d1e-f1ad-4975-b572-3a059f75d7cc\",\"success\":true}', 1, 3, '2021-10-21 11:42:34');
INSERT INTO `ext_log` VALUES (8925, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 2, '2021-10-21 11:42:34');
INSERT INTO `ext_log` VALUES (8926, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/286', 'SysMenuController.get(..)', '[286]', '{\"code\":\"0\",\"msg\":\"操作成功\",\"data\":{\"menuId\":286,\"pid\":258,\"title\":\"查询\",\"icon\":\"layui-icon \",\"href\":\"\",\"openType\":\"\",\"sort\":1,\"enable\":false,\"remark\":null,\"type\":2,\"powerCode\":\"user-seach\",\"dataFilterType\":\"ALL\",\"createTime\":null,\"checkArr\":\"0\"},\"requestId\":\"957b8952-f95b-4575-8ceb-a5e48b8a8345\",\"success\":true}', 1, 2, '2021-10-21 11:44:02');
INSERT INTO `ext_log` VALUES (8927, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/menu/selectTree', 'SysMenuController.selectTree()', '[]', NULL, 1, 3, '2021-10-21 11:44:02');
INSERT INTO `ext_log` VALUES (8928, 1, '127.0.0.1', NULL, 'Windows 10 or Windows Server 2016.Chrome', '/sys/dept/data', 'SysDeptController.data(..)', '[{\"deptId\":null,\"deptName\":null,\"address\":null,\"pid\":null,\"status\":null,\"sort\":null}]', NULL, 1, 5, '2021-10-21 11:44:21');

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
) ENGINE = InnoDB AUTO_INCREMENT = 298 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单权限资源表' ROW_FORMAT = Compact;

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
) ENGINE = InnoDB AUTO_INCREMENT = 343 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

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
INSERT INTO `sys_role_power` VALUES (320, 5, 1);
INSERT INTO `sys_role_power` VALUES (321, 5, 269);
INSERT INTO `sys_role_power` VALUES (322, 5, 270);
INSERT INTO `sys_role_power` VALUES (323, 5, 282);
INSERT INTO `sys_role_power` VALUES (324, 5, 285);
INSERT INTO `sys_role_power` VALUES (325, 5, 288);
INSERT INTO `sys_role_power` VALUES (326, 5, 287);
INSERT INTO `sys_role_power` VALUES (327, 5, 291);
INSERT INTO `sys_role_power` VALUES (328, 5, 251);
INSERT INTO `sys_role_power` VALUES (329, 5, 252);
INSERT INTO `sys_role_power` VALUES (330, 5, 258);
INSERT INTO `sys_role_power` VALUES (331, 5, 286);
INSERT INTO `sys_role_power` VALUES (332, 5, 264);
INSERT INTO `sys_role_power` VALUES (333, 5, 265);
INSERT INTO `sys_role_power` VALUES (334, 5, 266);
INSERT INTO `sys_role_power` VALUES (335, 5, 268);
INSERT INTO `sys_role_power` VALUES (336, 5, 280);
INSERT INTO `sys_role_power` VALUES (337, 5, 281);
INSERT INTO `sys_role_power` VALUES (338, 5, 283);
INSERT INTO `sys_role_power` VALUES (339, 5, 293);
INSERT INTO `sys_role_power` VALUES (340, 5, 284);
INSERT INTO `sys_role_power` VALUES (341, 5, 289);
INSERT INTO `sys_role_power` VALUES (342, 5, 290);

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
