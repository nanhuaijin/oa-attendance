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

 Date: 27/08/2020 12:35:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oa_role
-- ----------------------------
DROP TABLE IF EXISTS `oa_role`;
CREATE TABLE `oa_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `auth` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `del` tinyint(1) DEFAULT '0' COMMENT '0-正常 1-删除',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `create_account` varchar(255) DEFAULT NULL COMMENT '创建人账号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';

-- ----------------------------
-- Records of oa_role
-- ----------------------------
BEGIN;
INSERT INTO `oa_role` VALUES (1, '超级管理员', 'SUPER_ADMIN', 0, 'admin', 'admin', '2020-08-27 12:13:58', '2020-08-27 12:14:00');
INSERT INTO `oa_role` VALUES (2, '普通员工', 'STAFF', 0, 'admin', 'admin', '2020-08-27 12:16:07', '2020-08-27 12:16:09');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
