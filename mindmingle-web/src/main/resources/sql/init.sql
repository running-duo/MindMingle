-- ============================================================
-- MindMingle Database Initialization Script
-- ============================================================
-- Description: Database schema initialization for MindMingle project
-- Author: Database Team
-- Created: 2026-01-02
-- Version: 1.0.0
-- ============================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- 1. User & Authentication Tables (用户认证授权表)
-- ============================================================

-- ----------------------------
-- Table structure for t_user (系统用户表)
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username` VARCHAR(64) NOT NULL COMMENT '用户名',
    `password` VARCHAR(128) NOT NULL COMMENT '密码（BCrypt加密）',
    `real_name` VARCHAR(64) DEFAULT NULL COMMENT '真实姓名',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `email` VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip` VARCHAR(64) DEFAULT NULL COMMENT '最后登录IP',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_phone` (`phone`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- ----------------------------
-- Table structure for t_role (角色表)
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_code` VARCHAR(64) NOT NULL COMMENT '角色编码',
    `role_name` VARCHAR(64) NOT NULL COMMENT '角色名称',
    `sort` INT(11) NOT NULL DEFAULT 0 COMMENT '排序',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code` (`role_code`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- ----------------------------
-- Table structure for t_user_role (用户角色关联表)
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- ----------------------------
-- Table structure for t_permission (权限表)
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '父级ID，0表示顶级',
    `permission_code` VARCHAR(128) NOT NULL COMMENT '权限编码',
    `permission_name` VARCHAR(64) NOT NULL COMMENT '权限名称',
    `permission_type` TINYINT(1) NOT NULL COMMENT '权限类型：1-菜单，2-按钮，3-接口',
    `path` VARCHAR(255) DEFAULT NULL COMMENT '路由路径',
    `icon` VARCHAR(128) DEFAULT NULL COMMENT '图标',
    `component` VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
    `sort` INT(11) NOT NULL DEFAULT 0 COMMENT '排序',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_permission_code` (`permission_code`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- ----------------------------
-- Table structure for t_role_permission (角色权限关联表)
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
    `permission_id` BIGINT(20) NOT NULL COMMENT '权限ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

-- ============================================================
-- 2. WeChat Mini Program Tables (微信小程序相关表)
-- ============================================================

-- ----------------------------
-- Table structure for t_wx_user (微信用户表)
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_user`;
CREATE TABLE `t_wx_user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `open_id` VARCHAR(128) NOT NULL COMMENT '微信OpenID',
    `union_id` VARCHAR(128) DEFAULT NULL COMMENT '微信UnionID',
    `session_key` VARCHAR(128) DEFAULT NULL COMMENT '会话密钥',
    `nickname` VARCHAR(128) DEFAULT NULL COMMENT '昵称',
    `avatar_url` VARCHAR(512) DEFAULT NULL COMMENT '头像URL',
    `gender` TINYINT(1) DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    `country` VARCHAR(64) DEFAULT NULL COMMENT '国家',
    `province` VARCHAR(64) DEFAULT NULL COMMENT '省份',
    `city` VARCHAR(64) DEFAULT NULL COMMENT '城市',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_open_id` (`open_id`),
    KEY `idx_union_id` (`union_id`),
    KEY `idx_phone` (`phone`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='微信用户表';

-- ============================================================
-- 3. Message Tables (消息相关表)
-- ============================================================

-- ----------------------------
-- Table structure for t_message (消息记录表)
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `message_type` TINYINT(1) NOT NULL COMMENT '消息类型：1-系统消息，2-微信订阅消息，3-Bark推送',
    `receiver_type` TINYINT(1) NOT NULL COMMENT '接收者类型：1-系统用户，2-微信用户',
    `receiver_id` BIGINT(20) NOT NULL COMMENT '接收者ID',
    `title` VARCHAR(128) DEFAULT NULL COMMENT '消息标题',
    `content` TEXT NOT NULL COMMENT '消息内容',
    `template_id` VARCHAR(128) DEFAULT NULL COMMENT '模板ID（微信订阅消息）',
    `page` VARCHAR(255) DEFAULT NULL COMMENT '跳转页面',
    `send_status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '发送状态：0-待发送，1-发送成功，2-发送失败',
    `send_time` DATETIME DEFAULT NULL COMMENT '发送时间',
    `error_msg` VARCHAR(500) DEFAULT NULL COMMENT '错误信息',
    `is_read` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已读：0-未读，1-已读',
    `read_time` DATETIME DEFAULT NULL COMMENT '阅读时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_receiver` (`receiver_type`, `receiver_id`),
    KEY `idx_send_status` (`send_status`),
    KEY `idx_is_read` (`is_read`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息记录表';

-- ============================================================
-- 4. System Log Tables (系统日志表)
-- ============================================================

-- ----------------------------
-- Table structure for t_login_log (登录日志表)
-- ----------------------------
DROP TABLE IF EXISTS `t_login_log`;
CREATE TABLE `t_login_log` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_type` TINYINT(1) NOT NULL COMMENT '用户类型：1-系统用户，2-微信用户',
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `username` VARCHAR(128) NOT NULL COMMENT '用户名/OpenID',
    `login_type` TINYINT(1) NOT NULL COMMENT '登录类型：1-密码登录，2-微信授权登录',
    `ip_address` VARCHAR(64) DEFAULT NULL COMMENT 'IP地址',
    `user_agent` VARCHAR(512) DEFAULT NULL COMMENT '用户代理',
    `login_status` TINYINT(1) NOT NULL COMMENT '登录状态：1-成功，2-失败',
    `login_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    `error_msg` VARCHAR(500) DEFAULT NULL COMMENT '错误信息',
    PRIMARY KEY (`id`),
    KEY `idx_user` (`user_type`, `user_id`),
    KEY `idx_login_time` (`login_time`),
    KEY `idx_login_status` (`login_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录日志表';

-- ----------------------------
-- Table structure for t_operation_log (操作日志表)
-- ----------------------------
DROP TABLE IF EXISTS `t_operation_log`;
CREATE TABLE `t_operation_log` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_type` TINYINT(1) NOT NULL COMMENT '用户类型：1-系统用户，2-微信用户',
    `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
    `username` VARCHAR(128) DEFAULT NULL COMMENT '用户名',
    `module` VARCHAR(64) NOT NULL COMMENT '操作模块',
    `operation` VARCHAR(128) NOT NULL COMMENT '操作类型',
    `method` VARCHAR(255) DEFAULT NULL COMMENT '请求方法',
    `request_url` VARCHAR(512) DEFAULT NULL COMMENT '请求URL',
    `request_method` VARCHAR(10) DEFAULT NULL COMMENT '请求方式（GET/POST等）',
    `request_params` TEXT DEFAULT NULL COMMENT '请求参数',
    `response_result` TEXT DEFAULT NULL COMMENT '返回结果',
    `ip_address` VARCHAR(64) DEFAULT NULL COMMENT 'IP地址',
    `execution_time` INT(11) DEFAULT NULL COMMENT '执行时长（毫秒）',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：1-成功，2-失败',
    `error_msg` TEXT DEFAULT NULL COMMENT '错误信息',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user` (`user_type`, `user_id`),
    KEY `idx_module` (`module`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';

-- ============================================================
-- 5. Configuration Tables (配置表)
-- ============================================================

-- ----------------------------
-- Table structure for t_system_config (系统配置表)
-- ----------------------------
DROP TABLE IF EXISTS `t_system_config`;
CREATE TABLE `t_system_config` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `config_key` VARCHAR(128) NOT NULL COMMENT '配置键',
    `config_value` TEXT NOT NULL COMMENT '配置值',
    `config_type` VARCHAR(32) NOT NULL COMMENT '配置类型：string/number/boolean/json',
    `config_group` VARCHAR(64) DEFAULT NULL COMMENT '配置分组',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '配置描述',
    `is_encrypted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否加密：0-否，1-是',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`),
    KEY `idx_config_group` (`config_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- ============================================================
-- End of initialization script
-- ============================================================

SET FOREIGN_KEY_CHECKS = 1;
