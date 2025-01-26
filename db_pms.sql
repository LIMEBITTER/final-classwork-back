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

 Date: 27/01/2025 00:17:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_comeback
-- ----------------------------
DROP TABLE IF EXISTS `tb_comeback`;
CREATE TABLE `tb_comeback`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容	',
  `user_id` int(0) NOT NULL COMMENT '评论人id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论人姓名	',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间	',
  `is_delete` tinyint(0) NULL DEFAULT 0 COMMENT '是否已删除	',
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属工单id',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父评论id',
  `root_parent_id` int(0) NULL DEFAULT NULL COMMENT '根评论id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_comeback
-- ----------------------------
INSERT INTO `tb_comeback` VALUES (22, '一级评论', 1, '管理员', '2025-01-12 18:58:24', 0, 'WF20250108112016VLMX', NULL, NULL);
INSERT INTO `tb_comeback` VALUES (23, '一级评论2', 1, '管理员', '2025-01-12 18:58:39', 0, 'WF20250108112016VLMX', NULL, NULL);
INSERT INTO `tb_comeback` VALUES (24, '^_^哈哈哈哈哈哈哈哈哈', 1, '管理员', '2025-01-12 18:58:58', 0, 'WF20250108112016VLMX', 23, 23);
INSERT INTO `tb_comeback` VALUES (25, 'o(*￣︶￣*)o呵呵呵呵呵呵呵呵呵呵', 125, '张师傅', '2025-01-12 18:59:45', 0, 'WF20250108112016VLMX', 24, 23);
INSERT INTO `tb_comeback` VALUES (26, '大公司电饭锅电饭锅发的更大', 125, '张师傅', '2025-01-12 19:00:27', 0, 'WF20250108112016VLMX', 22, 22);

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
INSERT INTO `tb_detail` VALUES (-1398779903, 'WF20250109210225YODF', NULL);
INSERT INTO `tb_detail` VALUES (-253743103, 'WF20250108112016VLMX', NULL);
INSERT INTO `tb_detail` VALUES (715165698, 'WF20250115202754DPAP', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1027 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件上传' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_file
-- ----------------------------
INSERT INTO `tb_file` VALUES (1024, 'R.jpg', 'jpg', 38, 1, 'http://localhost:90/api/file/09ab762316b1413f8de7af70b84ed3fe.jpg', '528cf5fb9b9315327bb3c7f299352162', 1);
INSERT INTO `tb_file` VALUES (1025, 'R.jpg', 'jpg', 38, 1, 'http://localhost:90/api/file/09ab762316b1413f8de7af70b84ed3fe.jpg', '528cf5fb9b9315327bb3c7f299352162', 1);
INSERT INTO `tb_file` VALUES (1026, 'R.jpg', 'jpg', 38, 1, 'http://localhost:90/api/file/09ab762316b1413f8de7af70b84ed3fe.jpg', '528cf5fb9b9315327bb3c7f299352162', 0);
INSERT INTO `tb_file` VALUES (1027, 'a.png', 'png', 1500, 1, 'http://localhost:90/api/file/d796f6d97d0244ff91a56e1bddb0bf05.png', '69a81eb8c8d3d7887a1726e9857a1ed2', 0);

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message`  (
  `id` int(0) NOT NULL,
  `send_user` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `receive_user` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_read` tinyint(1) NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `room_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_message
-- ----------------------------
INSERT INTO `tb_message` VALUES (-2107621375, '123', '1', '9999', 1, '2025-01-26 23:44:58', NULL);
INSERT INTO `tb_message` VALUES (-2090844159, '123', '1', '官方公告干饭干饭', 1, '2025-01-26 23:49:18', NULL);
INSERT INTO `tb_message` VALUES (-2027929598, '1', '123', '45353545', 1, '2025-01-26 23:21:27', NULL);
INSERT INTO `tb_message` VALUES (-1809825790, '123', '1', '斤斤计较', 1, '2025-01-26 23:32:35', NULL);
INSERT INTO `tb_message` VALUES (-1797242879, '123', '1', '给个灌灌灌灌灌', 1, '2025-01-26 23:50:10', NULL);
INSERT INTO `tb_message` VALUES (-1650442238, '123', '1', '快快乐乐', 1, '2025-01-27 00:03:24', NULL);
INSERT INTO `tb_message` VALUES (-1516224511, '1', '123', '1111000', 1, '2025-01-26 23:45:09', NULL);
INSERT INTO `tb_message` VALUES (-1482670079, '123', '1', '好好好', 1, '2025-01-27 00:05:51', NULL);
INSERT INTO `tb_message` VALUES (-1453309951, '1', '123', '5555嚞', 1, '2025-01-27 00:15:20', NULL);
INSERT INTO `tb_message` VALUES (-1344258046, '123', '1', '铁铁铁', 1, '2025-01-27 00:08:47', NULL);
INSERT INTO `tb_message` VALUES (-1264566270, '1', '123', '反反复复付', 1, '2025-01-26 23:32:15', NULL);
INSERT INTO `tb_message` VALUES (-1222623230, '123', '1', '?????heiheihei', 1, '2025-01-26 22:57:56', NULL);
INSERT INTO `tb_message` VALUES (-1063239679, '123', '1', '8888888888', 1, '2025-01-26 23:51:59', NULL);
INSERT INTO `tb_message` VALUES (-1029685247, '125', '1', '啊啊啊啊啊，可恶，你个老六', 1, '2025-01-26 22:46:09', NULL);
INSERT INTO `tb_message` VALUES (-685752318, '123', '1', '444444', 1, '2025-01-26 23:28:36', NULL);
INSERT INTO `tb_message` VALUES (-618643454, '1', '125', '张三啊，我是超级管理员啊', 1, '2025-01-26 22:38:26', NULL);
INSERT INTO `tb_message` VALUES (-597671935, '1', '123', '反反复复', 1, '2025-01-27 00:09:28', NULL);
INSERT INTO `tb_message` VALUES (-559923199, '1', '123', '反反复复凤飞飞', 1, '2025-01-26 23:33:16', NULL);
INSERT INTO `tb_message` VALUES (-413122559, '123', '1', 'HHHHHH', 1, '2025-01-26 23:36:30', NULL);
INSERT INTO `tb_message` VALUES (-262127614, '123', '1', '7777', 1, '2025-01-26 23:43:44', NULL);
INSERT INTO `tb_message` VALUES (-207601662, '123', '1', '666666', 1, '2025-01-26 23:53:12', NULL);
INSERT INTO `tb_message` VALUES (-123715582, '123', '1', '34', 1, '2025-01-27 00:10:52', NULL);
INSERT INTO `tb_message` VALUES (10502146, '1', '125', '你个牛魔，我是真的服了', 1, '2025-01-26 22:39:17', NULL);
INSERT INTO `tb_message` VALUES (77611009, '1', '123', '反反复复反反复复大幅度发的奋斗奋斗', 1, '2025-01-26 23:37:48', NULL);
INSERT INTO `tb_message` VALUES (169885698, '123', '1', '交换机解决', 1, '2025-01-27 00:09:32', NULL);
INSERT INTO `tb_message` VALUES (257966082, '1', '123', 'JKJJJJJJJ', 1, '2025-01-26 23:36:58', NULL);
INSERT INTO `tb_message` VALUES (291520513, '123', '1', '哈哈哈哈', 1, '2025-01-27 00:04:28', NULL);
INSERT INTO `tb_message` VALUES (333463554, '1', '123', '333333', 1, '2025-01-26 23:30:10', NULL);
INSERT INTO `tb_message` VALUES (387989505, '1', '123', '贩夫贩妇菲菲王企鹅外人田其他', 1, '2025-01-26 23:40:58', NULL);
INSERT INTO `tb_message` VALUES (438321154, '123', '1', '我辅导费多方的发到发到的', 1, '2025-01-26 23:30:39', NULL);
INSERT INTO `tb_message` VALUES (543178754, '125', '1', '超级管理员？我打的就是admin', 1, '2025-01-26 22:38:55', NULL);
INSERT INTO `tb_message` VALUES (580927489, '1', '125', '哈哈哈哈哈哈，可恶的张三，去死把', 1, '2025-01-26 22:42:33', NULL);
INSERT INTO `tb_message` VALUES (643842049, '123', '1', '11111223434332', 1, '2025-01-26 23:17:04', NULL);
INSERT INTO `tb_message` VALUES (975192066, '1', '123', '3434434', 1, '2025-01-26 23:35:34', NULL);
INSERT INTO `tb_message` VALUES (1025523713, '123', '1', '4646475868', 1, '2025-01-26 23:59:08', NULL);
INSERT INTO `tb_message` VALUES (1038106626, '123', '1', '哈哈哈哈哈，天无绝人之路！', 1, '2025-01-26 22:40:09', NULL);
INSERT INTO `tb_message` VALUES (1063272449, '123', '1', '合同章然后先富带后富回复下很尬回归', 1, '2025-01-27 00:00:34', NULL);
INSERT INTO `tb_message` VALUES (1071661058, '1', '123', '333333', 1, '2025-01-26 23:30:25', NULL);
INSERT INTO `tb_message` VALUES (1084243970, '1', '123', '344343', 1, '2025-01-27 00:08:55', NULL);
INSERT INTO `tb_message` VALUES (1172324354, '1', '123', '11111', 1, '2025-01-26 23:10:38', NULL);
INSERT INTO `tb_message` VALUES (1268793346, '1', '123', '卡洛琳来了', 1, '2025-01-26 23:58:29', NULL);
INSERT INTO `tb_message` VALUES (1428176897, '1', '123', '测试用户啊，我是超级管理员啊', 1, '2025-01-26 22:39:43', NULL);
INSERT INTO `tb_message` VALUES (1507868673, '123', '1', '哼哼哼啊啊啊啊啊啊啊', 1, '2025-01-26 23:05:06', NULL);
INSERT INTO `tb_message` VALUES (1524645890, '123', '1', '呵呵', 1, '2025-01-27 00:02:24', NULL);
INSERT INTO `tb_message` VALUES (1709195265, '123', '1', '灌灌灌灌灌', 1, '2025-01-26 23:33:10', NULL);
INSERT INTO `tb_message` VALUES (1759526913, '123', '1', '5555', 1, '2025-01-27 00:15:12', NULL);
INSERT INTO `tb_message` VALUES (1843412994, '123', '1', '人呢，admin?????', 1, '2025-01-26 22:48:18', NULL);
INSERT INTO `tb_message` VALUES (1914716162, '1', '123', '88888', 1, '2025-01-26 23:43:50', NULL);
INSERT INTO `tb_message` VALUES (1981825026, '123', '1', '看看', 1, '2025-01-27 00:05:23', NULL);
INSERT INTO `tb_message` VALUES (2027962369, '123', '1', '发个发广告', 1, '2025-01-26 23:18:30', NULL);
INSERT INTO `tb_message` VALUES (2048933890, '123', '1', '333', 1, '2025-01-27 00:13:37', NULL);
INSERT INTO `tb_message` VALUES (2141208577, '123', '1', '发你妈呢', 1, '2025-01-26 23:22:35', NULL);

-- ----------------------------
-- Table structure for tb_notice
-- ----------------------------
DROP TABLE IF EXISTS `tb_notice`;
CREATE TABLE `tb_notice`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `publish_status` int(0) NULL DEFAULT 0 COMMENT '公告发布状态 0未发布 1已发布',
  `publisher_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布公告者姓名',
  `level` int(0) NULL DEFAULT NULL COMMENT '公告等级 1低2中3高',
  `target_type` int(0) NULL DEFAULT NULL COMMENT '公告指向角色 0全体1指定',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统公告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_notice
-- ----------------------------
INSERT INTO `tb_notice` VALUES (26, 'admin通知1', 1, 'admin', 2, 1, '2025-01-15 22:30:50', '2025-01-15 22:31:39', '1111111111111111111111');
INSERT INTO `tb_notice` VALUES (27, '普通-维修', 1, 'admin', 3, 1, '2025-01-15 22:31:09', '2025-01-15 22:34:47', '22222222222222222');
INSERT INTO `tb_notice` VALUES (28, '全体', 1, 'admin', 1, 0, '2025-01-15 22:31:26', '2025-01-15 22:34:42', '33333333333333333333333333333333');

-- ----------------------------
-- Table structure for tb_notice_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_notice_role`;
CREATE TABLE `tb_notice_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_id` int(0) NULL DEFAULT NULL,
  `notice_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2028040195 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_notice_role
-- ----------------------------
INSERT INTO `tb_notice_role` VALUES (-1394511870, 1, 26);
INSERT INTO `tb_notice_role` VALUES (778137601, 1, 28);
INSERT INTO `tb_notice_role` VALUES (778137602, 6, 28);
INSERT INTO `tb_notice_role` VALUES (778137603, 7, 28);
INSERT INTO `tb_notice_role` VALUES (1495306241, 1, 20);
INSERT INTO `tb_notice_role` VALUES (1495306242, 6, 20);
INSERT INTO `tb_notice_role` VALUES (1935765506, 6, 27);
INSERT INTO `tb_notice_role` VALUES (1935765507, 7, 27);

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `priority` tinyint(1) NULL DEFAULT NULL COMMENT '当前工单优先级',
  `related_person` int(0) NULL DEFAULT NULL COMMENT '当前流转到的处理人',
  `state` int(0) NULL DEFAULT NULL COMMENT '工单当前状态',
  `creator_id` int(0) NULL DEFAULT NULL COMMENT '提交工单的人',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `detail_id` int(0) NULL DEFAULT NULL COMMENT '当前工单的详细信息',
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `complaint` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 648056833 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES (-1621073918, '测试工单12121212', 2, 1, 3, 1, '2025-01-06 21:12:56', '2025-01-09 20:28:22', NULL, 'WF20250106211247AQRH', '31231232132321313123');
INSERT INTO `tb_order` VALUES (-1415557119, '测试工单-testUser222', 1, 1, 1, 123, '2025-01-09 21:02:43', NULL, NULL, 'WF20250109210225YODF', '测试测试测试');
INSERT INTO `tb_order` VALUES (-274714623, '普通用户测试工单111', 3, 1, 5, 123, '2025-01-08 11:20:36', '2025-01-10 15:42:35', NULL, 'WF20250108112016VLMX', '玩儿玩儿玩儿玩儿玩儿仍然围绕二二二二娃放到');
INSERT INTO `tb_order` VALUES (648056833, '发生大范德萨范德萨发的', 1, 1, 3, 1, '2025-01-15 20:28:01', '2025-01-15 20:29:27', NULL, 'WF20250115202754DPAP', '发大水发大幅度发的说法都发发');

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
INSERT INTO `tb_order_history` VALUES (-1562316799, '2025-01-09 15:35:50', 'WF20250108112016VLMX', 1, NULL, '分配工单', 'admin', 'admin分配给张师傅');
INSERT INTO `tb_order_history` VALUES (-1398779902, '2025-01-09 21:02:43', 'WF20250109210225YODF', 1, 1, '提交工单', 'admin', NULL);
INSERT INTO `tb_order_history` VALUES (-1025454079, '2025-01-10 15:42:35', 'WF20250108112016VLMX', 3, NULL, '结束工单', '李师傅', '李师傅结束当前工单');
INSERT INTO `tb_order_history` VALUES (-744468478, '2025-01-09 20:22:40', 'WF20250106211247AQRH', 1, NULL, '管理员审核', 'admin', '同意处理哈');
INSERT INTO `tb_order_history` VALUES (-689942527, '2025-01-09 20:28:22', 'WF20250106211247AQRH', 2, NULL, '转交工单', '张师傅', '张师傅转交给郭靖');
INSERT INTO `tb_order_history` VALUES (-505376766, '2025-01-09 20:01:37', 'WF20250108112016VLMX', 2, NULL, '转交工单', '李师傅', '李师傅转交给郭靖');
INSERT INTO `tb_order_history` VALUES (-350187519, '2025-01-15 20:28:49', 'WF20250115202754DPAP', 1, NULL, '管理员审核', 'admin', '235423423434442');
INSERT INTO `tb_order_history` VALUES (-253743102, '2025-01-08 11:20:36', 'WF20250108112016VLMX', 1, 123, '提交工单', 'testUser', NULL);
INSERT INTO `tb_order_history` VALUES (-94306302, '2025-01-10 15:14:59', 'WF20250108112016VLMX', 1, NULL, '接受工单', '李师傅', '李师傅接受当前工单');
INSERT INTO `tb_order_history` VALUES (715165699, '2025-01-15 20:28:01', 'WF20250115202754DPAP', 1, 1, '提交工单', 'admin', NULL);
INSERT INTO `tb_order_history` VALUES (811618307, '2025-01-09 20:23:02', 'WF20250106211247AQRH', 1, NULL, '分配工单', 'admin', 'admin分配给张师傅');
INSERT INTO `tb_order_history` VALUES (971001858, '2025-01-09 20:29:20', 'WF20250108112016VLMX', 2, NULL, '转交工单', '郭靖', '郭靖转交给李师傅');
INSERT INTO `tb_order_history` VALUES (1277210626, '2025-01-08 16:55:03', 'WF20250108112016VLMX', 1, NULL, '管理员审核', 'admin', '同意');
INSERT INTO `tb_order_history` VALUES (1927319554, '2025-01-15 20:29:27', 'WF20250115202754DPAP', 1, NULL, '分配工单', 'admin', 'admin分配给李师傅');
INSERT INTO `tb_order_history` VALUES (2032177153, '2025-01-09 19:58:07', 'WF20250108112016VLMX', 2, NULL, '转交工单', '张师傅', '张师傅转交给李师傅');

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
) ENGINE = InnoDB AUTO_INCREMENT = 518 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

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
INSERT INTO `tb_permission` VALUES (347, 1, '/system/notice', '/notice/index', 'MessageBox', '公告管理', '2023-10-24 20:02:09', '2025-01-12 14:24:24', 1, 'sys:notice:list', 99, 0, 2);
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
INSERT INTO `tb_permission` VALUES (511, 510, '/busniess/detail', '/order/detail/index', 'Memo', '工单详情', '2025-01-06 11:07:52', '2025-01-08 15:38:24', 1, 'biz:detail', 1, 1, 2);
INSERT INTO `tb_permission` VALUES (512, 510, '/business/list', '/order/list/index', 'Menu', '工单列表', '2025-01-06 16:36:45', NULL, 1, 'biz:list', 1, 0, 2);
INSERT INTO `tb_permission` VALUES (513, 510, '/business/submit', '/order/submit/index', 'Menu', '工单申请', '2025-01-06 18:25:34', '2025-01-06 19:29:36', 1, 'biz:submit', 1, 0, 2);
INSERT INTO `tb_permission` VALUES (514, 511, '', '', '', '管理员审核', '2025-01-08 11:31:07', NULL, 1, 'biz:detail:audit', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (515, 511, '', '', '', '维修员接单', '2025-01-08 20:39:10', NULL, 1, 'biz:detail:acc-rej-order', 1, 0, 3);
INSERT INTO `tb_permission` VALUES (516, 1, '/system/notice/detail', '/notice/detail/index', 'Printer', '查看公告详情', '2025-01-15 15:33:05', NULL, 1, 'sys:notice:detail', 1, 0, 2);
INSERT INTO `tb_permission` VALUES (517, 0, '/chat', '', 'ChatLineSquare', '在线聊天', '2025-01-16 11:13:13', NULL, 1, 'chat', 1, 0, 1);
INSERT INTO `tb_permission` VALUES (518, 517, '/chat/online', '/chatroom/index', 'CoffeeCup', '聊天', '2025-01-16 11:14:43', NULL, 1, 'chat:online', 1, 0, 2);
INSERT INTO `tb_permission` VALUES (519, 517, '/chatroom/test', '/chatroom/test/index', 'Menu', '聊天测试1', '2025-01-26 14:05:03', NULL, 1, 'chat:test', 1, 0, 2);

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, '超级管理员', '拥有全部权限', 'admin', '2023-01-22 11:40:31', '2025-01-26 14:05:10', 1);
INSERT INTO `tb_role` VALUES (6, '普通用户', '拥有部分权限', 'user', '2023-02-01 17:32:11', '2025-01-26 20:19:52', 1);
INSERT INTO `tb_role` VALUES (7, '维修人员', '负责维修的人员，拥有部分权限', 'serviceman', '2025-01-08 19:19:13', '2025-01-26 22:17:58', 1);

-- ----------------------------
-- Table structure for tb_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permission`;
CREATE TABLE `tb_role_permission`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(0) NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4756 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_permission
-- ----------------------------
INSERT INTO `tb_role_permission` VALUES (4757, 1, 517);
INSERT INTO `tb_role_permission` VALUES (4758, 1, 518);
INSERT INTO `tb_role_permission` VALUES (4759, 1, 519);
INSERT INTO `tb_role_permission` VALUES (4760, 1, 1);
INSERT INTO `tb_role_permission` VALUES (4761, 1, 2);
INSERT INTO `tb_role_permission` VALUES (4762, 1, 165);
INSERT INTO `tb_role_permission` VALUES (4763, 1, 166);
INSERT INTO `tb_role_permission` VALUES (4764, 1, 167);
INSERT INTO `tb_role_permission` VALUES (4765, 1, 168);
INSERT INTO `tb_role_permission` VALUES (4766, 1, 174);
INSERT INTO `tb_role_permission` VALUES (4767, 1, 3);
INSERT INTO `tb_role_permission` VALUES (4768, 1, 175);
INSERT INTO `tb_role_permission` VALUES (4769, 1, 176);
INSERT INTO `tb_role_permission` VALUES (4770, 1, 177);
INSERT INTO `tb_role_permission` VALUES (4771, 1, 178);
INSERT INTO `tb_role_permission` VALUES (4772, 1, 179);
INSERT INTO `tb_role_permission` VALUES (4773, 1, 4);
INSERT INTO `tb_role_permission` VALUES (4774, 1, 182);
INSERT INTO `tb_role_permission` VALUES (4775, 1, 183);
INSERT INTO `tb_role_permission` VALUES (4776, 1, 184);
INSERT INTO `tb_role_permission` VALUES (4777, 1, 185);
INSERT INTO `tb_role_permission` VALUES (4778, 1, 212);
INSERT INTO `tb_role_permission` VALUES (4779, 1, 516);
INSERT INTO `tb_role_permission` VALUES (4780, 1, 347);
INSERT INTO `tb_role_permission` VALUES (4781, 1, 348);
INSERT INTO `tb_role_permission` VALUES (4782, 1, 349);
INSERT INTO `tb_role_permission` VALUES (4783, 1, 350);
INSERT INTO `tb_role_permission` VALUES (4784, 1, 351);
INSERT INTO `tb_role_permission` VALUES (4785, 1, 352);
INSERT INTO `tb_role_permission` VALUES (4786, 1, 353);
INSERT INTO `tb_role_permission` VALUES (4787, 1, 510);
INSERT INTO `tb_role_permission` VALUES (4788, 1, 511);
INSERT INTO `tb_role_permission` VALUES (4789, 1, 514);
INSERT INTO `tb_role_permission` VALUES (4790, 1, 515);
INSERT INTO `tb_role_permission` VALUES (4791, 1, 512);
INSERT INTO `tb_role_permission` VALUES (4792, 1, 513);
INSERT INTO `tb_role_permission` VALUES (4793, 6, 517);
INSERT INTO `tb_role_permission` VALUES (4794, 6, 518);
INSERT INTO `tb_role_permission` VALUES (4795, 6, 519);
INSERT INTO `tb_role_permission` VALUES (4796, 6, 1);
INSERT INTO `tb_role_permission` VALUES (4797, 6, 2);
INSERT INTO `tb_role_permission` VALUES (4798, 6, 167);
INSERT INTO `tb_role_permission` VALUES (4799, 6, 3);
INSERT INTO `tb_role_permission` VALUES (4800, 6, 179);
INSERT INTO `tb_role_permission` VALUES (4801, 6, 212);
INSERT INTO `tb_role_permission` VALUES (4802, 6, 510);
INSERT INTO `tb_role_permission` VALUES (4803, 6, 511);
INSERT INTO `tb_role_permission` VALUES (4804, 6, 512);
INSERT INTO `tb_role_permission` VALUES (4805, 6, 513);
INSERT INTO `tb_role_permission` VALUES (4806, 7, 519);
INSERT INTO `tb_role_permission` VALUES (4807, 7, 510);
INSERT INTO `tb_role_permission` VALUES (4808, 7, 511);
INSERT INTO `tb_role_permission` VALUES (4809, 7, 515);
INSERT INTO `tb_role_permission` VALUES (4810, 7, 512);
INSERT INTO `tb_role_permission` VALUES (4811, 7, 513);
INSERT INTO `tb_role_permission` VALUES (4812, 7, 517);

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
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'admin', '$2a$10$jQRE3dMPkLvoH5jh0j3ebe0sQkvXViWX20vgDzeKWIqd7gZfyW30W', '管理员', 'http://localhost:90/api/file/d796f6d97d0244ff91a56e1bddb0bf05.png', '2023-02-07 00:00:00', '2025-01-12 16:19:38', 1, '17772113398', '18237613715@163.com', '这家伙很懒，什么都没有留下！');
INSERT INTO `tb_user` VALUES (123, 'testUser', '$2a$10$BwG2RZsrF4Z/4J8SmemmteGTVIYGkVdGh0Pea0GQZwdtyDaF5QFd2', '测试用户1', 'http://localhost:90/api/file/09ab762316b1413f8de7af70b84ed3fe.jpg', '2025-01-05 16:03:48', '2025-01-05 23:34:24', 1, NULL, NULL, '这家伙真懒，什么都没留下!');
INSERT INTO `tb_user` VALUES (125, 'zhangsan', '$2a$10$qwBeEbIHaa4Tc48OW/4X3e9E.6a9Iu7iJoAkfoJZzzZgineTlk/8q', '张师傅', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '2025-01-08 19:19:55', '2025-01-09 18:21:59', 1, '19988887765', 'zhu@163.com', '这家伙真懒，什么都没留下!');
INSERT INTO `tb_user` VALUES (126, 'lisia', '$2a$10$LLrdcUDy4BkMhk1ynbCpcu0l00AUfq/uAX/RbocR/TldVDUkxq33S', '李师傅', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '2025-01-08 19:20:13', NULL, 1, NULL, NULL, '这家伙真懒，什么都没留下!');
INSERT INTO `tb_user` VALUES (127, 'guojing', '$2a$10$lD78pt7.9rGlkLqi8VAfSuIiFwfdRjIWTFyP2QBiuEK7Pn7egSsQa', '郭靖', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '2025-01-09 20:01:09', NULL, 1, NULL, NULL, '这家伙真懒，什么都没留下!');

-- ----------------------------
-- Table structure for tb_user_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_order`;
CREATE TABLE `tb_user_order`  (
  `id` int(0) NOT NULL,
  `alloc_user_id` int(0) NULL DEFAULT NULL,
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_order
-- ----------------------------
INSERT INTO `tb_user_order` VALUES (-1629425663, 126, 'WF20250108112016VLMX');
INSERT INTO `tb_user_order` VALUES (811618306, 127, 'WF20250106211247AQRH');
INSERT INTO `tb_user_order` VALUES (1927319553, 126, 'WF20250115202754DPAP');

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES (67, 123, 6);
INSERT INTO `tb_user_role` VALUES (69, 125, 7);
INSERT INTO `tb_user_role` VALUES (70, 126, 7);
INSERT INTO `tb_user_role` VALUES (71, 127, 7);
INSERT INTO `tb_user_role` VALUES (75, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
