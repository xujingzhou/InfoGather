/*
 Navicat Premium Data Transfer

 Source Server         : 18.162.113.79(3306)
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 18.162.113.79:3306
 Source Schema         : education

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 05/03/2021 09:34:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE `education` CHARACTER SET 'utf8mb4';

-- ----------------------------
-- Table structure for punching_hole
-- ----------------------------
DROP TABLE IF EXISTS `punching_hole`;
CREATE TABLE `punching_hole`  (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `self_id` bigint(11) UNSIGNED ZEROFILL NOT NULL COMMENT '自身ID',
  `self_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '自身名称',
  `self_nat_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '自身NAT类型',
  `peer_id` bigint(11) UNSIGNED NULL DEFAULT NULL COMMENT '对端ID',
  `peer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '对端名称',
  `peer_nat_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '对端NAT类型',
  `ice_connection_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '打洞状态',
  `gmt_create` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `del_flag` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除(0:未删除  1:已删除)',
  PRIMARY KEY (`id`, `self_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of punching_hole
-- ----------------------------
INSERT INTO `punching_hole` VALUES (00000000008, 00000123456, 'tester', 'Port Restricted Cone', 2345678, 'tester1', '', 'completed', '2021-03-03 12:15:07', '2021-03-04 05:57:24', 0);
INSERT INTO `punching_hole` VALUES (00000000009, 00000023456, 'johnny', 'Restricted Cone', 345678, 'xujingzhou', '', 'completed', '2021-03-03 12:16:04', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
