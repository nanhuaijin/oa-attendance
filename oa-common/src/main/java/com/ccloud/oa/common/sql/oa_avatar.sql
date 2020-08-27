/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : oa_attendance

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 27/08/2020 12:36:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oa_avatar
-- ----------------------------
DROP TABLE IF EXISTS `oa_avatar`;
CREATE TABLE `oa_avatar` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(255) DEFAULT NULL COMMENT '用户账号',
  `avatar` varchar(255) DEFAULT 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif' COMMENT '用户头像地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户头像表';

-- ----------------------------
-- Records of oa_avatar
-- ----------------------------
BEGIN;
INSERT INTO `oa_avatar` VALUES (1, 'admin', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '2020-08-27 12:14:16', '2020-08-27 12:14:16');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
