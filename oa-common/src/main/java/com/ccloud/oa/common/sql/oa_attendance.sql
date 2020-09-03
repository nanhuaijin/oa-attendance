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

 Date: 27/08/2020 12:36:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oa_attendance
-- ----------------------------
DROP TABLE IF EXISTS `oa_attendance`;
CREATE TABLE `oa_attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(255) DEFAULT NULL COMMENT '用户账号',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `month` int(2) DEFAULT NULL COMMENT '月',
  `day` int(2) DEFAULT NULL COMMENT '日',
  `start` time DEFAULT NULL COMMENT '上班时间',
  `end` time DEFAULT NULL COMMENT '下班时间',
  `hours` double(3,1) DEFAULT NULL COMMENT '工作时长',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '0-正常 1-迟到 2-事假 3-病假 4-休假 默认0',
  `address_start` varchar(255) DEFAULT NULL COMMENT '上班打卡地点',
  `address_end` varchar(255) DEFAULT NULL COMMENT '下班打卡地点',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户打卡记录表';

SET FOREIGN_KEY_CHECKS = 1;
