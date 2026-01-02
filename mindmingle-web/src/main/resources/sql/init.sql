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
-- 1. Tenant Table (租户表)
-- ============================================================

-- ----------------------------
-- Table structure for t_tenant (租户表)
-- ----------------------------
DROP TABLE IF EXISTS `t_tenant`;
CREATE TABLE `t_tenant` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_name` VARCHAR(128) NOT NULL COMMENT '租户名称',
    `tenant_code` VARCHAR(64) NOT NULL COMMENT '租户编码',
    `contact_name` VARCHAR(64) DEFAULT NULL COMMENT '联系人姓名',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系人电话',
    `contact_email` VARCHAR(128) DEFAULT NULL COMMENT '联系人邮箱',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `max_users` INT(11) DEFAULT NULL COMMENT '最大用户数',
    `max_tools` INT(11) DEFAULT NULL COMMENT '最大工具数',
    `expire_time` DATETIME DEFAULT NULL COMMENT '过期时间',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by` BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
    `updated_by` BIGINT(20) DEFAULT NULL COMMENT '更新人ID',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_code` (`tenant_code`),
    KEY `idx_status` (`status`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='租户表';

-- ============================================================
-- 2. User & Authentication Tables (用户认证授权表)
-- ============================================================

-- ----------------------------
-- Table structure for t_user (系统用户表)
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT(20) NOT NULL DEFAULT 1 COMMENT '租户ID',
    `username` VARCHAR(64) NOT NULL COMMENT '用户名',
    `password` VARCHAR(128) NOT NULL COMMENT '密码（BCrypt加密）',
    `nickname` VARCHAR(128) DEFAULT NULL COMMENT '昵称',
    `real_name` VARCHAR(64) DEFAULT NULL COMMENT '真实姓名',
    `email` VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `avatar_url` VARCHAR(512) DEFAULT NULL COMMENT '头像URL',
    `wx_openid` VARCHAR(128) DEFAULT NULL COMMENT '微信OpenID',
    `wx_unionid` VARCHAR(128) DEFAULT NULL COMMENT '微信UnionID',
    `user_type` TINYINT(1) DEFAULT 0 COMMENT '用户类型：0-普通用户，1-管理员',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip` VARCHAR(64) DEFAULT NULL COMMENT '最后登录IP',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by` BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
    `updated_by` BIGINT(20) DEFAULT NULL COMMENT '更新人ID',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_username` (`tenant_id`, `username`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_phone` (`phone`),
    KEY `idx_email` (`email`),
    KEY `idx_wx_openid` (`wx_openid`),
    KEY `idx_status` (`status`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- ----------------------------
-- Table structure for t_role (角色表)
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT(20) NOT NULL DEFAULT 1 COMMENT '租户ID',
    `role_code` VARCHAR(64) NOT NULL COMMENT '角色编码',
    `role_name` VARCHAR(64) NOT NULL COMMENT '角色名称',
    `role_type` TINYINT(1) DEFAULT 0 COMMENT '角色类型：0-普通角色，1-系统角色',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '角色描述',
    `is_system` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否系统角色：0-否，1-是',
    `sort_order` INT(11) NOT NULL DEFAULT 0 COMMENT '排序',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by` BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
    `updated_by` BIGINT(20) DEFAULT NULL COMMENT '更新人ID',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_role_code` (`tenant_id`, `role_code`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_role_type` (`role_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- ----------------------------
-- Table structure for t_user_role (用户角色关联表)
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
    `scope_type` TINYINT(1) DEFAULT 0 COMMENT '范围类型：0-全局，1-租户，2-工具',
    `scope_id` BIGINT(20) DEFAULT NULL COMMENT '范围ID',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by` BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role_scope` (`user_id`, `role_id`, `scope_type`, `scope_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- ----------------------------
-- Table structure for t_permission (权限表)
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `permission_code` VARCHAR(128) NOT NULL COMMENT '权限编码',
    `permission_name` VARCHAR(64) NOT NULL COMMENT '权限名称',
    `permission_type` TINYINT(1) NOT NULL COMMENT '权限类型：1-菜单，2-按钮，3-接口',
    `resource_type` VARCHAR(32) DEFAULT NULL COMMENT '资源类型',
    `resource_action` VARCHAR(32) DEFAULT NULL COMMENT '资源操作',
    `parent_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '父级ID，0表示顶级',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '权限描述',
    `sort_order` INT(11) NOT NULL DEFAULT 0 COMMENT '排序',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_permission_code` (`permission_code`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_permission_type` (`permission_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- ----------------------------
-- Table structure for t_role_permission (角色权限关联表)
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
    `permission_id` BIGINT(20) NOT NULL COMMENT '权限ID',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by` BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

-- ============================================================
-- 3. Menu Tables (菜单表)
-- ============================================================

-- ----------------------------
-- Table structure for t_menu (菜单表)
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT(20) NOT NULL DEFAULT 1 COMMENT '租户ID',
    `menu_name` VARCHAR(64) NOT NULL COMMENT '菜单名称',
    `menu_code` VARCHAR(128) NOT NULL COMMENT '菜单编码',
    `menu_type` TINYINT(1) NOT NULL COMMENT '菜单类型：1-目录，2-菜单，3-按钮',
    `parent_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '父菜单ID，0表示顶级',
    `path` VARCHAR(255) DEFAULT NULL COMMENT '路由路径',
    `component` VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
    `icon` VARCHAR(128) DEFAULT NULL COMMENT '图标',
    `sort_order` INT(11) NOT NULL DEFAULT 0 COMMENT '排序',
    `is_visible` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否可见：0-否，1-是',
    `is_cache` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否缓存：0-否，1-是',
    `is_frame` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否外链：0-否，1-是',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by` BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
    `updated_by` BIGINT(20) DEFAULT NULL COMMENT '更新人ID',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_menu_code` (`tenant_id`, `menu_code`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单表';

-- ----------------------------
-- Table structure for t_menu_permission (菜单权限关联表)
-- ----------------------------
DROP TABLE IF EXISTS `t_menu_permission`;
CREATE TABLE `t_menu_permission` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `menu_id` BIGINT(20) NOT NULL COMMENT '菜单ID',
    `permission_id` BIGINT(20) NOT NULL COMMENT '权限ID',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by` BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_menu_permission` (`menu_id`, `permission_id`),
    KEY `idx_menu_id` (`menu_id`),
    KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单权限关联表';

-- ============================================================
-- 4. Tool & Template Tables (工具和模板表)
-- ============================================================

-- ----------------------------
-- Table structure for t_template (工具模板表)
-- ----------------------------
DROP TABLE IF EXISTS `t_template`;
CREATE TABLE `t_template` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `template_name` VARCHAR(128) NOT NULL COMMENT '模板名称',
    `template_code` VARCHAR(64) NOT NULL COMMENT '模板编码',
    `template_type` VARCHAR(32) DEFAULT NULL COMMENT '模板类型',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '模板描述',
    `icon_url` VARCHAR(512) DEFAULT NULL COMMENT '图标URL',
    `config_json` TEXT DEFAULT NULL COMMENT '配置JSON',
    `preview_image_url` VARCHAR(512) DEFAULT NULL COMMENT '预览图URL',
    `is_system` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否系统模板：0-否，1-是',
    `category` VARCHAR(64) DEFAULT NULL COMMENT '分类',
    `tags` VARCHAR(255) DEFAULT NULL COMMENT '标签',
    `use_count` INT(11) NOT NULL DEFAULT 0 COMMENT '使用次数',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by` BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
    `updated_by` BIGINT(20) DEFAULT NULL COMMENT '更新人ID',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_template_code` (`template_code`),
    KEY `idx_template_type` (`template_type`),
    KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工具模板表';

-- ----------------------------
-- Table structure for t_tool (工具表)
-- ----------------------------
DROP TABLE IF EXISTS `t_tool`;
CREATE TABLE `t_tool` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT(20) NOT NULL DEFAULT 1 COMMENT '租户ID',
    `tool_name` VARCHAR(128) NOT NULL COMMENT '工具名称',
    `tool_code` VARCHAR(64) NOT NULL COMMENT '工具编码',
    `tool_type` VARCHAR(32) DEFAULT NULL COMMENT '工具类型',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '工具描述',
    `icon_url` VARCHAR(512) DEFAULT NULL COMMENT '图标URL',
    `template_id` BIGINT(20) DEFAULT NULL COMMENT '模板ID',
    `config_json` TEXT DEFAULT NULL COMMENT '配置JSON',
    `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建者ID',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `is_public` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否公开：0-否，1-是',
    `sort_order` INT(11) NOT NULL DEFAULT 0 COMMENT '排序',
    `view_count` INT(11) NOT NULL DEFAULT 0 COMMENT '浏览次数',
    `use_count` INT(11) NOT NULL DEFAULT 0 COMMENT '使用次数',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by` BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
    `updated_by` BIGINT(20) DEFAULT NULL COMMENT '更新人ID',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_tool_code` (`tenant_id`, `tool_code`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_template_id` (`template_id`),
    KEY `idx_creator_id` (`creator_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工具表';

-- ----------------------------
-- Table structure for t_tool_member (工具协作者表)
-- ----------------------------
DROP TABLE IF EXISTS `t_tool_member`;
CREATE TABLE `t_tool_member` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT(20) NOT NULL DEFAULT 1 COMMENT '租户ID',
    `tool_id` BIGINT(20) NOT NULL COMMENT '工具ID',
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `permission_level` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '权限级别：1-查看，2-编辑，3-管理',
    `invited_by` BIGINT(20) DEFAULT NULL COMMENT '邀请人ID',
    `invited_at` DATETIME DEFAULT NULL COMMENT '邀请时间',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_tool_user` (`tenant_id`, `tool_id`, `user_id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_tool_id` (`tool_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工具协作者表';

-- ----------------------------
-- Table structure for t_submission (工具提交数据表)
-- ----------------------------
DROP TABLE IF EXISTS `t_submission`;
CREATE TABLE `t_submission` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT(20) NOT NULL DEFAULT 1 COMMENT '租户ID',
    `tool_id` BIGINT(20) NOT NULL COMMENT '工具ID',
    `user_id` BIGINT(20) NOT NULL COMMENT '提交用户ID',
    `submission_data` TEXT DEFAULT NULL COMMENT '提交数据JSON',
    `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态：0-待审核，1-已通过，2-已拒绝',
    `submit_time` DATETIME DEFAULT NULL COMMENT '提交时间',
    `review_user_id` BIGINT(20) DEFAULT NULL COMMENT '审核人ID',
    `review_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `review_comment` VARCHAR(500) DEFAULT NULL COMMENT '审核意见',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by` BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
    `updated_by` BIGINT(20) DEFAULT NULL COMMENT '更新人ID',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_tool_id` (`tool_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_submit_time` (`submit_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工具提交数据表';

-- ============================================================
-- End of initialization script
-- ============================================================

SET FOREIGN_KEY_CHECKS = 1;
