/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : flowboot

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 25/04/2022 18:42:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` tinyint(1) NULL DEFAULT NULL COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '初始化密码', 'sys.user.initPassword', '123456', NULL, 'root', '2021-11-02 16:48:17', '', '2021-11-02 16:48:17', NULL, 1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 5, '/sys/', '', 0, 0, 'M', '1', '1', '', 'el-icon-setting', 'root', '2021-11-02 16:20:26', 'root', '2021-12-27 14:57:36', '');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, 1, '/sys/user/', '/sys/user/index', 0, 0, 'C', '1', '1', 'sys:user', 'el-icon-user-solid', 'root', '2021-11-02 16:22:17', 'root', '2021-11-17 19:00:32', '');
INSERT INTO `sys_menu` VALUES (3, '菜单管理', 1, 2, '/sys/menu/', '/sys/menu/index', 0, 0, 'C', '1', '1', 'sys:menu', 'fa fa-th-list', 'root', '2021-11-02 16:23:13', 'root', '2021-11-17 19:00:38', '');
INSERT INTO `sys_menu` VALUES (4, '角色管理', 1, 1, '/sys/role/', '/sys/role/index', 0, 0, 'C', '1', '1', 'sys:role', 'fa fa-user-circle', 'root', '2021-11-02 16:34:59', 'root', '2021-11-17 19:00:50', '');
INSERT INTO `sys_menu` VALUES (5, '配置管理', 1, 0, '/sys/config/', '/sys/config/index', 0, 0, 'C', '1', '1', 'sys:config', 'fa fa-clipboard', 'root', '2021-11-02 16:35:55', 'root', '2021-11-17 19:00:57', '');
INSERT INTO `sys_menu` VALUES (6, '用户查询', 2, 0, '', '', 0, 0, 'F', '1', '1', 'sys:user:query', '#', 'root', '2021-11-17 18:29:21', '', '2021-11-17 18:29:21', '');
INSERT INTO `sys_menu` VALUES (7, '用户添加', 2, 0, '', '', 0, 0, 'F', '1', '1', 'sys:user:save', '#', 'root', '2021-11-17 18:15:47', '', '2021-11-17 18:15:47', '');
INSERT INTO `sys_menu` VALUES (8, '用户删除', 2, 0, '', '', 0, 0, 'F', '1', '1', 'sys:user:delete', '#', 'root', '2021-11-17 18:16:34', '', '2021-11-17 18:16:34', '');
INSERT INTO `sys_menu` VALUES (9, '用户更新', 2, 0, '', '', 0, 0, 'F', '1', '1', 'sys:user:update', '#', 'root', '2021-11-17 18:17:10', '', '2021-11-17 18:17:10', '');
INSERT INTO `sys_menu` VALUES (10, '菜单列表', 3, 0, '', '', 0, 0, 'F', '1', '1', 'sys:menu:list', '#', 'root', '2021-11-17 18:17:54', '', '2021-11-17 18:17:54', '');
INSERT INTO `sys_menu` VALUES (11, '菜单更新', 3, 0, '', '', 0, 0, 'F', '1', '1', 'sys:menu:update', '#', 'root', '2021-11-17 18:18:17', '', '2021-11-17 18:18:17', '');
INSERT INTO `sys_menu` VALUES (12, '菜单删除', 3, 0, '', '', 0, 0, 'F', '1', '1', 'sys:menu:delete', '#', 'root', '2021-11-17 18:19:20', '', '2021-11-17 18:19:20', '');
INSERT INTO `sys_menu` VALUES (13, '菜单新增', 3, 0, '', '', 0, 0, 'F', '1', '1', 'sys:menu:save', '#', 'root', '2021-11-17 18:19:59', '', '2021-11-17 18:19:59', '');
INSERT INTO `sys_menu` VALUES (14, '菜单查询', 3, 0, '', '', 0, 0, 'F', '1', '1', 'sys:menu:query', '#', 'root', '2021-11-17 18:21:18', '', '2021-11-17 18:21:18', '');
INSERT INTO `sys_menu` VALUES (15, '角色列表', 4, 0, '', '', 0, 0, 'F', '1', '1', 'sys:role:list', '#', 'root', '2021-11-17 18:20:50', '', '2021-11-17 18:20:50', '');
INSERT INTO `sys_menu` VALUES (16, '角色查询', 4, 0, '', '', 0, 0, 'F', '1', '1', 'sys:role:query', '#', 'root', '2021-11-17 18:24:13', '', '2021-11-17 18:24:13', '');
INSERT INTO `sys_menu` VALUES (17, '角色新增', 4, 0, '', '', 0, 0, 'F', '1', '1', 'sys:role:save', '#', 'root', '2021-11-17 18:24:32', 'root', '2021-11-17 18:25:05', '');
INSERT INTO `sys_menu` VALUES (18, '角色删除', 4, 0, '', '', 0, 0, 'F', '1', '1', 'sys:role:delete', '#', 'root', '2021-11-17 18:24:47', '', '2021-11-17 18:24:47', '');
INSERT INTO `sys_menu` VALUES (19, '角色修改', 4, 0, '', '', 0, 0, 'F', '1', '1', 'sys:role:update', '#', 'root', '2021-11-17 18:25:38', '', '2021-11-17 18:25:38', '');
INSERT INTO `sys_menu` VALUES (20, '配置列表', 5, 0, '', '', 0, 0, 'F', '1', '1', 'sys:config:list', '#', 'root', '2021-11-17 18:26:37', '', '2021-11-17 18:26:37', '');
INSERT INTO `sys_menu` VALUES (21, '配置新增', 5, 0, '', '', 0, 0, 'F', '1', '1', 'sys:config:save', '#', 'root', '2021-11-17 18:27:07', '', '2021-11-17 18:27:07', '');
INSERT INTO `sys_menu` VALUES (22, '配置查询', 5, 0, '', '', 0, 0, 'F', '1', '1', 'sys:config:query', '#', 'root', '2021-11-17 18:27:39', '', '2021-11-17 18:27:39', '');
INSERT INTO `sys_menu` VALUES (23, '配置更新', 5, 0, '', '', 0, 0, 'F', '1', '1', 'sys:config:update', '#', 'root', '2021-11-17 18:28:06', '', '2021-11-17 18:28:06', '');
INSERT INTO `sys_menu` VALUES (24, '配置删除', 5, 0, '', '', 0, 0, 'F', '1', '1', 'sys:config:delete', '#', 'root', '2021-11-17 18:28:38', '', '2021-11-17 18:28:38', '');
INSERT INTO `sys_menu` VALUES (25, '更新状态', 2, 0, '', '', 0, 0, 'F', '1', '1', 'sys:user:status', '#', 'root', '2021-11-17 19:05:16', '', '2021-11-17 19:05:16', '');
INSERT INTO `sys_menu` VALUES (26, '菜单状态', 3, 0, '', '', 0, 0, 'F', '1', '1', 'sys:menu:status', '#', 'root', '2021-11-17 19:05:44', '', '2021-11-17 19:05:44', '');
INSERT INTO `sys_menu` VALUES (27, '角色状态', 4, 0, '', '', 0, 0, 'F', '1', '1', 'sys:role:status', '#', 'root', '2021-11-17 19:06:06', '', '2021-11-17 19:06:06', '');
INSERT INTO `sys_menu` VALUES (28, '配置状态', 5, 0, '', '', 0, 0, 'F', '1', '1', 'sys:config:status', '#', 'root', '2021-11-17 19:06:25', '', '2021-11-17 19:06:25', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'root', 1, '2', 1, 1, '1', '0', 'root', '2021-11-02 16:40:47', '', '2021-11-02 16:40:47', NULL);
INSERT INTO `sys_role` VALUES (2, '管理员', 'admin', 1, '2', 1, 1, '1', '0', 'root', '2021-11-16 21:35:25', 'root', '2022-04-25 18:36:20', NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 45);
INSERT INTO `sys_role_menu` VALUES (2, 46);
INSERT INTO `sys_role_menu` VALUES (2, 47);
INSERT INTO `sys_role_menu` VALUES (2, 48);
INSERT INTO `sys_role_menu` VALUES (2, 49);
INSERT INTO `sys_role_menu` VALUES (2, 50);
INSERT INTO `sys_role_menu` VALUES (2, 51);
INSERT INTO `sys_role_menu` VALUES (2, 52);
INSERT INTO `sys_role_menu` VALUES (2, 53);
INSERT INTO `sys_role_menu` VALUES (2, 55);
INSERT INTO `sys_role_menu` VALUES (2, 56);
INSERT INTO `sys_role_menu` VALUES (2, 57);
INSERT INTO `sys_role_menu` VALUES (2, 58);
INSERT INTO `sys_role_menu` VALUES (2, 59);
INSERT INTO `sys_role_menu` VALUES (2, 60);
INSERT INTO `sys_role_menu` VALUES (2, 61);
INSERT INTO `sys_role_menu` VALUES (2, 62);
INSERT INTO `sys_role_menu` VALUES (2, 63);
INSERT INTO `sys_role_menu` VALUES (2, 64);
INSERT INTO `sys_role_menu` VALUES (2, 82);
INSERT INTO `sys_role_menu` VALUES (2, 83);
INSERT INTO `sys_role_menu` VALUES (2, 84);
INSERT INTO `sys_role_menu` VALUES (2, 85);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, NULL, 'root', 'root', '00', '668888312@qq.com', '18899008866', '0', '', '{bcrypt}$2a$10$E/zkvYepdFf6HkHRONjZregzfKu8RJo8r1wbTHkJii9ykzxd7ulc6', '1', '0', '', NULL, '', '2021-11-02 16:04:11', 'root', '2021-11-05 18:34:51', NULL);
INSERT INTO `sys_user` VALUES (2, NULL, 'admin', 'admin', '00', '192391297@qq.com', '18800990099', '0', '', '{bcrypt}$2a$10$ToIEiZeckRwHl7OVr4VLyee9dyR8vxupWZZBPIutYSOHt2riWpm02', '1', '0', '', NULL, 'root', '2021-12-14 10:31:51', 'root', '2022-04-25 18:36:43', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
