/*
 Navicat Premium Data Transfer

 Source Server         : zxb
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : db_pms

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 07/01/2025 20:13:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_detail`;
CREATE TABLE `tb_detail`  (
  `detail_id` int(0) NOT NULL,
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '20位工单id',
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传的图片url',
  PRIMARY KEY (`detail_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_detail
-- ----------------------------
INSERT INTO `tb_detail` VALUES (-1621073917, 'WF20250106211247AQRH', NULL);

-- ----------------------------
-- Table structure for tb_file
-- ----------------------------
DROP TABLE IF EXISTS `tb_file`;
CREATE TABLE `tb_file`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件名称',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` bigint(0) NULL DEFAULT NULL COMMENT '文件大小',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否禁用',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '访问路径',
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'md5',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1025 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件上传' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_file
-- ----------------------------
INSERT INTO `tb_file` VALUES (1024, 'R.jpg', 'jpg', 38, 1, 'http://localhost:90/api/file/09ab762316b1413f8de7af70b84ed3fe.jpg', '528cf5fb9b9315327bb3c7f299352162', 0);
INSERT INTO `tb_file` VALUES (1025, 'R.jpg', 'jpg', 38, 1, 'http://localhost:90/api/file/09ab762316b1413f8de7af70b84ed3fe.jpg', '528cf5fb9b9315327bb3c7f299352162', 0);

-- ----------------------------
-- Table structure for tb_notice
-- ----------------------------
DROP TABLE IF EXISTS `tb_notice`;
CREATE TABLE `tb_notice`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统公告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_notice
-- ----------------------------
INSERT INTO `tb_notice` VALUES (1, 'v3.0.0', '正式发布', '2023-10-24 20:05:32');
INSERT INTO `tb_notice` VALUES (2, 'v3.0.1', '修复一些已知bug', '2023-10-24 20:11:32');
INSERT INTO `tb_notice` VALUES (3, 'v3.0.3', '修复一些已知bug,和新增一些功能：（1）加入手机号和邮箱的正则校验（2）加入滑块组件、评分组件、计数器组件等！\n', '2023-12-19 09:33:25');
INSERT INTO `tb_notice` VALUES (4, 'v3.0.5', '修复一些为止bug，xinz自定义菜单相关配色', '2023-12-26 14:34:44');
INSERT INTO `tb_notice` VALUES (5, 'v-3.0.6', '新增数据可视化图表', '2023-12-28 14:22:38');

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `id` int(0) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `priority` tinyint(1) NULL DEFAULT NULL COMMENT '当前工单优先级',
  `related_person` int(0) NULL DEFAULT NULL COMMENT '当前流转到的处理人',
  `state` int(0) NULL DEFAULT NULL COMMENT '工单当前状态',
  `creator_id` int(0) NULL DEFAULT NULL COMMENT '提交工单的人',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `upadte_time` datetime(0) NULL DEFAULT NULL,
  `detail_id` int(0) NULL DEFAULT NULL COMMENT '当前工单的详细信息',
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `complaint` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES (-1621073918, '测试工单12121212', 2, 1, 1, 1, '2025-01-06 21:12:56', NULL, NULL, 'WF20250106211247AQRH', '31231232132321313123');

-- ----------------------------
-- Table structure for tb_order_history
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_history`;
CREATE TABLE `tb_order_history`  (
  `id` int(0) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '处理该工单的时间，也就是更新时间',
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工单的id',
  `circulation` int(0) NULL DEFAULT -1 COMMENT '流转状态，e.g.同意，转交，拒绝',
  `operator_id` int(0) NULL DEFAULT NULL COMMENT '当前流程的操作人id',
  `current_node` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前处理工单的节点 e.g.提交工单，负责人审批...',
  `operator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前流程的操作人姓名',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前操作人的处理意见(备注)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order_history
-- ----------------------------
INSERT INTO `tb_order_history` VALUES (-1621073916, '2025-01-06 21:12:56', 'WF20250106211247AQRH', 1, 1, '提交工单', 'admin', NULL);
INSERT INTO `tb_order_history` VALUES (2, '2025-01-06 21:12:56', 'WF20250106211247AQRH', 2, 3333, '嘿嘿工单', 'tes张三', NULL);

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '访问路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '组件路径',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '图标',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '标题',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` bigint(0) NULL DEFAULT 1 COMMENT '当前状态',
  `perms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限标识',
  `order_num` int(0) NULL DEFAULT 99 COMMENT '排序号',
  `hidden` tinyint(1) NULL DEFAULT 0 COMMENT '是否隐藏',
  `menu_type` tinyint(0) NULL DEFAULT NULL COMMENT '菜单类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 513 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_permission
-- ----------------------------
INSERT INTO `tb_permission` VALUES (1, 0, '/system', '', 'Setting', '系统管理', '2023-09-07 11:34:15', '2023-09-07 11:34:18', 1, 'system', 99, 0, 1);
INSERT INTO `tb_permission` VALUES (2, 1, '/system/user', '/user/index', 'User', '用户管理', '2023-01-23 13:13:45', '2023-01-23 13:13:49', 1, 'sys:user:list', 1, 0, 2);
INSERT INTO `tb_permission` VALUES (3, 1, '/system/role', '/role/index', 'UserFilled', '角色管理', '2023-01-23 13:13:52', '2023-01-23 13:13:55', 1, 'sys:role:list', 1, 0, 2);
INSERT INTO `tb_permission` VALUES (4, 1, '/system/permission', '/permission/index', 'Menu', '菜单管理', '2023-01-23 13:13:57', '2023-01-23 13:14:00', 1, 'sys:permission:list', 1, 0, 2);
INSERT INTO `tb_permission` VALUES (165, 2, '', '', '', '新增用户', NULL, NULL, 1, 'sys:user:add', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (166, 2, '', '', '', '修改用户', NULL, NULL, 1, 'sys:user:update', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (167, 2, '', '', '', '批量导出', NULL, NULL, 1, 'sys:user:batch:export', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (168, 2, '', '', '', '批量删除', NULL, NULL, 1, 'sys:user:batch:delete', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (174, 2, '', '', '', '删除用户', NULL, NULL, 1, 'sys:user:delete', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (175, 3, '', '', '', '新增角色', NULL, NULL, 1, 'sys:role:add', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (176, 3, '', '', '', '修改角色', NULL, NULL, 1, 'sys:role:update', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (177, 3, '', '', '', '删除角色', NULL, NULL, 1, 'sys:role:delete', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (178, 3, '', '', '', '批量删除', NULL, NULL, 1, 'sys:role:batch:delete', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (179, 3, '', '', '', '批量导出', NULL, NULL, 1, 'sys:role:batch:export', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (182, 4, '', '', '', '新增菜单', NULL, NULL, 1, 'sys:permission:add', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (183, 4, '', '', '', '修改菜单', NULL, NULL, 1, 'sys:permission:update', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (184, 4, '', '', '', '删除菜单', NULL, NULL, 1, 'sys:permission:delete', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (185, 4, '', '', '', '新增子级', NULL, NULL, 1, 'sys:permission:children:add', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (212, 1, '/system/file', '/file/index', 'Folder', '文件管理', '2023-10-11 17:26:42', '2023-10-11 17:27:50', 1, 'file:list', 1, 0, 2);
INSERT INTO `tb_permission` VALUES (229, 228, '', '', '', '新增', '2023-10-18 20:31:59', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (230, 228, '', '', '', '修改', '2023-10-18 20:31:59', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (231, 228, '', '', '', '批量导出', '2023-10-18 20:31:59', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (232, 228, '', '', '', '批量删除', '2023-10-18 20:31:59', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (233, 228, '', '', '', '批量导入', '2023-10-18 20:31:59', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (234, 228, '', '', '', '删除', '2023-10-18 20:31:59', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (236, 235, '', '', '', '新增', '2023-10-20 12:40:14', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (237, 235, '', '', '', '修改', '2023-10-20 12:40:14', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (238, 235, '', '', '', '删除', '2023-10-20 12:40:14', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (239, 235, '', '', '', '批量导出', '2023-10-20 12:40:14', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (240, 235, '', '', '', '批量删除', '2023-10-20 12:40:14', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (241, 235, '', '', '', '批量导入', '2023-10-20 12:40:14', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (243, 242, '', '', '', '新增', '2023-10-20 12:44:42', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (244, 242, '', '', '', '修改', '2023-10-20 12:44:42', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (245, 242, '', '', '', '删除', '2023-10-20 12:44:42', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (246, 242, '', '', '', '批量导出', '2023-10-20 12:44:42', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (247, 242, '', '', '', '批量删除', '2023-10-20 12:44:42', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (248, 242, '', '', '', '批量导入', '2023-10-20 12:44:42', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (250, 249, '', '', '', '新增', '2023-10-20 13:55:02', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (251, 249, '', '', '', '修改', '2023-10-20 13:55:02', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (252, 249, '', '', '', '删除', '2023-10-20 13:55:02', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (253, 249, '', '', '', '批量导出', '2023-10-20 13:55:02', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (254, 249, '', '', '', '批量删除', '2023-10-20 13:55:02', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (255, 249, '', '', '', '批量导入', '2023-10-20 13:55:02', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (257, 256, '', '', '', '新增', '2023-10-20 14:00:38', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (258, 256, '', '', '', '修改', '2023-10-20 14:00:38', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (259, 256, '', '', '', '删除', '2023-10-20 14:00:38', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (260, 256, '', '', '', '批量导出', '2023-10-20 14:00:38', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (261, 256, '', '', '', '批量删除', '2023-10-20 14:00:38', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (262, 256, '', '', '', '批量导入', '2023-10-20 14:00:38', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (264, 263, '', '', '', '新增', '2023-10-23 17:59:05', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (265, 263, '', '', '', '修改', '2023-10-23 17:59:05', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (266, 263, '', '', '', '删除', '2023-10-23 17:59:05', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (267, 263, '', '', '', '批量导出', '2023-10-23 17:59:05', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (268, 263, '', '', '', '批量删除', '2023-10-23 17:59:05', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (269, 263, '', '', '', '批量导入', '2023-10-23 17:59:05', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (271, 270, '', '', '', '新增', '2023-10-23 18:03:58', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (272, 270, '', '', '', '修改', '2023-10-23 18:03:58', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (273, 270, '', '', '', '删除', '2023-10-23 18:03:58', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (274, 270, '', '', '', '批量导出', '2023-10-23 18:03:58', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (275, 270, '', '', '', '批量删除', '2023-10-23 18:03:58', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (276, 270, '', '', '', '批量导入', '2023-10-23 18:03:58', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (278, 277, '', '', '', '新增', '2023-10-23 18:07:05', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (279, 277, '', '', '', '修改', '2023-10-23 18:07:05', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (280, 277, '', '', '', '删除', '2023-10-23 18:07:05', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (281, 277, '', '', '', '批量导出', '2023-10-23 18:07:05', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (282, 277, '', '', '', '批量删除', '2023-10-23 18:07:05', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (283, 277, '', '', '', '批量导入', '2023-10-23 18:07:05', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (285, 284, '', '', '', '新增', '2023-10-23 18:08:49', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (286, 284, '', '', '', '修改', '2023-10-23 18:08:49', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (287, 284, '', '', '', '删除', '2023-10-23 18:08:49', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (288, 284, '', '', '', '批量导出', '2023-10-23 18:08:49', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (289, 284, '', '', '', '批量删除', '2023-10-23 18:08:49', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (290, 284, '', '', '', '批量导入', '2023-10-23 18:08:49', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (292, 291, '', '', '', '新增', '2023-10-23 18:10:35', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (293, 291, '', '', '', '修改', '2023-10-23 18:10:35', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (294, 291, '', '', '', '删除', '2023-10-23 18:10:35', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (295, 291, '', '', '', '批量导出', '2023-10-23 18:10:35', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (296, 291, '', '', '', '批量删除', '2023-10-23 18:10:35', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (297, 291, '', '', '', '批量导入', '2023-10-23 18:10:35', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (299, 298, '', '', '', '新增', '2023-10-23 18:11:26', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (300, 298, '', '', '', '修改', '2023-10-23 18:11:26', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (301, 298, '', '', '', '删除', '2023-10-23 18:11:26', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (302, 298, '', '', '', '批量导出', '2023-10-23 18:11:26', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (303, 298, '', '', '', '批量删除', '2023-10-23 18:11:26', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (304, 298, '', '', '', '批量导入', '2023-10-23 18:11:26', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (306, 305, '', '', '', '新增', '2023-10-23 18:17:28', NULL, 1, 'sys:li:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (307, 305, '', '', '', '修改', '2023-10-23 18:17:28', NULL, 1, 'sys:li:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (308, 305, '', '', '', '删除', '2023-10-23 18:17:28', NULL, 1, 'sys:li:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (309, 305, '', '', '', '批量导出', '2023-10-23 18:17:28', NULL, 1, 'sys:li:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (310, 305, '', '', '', '批量删除', '2023-10-23 18:17:28', NULL, 1, 'sys:li:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (311, 305, '', '', '', '批量导入', '2023-10-23 18:17:28', NULL, 1, 'sys:li:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (347, 1, '/notice', '/notice/index', 'MessageBox', '系统公告', '2023-10-24 20:02:09', '2023-11-20 21:02:05', 1, 'sys:notice:list', 99, 0, 2);
INSERT INTO `tb_permission` VALUES (348, 347, '', '', '', '新增', '2023-10-24 20:02:09', NULL, 1, 'sys:notice:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (349, 347, '', '', '', '修改', '2023-10-24 20:02:09', NULL, 1, 'sys:notice:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (350, 347, '', '', '', '删除', '2023-10-24 20:02:09', NULL, 1, 'sys:notice:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (351, 347, '', '', '', '批量导出', '2023-10-24 20:02:09', NULL, 1, 'sys:notice:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (352, 347, '', '', '', '批量删除', '2023-10-24 20:02:09', NULL, 1, 'sys:notice:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (353, 347, '', '', '', '批量导入', '2023-10-24 20:02:09', NULL, 1, 'sys:notice:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (355, 354, '', '', '', '新增', '2023-11-16 23:08:48', NULL, 1, 'sys:log:add', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (356, 354, '', '', '', '修改', '2023-11-16 23:08:48', NULL, 1, 'sys:log:update', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (357, 354, '', '', '', '删除', '2023-11-16 23:08:48', NULL, 1, 'sys:log:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (358, 354, '', '', '', '批量导出', '2023-11-16 23:08:48', NULL, 1, 'sys:log:batch:export', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (359, 354, '', '', '', '批量删除', '2023-11-16 23:08:48', NULL, 1, 'sys:log:batch:delete', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (360, 354, '', '', '', '批量导入', '2023-11-16 23:08:48', NULL, 1, 'sys:log:batch:upload', 99, 0, 3);
INSERT INTO `tb_permission` VALUES (510, 0, '/business', '', 'OfficeBuilding', '业务管理', '2025-01-06 10:55:44', '2023-09-07 11:34:18', 1, 'business', 99, 0, 1);
INSERT INTO `tb_permission` VALUES (511, 510, '/busniess/detail', '/order/detail/index', 'Memo', '工单详情', '2025-01-06 11:07:52', '2025-01-06 15:38:40', 1, 'biz:detail', 1, 0, 2);
INSERT INTO `tb_permission` VALUES (512, 510, '/business/list', '/order/list/index', 'Menu', '工单列表', '2025-01-06 16:36:45', NULL, 1, 'biz:list', 1, 0, 2);
INSERT INTO `tb_permission` VALUES (513, 510, '/business/submit', '/order/submit/index', 'Menu', '工单申请', '2025-01-06 18:25:34', '2025-01-06 19:29:36', 1, 'biz:submit', 1, 0, 2);

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `perms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '唯一标识',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` int(0) NULL DEFAULT 1 COMMENT '当前状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, '超级管理员', '拥有全部权限', 'admin', '2023-01-22 11:40:31', '2025-01-06 18:26:04', 1);
INSERT INTO `tb_role` VALUES (6, '普通用户', '拥有部分权限', 'user', '2023-02-01 17:32:11', '2025-01-05 22:55:30', 1);

-- ----------------------------
-- Table structure for tb_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permission`;
CREATE TABLE `tb_role_permission`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(0) NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4593 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_permission
-- ----------------------------
INSERT INTO `tb_role_permission` VALUES (4501, 6, 2);
INSERT INTO `tb_role_permission` VALUES (4502, 6, 167);
INSERT INTO `tb_role_permission` VALUES (4503, 6, 3);
INSERT INTO `tb_role_permission` VALUES (4504, 6, 179);
INSERT INTO `tb_role_permission` VALUES (4505, 6, 212);
INSERT INTO `tb_role_permission` VALUES (4506, 6, 1);
INSERT INTO `tb_role_permission` VALUES (4564, 1, 1);
INSERT INTO `tb_role_permission` VALUES (4565, 1, 2);
INSERT INTO `tb_role_permission` VALUES (4566, 1, 165);
INSERT INTO `tb_role_permission` VALUES (4567, 1, 166);
INSERT INTO `tb_role_permission` VALUES (4568, 1, 167);
INSERT INTO `tb_role_permission` VALUES (4569, 1, 168);
INSERT INTO `tb_role_permission` VALUES (4570, 1, 174);
INSERT INTO `tb_role_permission` VALUES (4571, 1, 3);
INSERT INTO `tb_role_permission` VALUES (4572, 1, 175);
INSERT INTO `tb_role_permission` VALUES (4573, 1, 176);
INSERT INTO `tb_role_permission` VALUES (4574, 1, 177);
INSERT INTO `tb_role_permission` VALUES (4575, 1, 178);
INSERT INTO `tb_role_permission` VALUES (4576, 1, 179);
INSERT INTO `tb_role_permission` VALUES (4577, 1, 4);
INSERT INTO `tb_role_permission` VALUES (4578, 1, 182);
INSERT INTO `tb_role_permission` VALUES (4579, 1, 183);
INSERT INTO `tb_role_permission` VALUES (4580, 1, 184);
INSERT INTO `tb_role_permission` VALUES (4581, 1, 185);
INSERT INTO `tb_role_permission` VALUES (4582, 1, 212);
INSERT INTO `tb_role_permission` VALUES (4583, 1, 347);
INSERT INTO `tb_role_permission` VALUES (4584, 1, 348);
INSERT INTO `tb_role_permission` VALUES (4585, 1, 349);
INSERT INTO `tb_role_permission` VALUES (4586, 1, 350);
INSERT INTO `tb_role_permission` VALUES (4587, 1, 351);
INSERT INTO `tb_role_permission` VALUES (4588, 1, 352);
INSERT INTO `tb_role_permission` VALUES (4589, 1, 353);
INSERT INTO `tb_role_permission` VALUES (4590, 1, 510);
INSERT INTO `tb_role_permission` VALUES (4591, 1, 511);
INSERT INTO `tb_role_permission` VALUES (4592, 1, 512);
INSERT INTO `tb_role_permission` VALUES (4593, 1, 513);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` bigint(0) NULL DEFAULT 0 COMMENT '当前状态',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '这家伙真懒，什么都没留下!' COMMENT '个人简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 124 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'admin', '$2a$10$jQRE3dMPkLvoH5jh0j3ebe0sQkvXViWX20vgDzeKWIqd7gZfyW30W', '管理员', 'http://localhost:90/api/file/99b1dc604ee146d98ef0179e46ef42e2.png', '2023-02-07 00:00:00', '2025-01-06 10:47:16', 1, '17772113398', '18237613715@163.com', '这家伙很懒，什么都没有留下！');
INSERT INTO `tb_user` VALUES (123, 'testUser', '$2a$10$BwG2RZsrF4Z/4J8SmemmteGTVIYGkVdGh0Pea0GQZwdtyDaF5QFd2', '测试用户1', 'http://localhost:90/api/file/09ab762316b1413f8de7af70b84ed3fe.jpg', '2025-01-05 16:03:48', '2025-01-05 23:34:24', 1, NULL, NULL, '这家伙真懒，什么都没留下!');
INSERT INTO `tb_user` VALUES (124, '34434', '$2a$10$UGWtKd0lAvNgzQDUBDAXzOfeh6/.MubUY7e39FlVqvcRHaIAAbgGK', '23434', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '2025-01-06 10:49:20', NULL, 1, NULL, NULL, '这家伙真懒，什么都没留下!');

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES (64, 1, 1);
INSERT INTO `tb_user_role` VALUES (67, 123, 6);
INSERT INTO `tb_user_role` VALUES (68, 124, 6);

SET FOREIGN_KEY_CHECKS = 1;
