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

 Date: 27/08/2020 12:36:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oa_user
-- ----------------------------
DROP TABLE IF EXISTS `oa_user`;
CREATE TABLE `oa_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '关联角色权限表oa_role的id',
  `account` varchar(255) DEFAULT NULL COMMENT '用户账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT NULL COMMENT '加密盐',
  `username` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `phone` char(11) DEFAULT NULL COMMENT '手机号码',
  `sex` tinyint(1) DEFAULT '0' COMMENT '性别 0-男 1-女',
  `birthday` varchar(10) DEFAULT NULL COMMENT '生日',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `del` tinyint(1) DEFAULT '0' COMMENT '状态 0-正常 1-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of oa_user
-- ----------------------------
BEGIN;
INSERT INTO `oa_user` VALUES (1, 1, 'admin', '!#/)zW��C�JJ��d0cddede84c444a23a3831f576a68350', '0864bc45d7', 'admin', 'admin', '12345678901', 0, '2020-01-01', 0, '2020-08-27 12:14:16', '2020-08-27 12:14:16');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
