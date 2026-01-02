-- ============================================================
-- MindMingle Database Initial Data Script
-- ============================================================
-- Description: Initial data for MindMingle project
-- Author: Database Team
-- Created: 2026-01-02
-- Version: 1.0.0
-- ============================================================

SET NAMES utf8mb4;

-- ============================================================
-- 1. Initial Tenants (初始租户数据)
-- ============================================================

INSERT INTO `t_tenant` (`id`, `tenant_name`, `tenant_code`, `contact_name`, `contact_phone`, `contact_email`, `status`, `max_users`, `max_tools`, `expire_time`, `created_at`, `updated_at`)
VALUES
(1, '默认租户', 'DEFAULT', '系统管理员', '13800138000', 'admin@mindmingle.com', 1, 1000, 500, '2099-12-31 23:59:59', NOW(), NOW());

-- ============================================================
-- 2. Initial Users (初始用户数据)
-- ============================================================

-- 插入系统用户
-- 默认密码: admin123 (BCrypt加密后: $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi)
INSERT INTO `t_user` (`id`, `tenant_id`, `username`, `password`, `nickname`, `real_name`, `phone`, `email`, `avatar_url`, `user_type`, `status`, `created_at`, `updated_at`)
VALUES
(1, 1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '超级管理员', '超级管理员', '13800138000', 'admin@mindmingle.com', NULL, 1, 1, NOW(), NOW()),
(2, 1, 'teacher1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张老师', '张老师', '13800138001', 'teacher1@mindmingle.com', NULL, 0, 1, NOW(), NOW()),
(3, 1, 'teacher2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李老师', '李老师', '13800138002', 'teacher2@mindmingle.com', NULL, 0, 1, NOW(), NOW()),
(4, 1, 'teacher3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '王老师', '王老师', '13800138003', 'teacher3@mindmingle.com', NULL, 0, 1, NOW(), NOW()),
(5, 1, 'manager', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', '系统管理员', '13800138004', 'manager@mindmingle.com', NULL, 1, 1, NOW(), NOW());

-- ============================================================
-- 3. Initial Roles (初始角色数据)
-- ============================================================

INSERT INTO `t_role` (`id`, `tenant_id`, `role_code`, `role_name`, `role_type`, `description`, `is_system`, `sort_order`, `created_at`, `updated_at`)
VALUES
(1, 1, 'ROLE_SUPER_ADMIN', '超级管理员', 1, '拥有系统所有权限', 1, 1, NOW(), NOW()),
(2, 1, 'ROLE_ADMIN', '系统管理员', 1, '拥有系统大部分权限', 1, 2, NOW(), NOW()),
(3, 1, 'ROLE_TEACHER', '教师', 0, '教师角色，可查看学生信息和教学管理', 0, 3, NOW(), NOW()),
(4, 1, 'ROLE_USER', '普通用户', 0, '普通用户角色，基础权限', 0, 4, NOW(), NOW());

-- ============================================================
-- 4. Initial User-Role Relations (初始用户角色关联)
-- ============================================================

INSERT INTO `t_user_role` (`user_id`, `role_id`, `scope_type`, `scope_id`, `created_at`, `created_by`)
VALUES
(1, 1, 0, NULL, NOW(), 1),  -- admin 拥有超级管理员角色
(2, 3, 0, NULL, NOW(), 1),  -- teacher1 拥有教师角色
(3, 3, 0, NULL, NOW(), 1),  -- teacher2 拥有教师角色
(4, 3, 0, NULL, NOW(), 1),  -- teacher3 拥有教师角色
(5, 2, 0, NULL, NOW(), 1);  -- manager 拥有系统管理员角色

-- ============================================================
-- 5. Initial Permissions (初始权限数据)
-- ============================================================

INSERT INTO `t_permission` (`id`, `permission_code`, `permission_name`, `permission_type`, `resource_type`, `resource_action`, `parent_id`, `description`, `sort_order`, `created_at`, `updated_at`)
VALUES
-- 一级权限模块
(1, 'system', '系统管理', 1, NULL, NULL, 0, '系统管理模块', 1, NOW(), NOW()),
(2, 'tool', '工具管理', 1, NULL, NULL, 0, '工具管理模块', 2, NOW(), NOW()),
(3, 'user', '用户管理', 1, NULL, NULL, 0, '用户管理模块', 3, NOW(), NOW()),

-- 系统管理 - 二级权限
(101, 'system:role', '角色管理', 1, 'role', NULL, 1, '角色管理页面', 1, NOW(), NOW()),
(102, 'system:permission', '权限管理', 1, 'permission', NULL, 1, '权限管理页面', 2, NOW(), NOW()),
(103, 'system:menu', '菜单管理', 1, 'menu', NULL, 1, '菜单管理页面', 3, NOW(), NOW()),
(104, 'system:tenant', '租户管理', 1, 'tenant', NULL, 1, '租户管理页面', 4, NOW(), NOW()),

-- 角色管理 - 按钮权限
(10101, 'system:role:list', '查看角色', 2, 'role', 'list', 101, '查看角色列表', 1, NOW(), NOW()),
(10102, 'system:role:add', '新增角色', 2, 'role', 'add', 101, '新增角色', 2, NOW(), NOW()),
(10103, 'system:role:edit', '编辑角色', 2, 'role', 'edit', 101, '编辑角色', 3, NOW(), NOW()),
(10104, 'system:role:delete', '删除角色', 2, 'role', 'delete', 101, '删除角色', 4, NOW(), NOW()),
(10105, 'system:role:auth', '角色授权', 2, 'role', 'auth', 101, '角色权限分配', 5, NOW(), NOW()),

-- 权限管理 - 按钮权限
(10201, 'system:permission:list', '查看权限', 2, 'permission', 'list', 102, '查看权限列表', 1, NOW(), NOW()),
(10202, 'system:permission:add', '新增权限', 2, 'permission', 'add', 102, '新增权限', 2, NOW(), NOW()),
(10203, 'system:permission:edit', '编辑权限', 2, 'permission', 'edit', 102, '编辑权限', 3, NOW(), NOW()),
(10204, 'system:permission:delete', '删除权限', 2, 'permission', 'delete', 102, '删除权限', 4, NOW(), NOW()),

-- 菜单管理 - 按钮权限
(10301, 'system:menu:list', '查看菜单', 2, 'menu', 'list', 103, '查看菜单列表', 1, NOW(), NOW()),
(10302, 'system:menu:add', '新增菜单', 2, 'menu', 'add', 103, '新增菜单', 2, NOW(), NOW()),
(10303, 'system:menu:edit', '编辑菜单', 2, 'menu', 'edit', 103, '编辑菜单', 3, NOW(), NOW()),
(10304, 'system:menu:delete', '删除菜单', 2, 'menu', 'delete', 103, '删除菜单', 4, NOW(), NOW()),

-- 租户管理 - 按钮权限
(10401, 'system:tenant:list', '查看租户', 2, 'tenant', 'list', 104, '查看租户列表', 1, NOW(), NOW()),
(10402, 'system:tenant:add', '新增租户', 2, 'tenant', 'add', 104, '新增租户', 2, NOW(), NOW()),
(10403, 'system:tenant:edit', '编辑租户', 2, 'tenant', 'edit', 104, '编辑租户', 3, NOW(), NOW()),
(10404, 'system:tenant:delete', '删除租户', 2, 'tenant', 'delete', 104, '删除租户', 4, NOW(), NOW()),

-- 工具管理 - 二级权限
(201, 'tool:template', '模板管理', 1, 'template', NULL, 2, '工具模板管理', 1, NOW(), NOW()),
(202, 'tool:manage', '工具管理', 1, 'tool', NULL, 2, '工具管理页面', 2, NOW(), NOW()),
(203, 'tool:submission', '提交管理', 1, 'submission', NULL, 2, '工具提交数据管理', 3, NOW(), NOW()),

-- 模板管理 - 按钮权限
(20101, 'tool:template:list', '查看模板', 2, 'template', 'list', 201, '查看模板列表', 1, NOW(), NOW()),
(20102, 'tool:template:add', '新增模板', 2, 'template', 'add', 201, '新增模板', 2, NOW(), NOW()),
(20103, 'tool:template:edit', '编辑模板', 2, 'template', 'edit', 201, '编辑模板', 3, NOW(), NOW()),
(20104, 'tool:template:delete', '删除模板', 2, 'template', 'delete', 201, '删除模板', 4, NOW(), NOW()),

-- 工具管理 - 按钮权限
(20201, 'tool:manage:list', '查看工具', 2, 'tool', 'list', 202, '查看工具列表', 1, NOW(), NOW()),
(20202, 'tool:manage:add', '新增工具', 2, 'tool', 'add', 202, '新增工具', 2, NOW(), NOW()),
(20203, 'tool:manage:edit', '编辑工具', 2, 'tool', 'edit', 202, '编辑工具', 3, NOW(), NOW()),
(20204, 'tool:manage:delete', '删除工具', 2, 'tool', 'delete', 202, '删除工具', 4, NOW(), NOW()),

-- 提交管理 - 按钮权限
(20301, 'tool:submission:list', '查看提交', 2, 'submission', 'list', 203, '查看提交数据', 1, NOW(), NOW()),
(20302, 'tool:submission:review', '审核提交', 2, 'submission', 'review', 203, '审核提交数据', 2, NOW(), NOW()),
(20303, 'tool:submission:delete', '删除提交', 2, 'submission', 'delete', 203, '删除提交数据', 3, NOW(), NOW()),

-- 用户管理 - 二级权限
(301, 'user:manage', '用户管理', 1, 'user', NULL, 3, '用户管理页面', 1, NOW(), NOW()),

-- 用户管理 - 按钮权限
(30101, 'user:manage:list', '查看用户', 2, 'user', 'list', 301, '查看用户列表', 1, NOW(), NOW()),
(30102, 'user:manage:add', '新增用户', 2, 'user', 'add', 301, '新增用户', 2, NOW(), NOW()),
(30103, 'user:manage:edit', '编辑用户', 2, 'user', 'edit', 301, '编辑用户', 3, NOW(), NOW()),
(30104, 'user:manage:delete', '删除用户', 2, 'user', 'delete', 301, '删除用户', 4, NOW(), NOW()),
(30105, 'user:manage:reset', '重置密码', 2, 'user', 'reset', 301, '重置用户密码', 5, NOW(), NOW());

-- ============================================================
-- 6. Initial Role-Permission Relations (初始角色权限关联)
-- ============================================================

-- 超级管理员拥有所有权限
INSERT INTO `t_role_permission` (`role_id`, `permission_id`, `created_at`, `created_by`)
SELECT 1, `id`, NOW(), 1 FROM `t_permission` WHERE `is_deleted` = 0;

-- 系统管理员拥有除删除外的大部分权限
INSERT INTO `t_role_permission` (`role_id`, `permission_id`, `created_at`, `created_by`)
SELECT 2, `id`, NOW(), 1 FROM `t_permission`
WHERE `is_deleted` = 0 AND `permission_code` NOT LIKE '%delete%';

-- 教师角色拥有查看和基础操作权限
INSERT INTO `t_role_permission` (`role_id`, `permission_id`, `created_at`, `created_by`)
VALUES
-- 工具管理模块
(3, 2, NOW(), 1),      -- 工具管理模块
(3, 202, NOW(), 1),    -- 工具管理菜单
(3, 20201, NOW(), 1),  -- 查看工具
(3, 203, NOW(), 1),    -- 提交管理菜单
(3, 20301, NOW(), 1),  -- 查看提交
-- 用户管理模块
(3, 3, NOW(), 1),      -- 用户管理模块
(3, 301, NOW(), 1),    -- 用户管理菜单
(3, 30101, NOW(), 1);  -- 查看用户

-- 普通用户角色拥有基础查看权限
INSERT INTO `t_role_permission` (`role_id`, `permission_id`, `created_at`, `created_by`)
VALUES
(4, 2, NOW(), 1),      -- 工具管理模块
(4, 202, NOW(), 1),    -- 工具管理菜单
(4, 20201, NOW(), 1);  -- 查看工具

-- ============================================================
-- 7. Initial Menus (初始菜单数据)
-- ============================================================

INSERT INTO `t_menu` (`id`, `tenant_id`, `menu_name`, `menu_code`, `menu_type`, `parent_id`, `path`, `component`, `icon`, `sort_order`, `is_visible`, `is_cache`, `is_frame`, `status`, `created_at`, `updated_at`)
VALUES
-- 一级菜单
(1, 1, '系统管理', 'system', 1, 0, '/system', NULL, 'system', 1, 1, 0, 0, 1, NOW(), NOW()),
(2, 1, '工具管理', 'tool', 1, 0, '/tool', NULL, 'tool', 2, 1, 0, 0, 1, NOW(), NOW()),
(3, 1, '用户管理', 'user', 1, 0, '/user', NULL, 'user', 3, 1, 0, 0, 1, NOW(), NOW()),

-- 系统管理 - 二级菜单
(101, 1, '角色管理', 'system:role', 2, 1, '/system/role', 'system/role/index', 'role', 1, 1, 1, 0, 1, NOW(), NOW()),
(102, 1, '权限管理', 'system:permission', 2, 1, '/system/permission', 'system/permission/index', 'permission', 2, 1, 1, 0, 1, NOW(), NOW()),
(103, 1, '菜单管理', 'system:menu', 2, 1, '/system/menu', 'system/menu/index', 'menu', 3, 1, 1, 0, 1, NOW(), NOW()),
(104, 1, '租户管理', 'system:tenant', 2, 1, '/system/tenant', 'system/tenant/index', 'tenant', 4, 1, 1, 0, 1, NOW(), NOW()),

-- 工具管理 - 二级菜单
(201, 1, '模板管理', 'tool:template', 2, 2, '/tool/template', 'tool/template/index', 'template', 1, 1, 1, 0, 1, NOW(), NOW()),
(202, 1, '工具管理', 'tool:manage', 2, 2, '/tool/manage', 'tool/manage/index', 'tool', 2, 1, 1, 0, 1, NOW(), NOW()),
(203, 1, '提交管理', 'tool:submission', 2, 2, '/tool/submission', 'tool/submission/index', 'submission', 3, 1, 1, 0, 1, NOW(), NOW()),

-- 用户管理 - 二级菜单
(301, 1, '用户管理', 'user:manage', 2, 3, '/user/manage', 'user/manage/index', 'user', 1, 1, 1, 0, 1, NOW(), NOW());

-- ============================================================
-- 8. Initial Templates (初始模板数据)
-- ============================================================

INSERT INTO `t_template` (`id`, `template_name`, `template_code`, `template_type`, `description`, `icon_url`, `config_json`, `preview_image_url`, `is_system`, `category`, `tags`, `use_count`, `created_at`, `updated_at`, `created_by`)
VALUES
(1, '基础表单模板', 'FORM_BASIC', 'form', '基础表单模板，包含常用表单控件', NULL, '{}', NULL, 1, 'form', 'form,basic', 0, NOW(), NOW(), 1),
(2, '数据列表模板', 'LIST_BASIC', 'list', '数据列表模板，支持增删改查', NULL, '{}', NULL, 1, 'list', 'list,table', 0, NOW(), NOW(), 1),
(3, '数据统计模板', 'CHART_BASIC', 'chart', '数据统计模板，支持多种图表', NULL, '{}', NULL, 1, 'chart', 'chart,statistics', 0, NOW(), NOW(), 1);

-- ============================================================
-- End of initial data script
-- ============================================================

-- ============================================================
-- 测试账号信息
-- ============================================================
-- 1. 超级管理员
--    用户名: admin
--    密码: admin123
--    说明: 拥有所有权限
--
-- 2. 系统管理员
--    用户名: manager
--    密码: admin123
--    说明: 拥有除删除外的大部分权限
--
-- 3. 教师账号
--    用户名: teacher1 / teacher2 / teacher3
--    密码: admin123
--    说明: 拥有查看和基础操作权限
--
-- ⚠️ 重要提示：
-- 1. 首次部署后请立即修改所有默认密码！
-- 2. 建议为每个账号设置不同的强密码
-- 3. 定期更换密码以确保系统安全
-- 4. 默认租户ID为1，所有初始数据都属于该租户
-- ============================================================
