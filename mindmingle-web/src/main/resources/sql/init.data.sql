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
-- 1. Initial Users (初始用户数据)
-- ============================================================

-- 插入系统用户
-- 默认密码: admin123 (BCrypt加密后: $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi)
INSERT INTO `t_user` (`id`, `username`, `password`, `real_name`, `phone`, `email`, `avatar`, `status`, `remark`, `create_time`, `update_time`)
VALUES
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '超级管理员', '13800138000', 'admin@mindmingle.com', NULL, 1, '系统默认超级管理员账号', NOW(), NOW()),
(2, 'teacher1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张老师', '13800138001', 'teacher1@mindmingle.com', NULL, 1, '语文老师', NOW(), NOW()),
(3, 'teacher2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李老师', '13800138002', 'teacher2@mindmingle.com', NULL, 1, '数学老师', NOW(), NOW()),
(4, 'teacher3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '王老师', '13800138003', 'teacher3@mindmingle.com', NULL, 1, '英语老师', NOW(), NOW()),
(5, 'manager', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', '13800138004', 'manager@mindmingle.com', NULL, 1, '普通管理员账号', NOW(), NOW());

-- ============================================================
-- 2. Initial Roles (初始角色数据)
-- ============================================================

INSERT INTO `t_role` (`id`, `role_code`, `role_name`, `sort`, `status`, `remark`, `create_time`, `update_time`)
VALUES
(1, 'ROLE_SUPER_ADMIN', '超级管理员', 1, 1, '拥有系统所有权限', NOW(), NOW()),
(2, 'ROLE_ADMIN', '系统管理员', 2, 1, '拥有系统大部分权限', NOW(), NOW()),
(3, 'ROLE_TEACHER', '教师', 3, 1, '教师角色，可查看学生信息和教学管理', NOW(), NOW()),
(4, 'ROLE_USER', '普通用户', 4, 1, '普通用户角色，基础权限', NOW(), NOW());

-- ============================================================
-- 3. Initial User-Role Relations (初始用户角色关联)
-- ============================================================

INSERT INTO `t_user_role` (`user_id`, `role_id`, `create_time`)
VALUES
(1, 1, NOW()),  -- admin 拥有超级管理员角色
(2, 3, NOW()),  -- teacher1 拥有教师角色
(3, 3, NOW()),  -- teacher2 拥有教师角色
(4, 3, NOW()),  -- teacher3 拥有教师角色
(5, 2, NOW());  -- manager 拥有系统管理员角色

-- ============================================================
-- 4. Initial Permissions (初始权限数据)
-- ============================================================

-- 系统管理模块
INSERT INTO `t_permission` (`id`, `parent_id`, `permission_code`, `permission_name`, `permission_type`, `path`, `icon`, `component`, `sort`, `status`, `remark`, `create_time`, `update_time`)
VALUES
-- 一级菜单
(1, 0, 'system', '系统管理', 1, '/system', 'system', NULL, 1, 1, '系统管理模块', NOW(), NOW()),
(2, 0, 'wechat', '微信管理', 1, '/wechat', 'wechat', NULL, 2, 1, '微信小程序管理', NOW(), NOW()),
(3, 0, 'message', '消息管理', 1, '/message', 'message', NULL, 3, 1, '消息推送管理', NOW(), NOW()),
(4, 0, 'monitor', '系统监控', 1, '/monitor', 'monitor', NULL, 4, 1, '系统监控模块', NOW(), NOW()),

-- 系统管理 - 二级菜单
(101, 1, 'system:user', '用户管理', 1, '/system/user', 'user', 'system/user/index', 1, 1, '用户管理页面', NOW(), NOW()),
(102, 1, 'system:role', '角色管理', 1, '/system/role', 'role', 'system/role/index', 2, 1, '角色管理页面', NOW(), NOW()),
(103, 1, 'system:permission', '权限管理', 1, '/system/permission', 'permission', 'system/permission/index', 3, 1, '权限管理页面', NOW(), NOW()),
(104, 1, 'system:config', '系统配置', 1, '/system/config', 'config', 'system/config/index', 4, 1, '系统配置页面', NOW(), NOW()),

-- 用户管理 - 按钮权限
(10101, 101, 'system:user:list', '查看用户', 2, NULL, NULL, NULL, 1, 1, '查看用户列表', NOW(), NOW()),
(10102, 101, 'system:user:add', '新增用户', 2, NULL, NULL, NULL, 2, 1, '新增用户', NOW(), NOW()),
(10103, 101, 'system:user:edit', '编辑用户', 2, NULL, NULL, NULL, 3, 1, '编辑用户', NOW(), NOW()),
(10104, 101, 'system:user:delete', '删除用户', 2, NULL, NULL, NULL, 4, 1, '删除用户', NOW(), NOW()),
(10105, 101, 'system:user:reset', '重置密码', 2, NULL, NULL, NULL, 5, 1, '重置用户密码', NOW(), NOW()),
(10106, 101, 'system:user:export', '导出用户', 2, NULL, NULL, NULL, 6, 1, '导出用户数据', NOW(), NOW()),

-- 角色管理 - 按钮权限
(10201, 102, 'system:role:list', '查看角色', 2, NULL, NULL, NULL, 1, 1, '查看角色列表', NOW(), NOW()),
(10202, 102, 'system:role:add', '新增角色', 2, NULL, NULL, NULL, 2, 1, '新增角色', NOW(), NOW()),
(10203, 102, 'system:role:edit', '编辑角色', 2, NULL, NULL, NULL, 3, 1, '编辑角色', NOW(), NOW()),
(10204, 102, 'system:role:delete', '删除角色', 2, NULL, NULL, NULL, 4, 1, '删除角色', NOW(), NOW()),
(10205, 102, 'system:role:auth', '角色授权', 2, NULL, NULL, NULL, 5, 1, '角色权限分配', NOW(), NOW()),

-- 权限管理 - 按钮权限
(10301, 103, 'system:permission:list', '查看权限', 2, NULL, NULL, NULL, 1, 1, '查看权限列表', NOW(), NOW()),
(10302, 103, 'system:permission:add', '新增权限', 2, NULL, NULL, NULL, 2, 1, '新增权限', NOW(), NOW()),
(10303, 103, 'system:permission:edit', '编辑权限', 2, NULL, NULL, NULL, 3, 1, '编辑权限', NOW(), NOW()),
(10304, 103, 'system:permission:delete', '删除权限', 2, NULL, NULL, NULL, 4, 1, '删除权限', NOW(), NOW()),

-- 系统配置 - 按钮权限
(10401, 104, 'system:config:list', '查看配置', 2, NULL, NULL, NULL, 1, 1, '查看系统配置', NOW(), NOW()),
(10402, 104, 'system:config:edit', '修改配置', 2, NULL, NULL, NULL, 2, 1, '修改系统配置', NOW(), NOW()),

-- 微信管理 - 二级菜单
(201, 2, 'wechat:user', '微信用户', 1, '/wechat/user', 'user', 'wechat/user/index', 1, 1, '微信用户管理', NOW(), NOW()),

-- 微信用户 - 按钮权限
(20101, 201, 'wechat:user:list', '查看用户', 2, NULL, NULL, NULL, 1, 1, '查看微信用户列表', NOW(), NOW()),
(20102, 201, 'wechat:user:detail', '用户详情', 2, NULL, NULL, NULL, 2, 1, '查看微信用户详情', NOW(), NOW()),
(20103, 201, 'wechat:user:status', '启用/禁用', 2, NULL, NULL, NULL, 3, 1, '启用或禁用微信用户', NOW(), NOW()),
(20104, 201, 'wechat:user:export', '导出用户', 2, NULL, NULL, NULL, 4, 1, '导出微信用户数据', NOW(), NOW()),

-- 消息管理 - 二级菜单
(301, 3, 'message:record', '消息记录', 1, '/message/record', 'record', 'message/record/index', 1, 1, '消息推送记录', NOW(), NOW()),
(302, 3, 'message:template', '消息模板', 1, '/message/template', 'template', 'message/template/index', 2, 1, '消息模板管理', NOW(), NOW()),

-- 消息记录 - 按钮权限
(30101, 301, 'message:record:list', '查看记录', 2, NULL, NULL, NULL, 1, 1, '查看消息记录', NOW(), NOW()),
(30102, 301, 'message:record:detail', '记录详情', 2, NULL, NULL, NULL, 2, 1, '查看消息详情', NOW(), NOW()),
(30103, 301, 'message:record:resend', '重新发送', 2, NULL, NULL, NULL, 3, 1, '重新发送失败消息', NOW(), NOW()),
(30104, 301, 'message:record:delete', '删除记录', 2, NULL, NULL, NULL, 4, 1, '删除消息记录', NOW(), NOW()),

-- 系统监控 - 二级菜单
(401, 4, 'monitor:loginlog', '登录日志', 1, '/monitor/loginlog', 'log', 'monitor/loginlog/index', 1, 1, '登录日志监控', NOW(), NOW()),
(402, 4, 'monitor:operlog', '操作日志', 1, '/monitor/operlog', 'log', 'monitor/operlog/index', 2, 1, '操作日志监控', NOW(), NOW());

-- ============================================================
-- 5. Initial Role-Permission Relations (初始角色权限关联)
-- ============================================================

-- 超级管理员拥有所有权限
INSERT INTO `t_role_permission` (`role_id`, `permission_id`, `create_time`)
SELECT 1, `id`, NOW() FROM `t_permission` WHERE `status` = 1;

-- 系统管理员拥有除删除外的大部分权限
INSERT INTO `t_role_permission` (`role_id`, `permission_id`, `create_time`)
SELECT 2, `id`, NOW() FROM `t_permission`
WHERE `status` = 1 AND `permission_code` NOT LIKE '%delete%';

-- 教师角色拥有查看和基础操作权限
INSERT INTO `t_role_permission` (`role_id`, `permission_id`, `create_time`)
VALUES
-- 微信管理模块
(3, 2, NOW()),      -- 微信管理模块
(3, 201, NOW()),    -- 微信用户菜单
(3, 20101, NOW()),  -- 查看微信用户
(3, 20102, NOW()),  -- 微信用户详情
-- 消息管理模块
(3, 3, NOW()),      -- 消息管理模块
(3, 301, NOW()),    -- 消息记录菜单
(3, 30101, NOW()),  -- 查看消息记录
(3, 30102, NOW()),  -- 消息详情
-- 系统监控模块
(3, 4, NOW()),      -- 系统监控模块
(3, 401, NOW()),    -- 登录日志菜单
(3, 402, NOW());    -- 操作日志菜单

-- ============================================================
-- 6. Initial WeChat Users (初始微信用户数据 - 测试用)
-- ============================================================

INSERT INTO `t_wx_user` (`id`, `open_id`, `union_id`, `nickname`, `gender`, `phone`, `status`, `remark`, `create_time`, `update_time`)
VALUES
(1, 'test_openid_001', 'test_unionid_001', '测试用户1', 1, '13900000001', 1, '测试微信用户', NOW(), NOW()),
(2, 'test_openid_002', 'test_unionid_002', '测试用户2', 2, '13900000002', 1, '测试微信用户', NOW(), NOW()),
(3, 'test_openid_003', NULL, '测试用户3', 0, NULL, 1, '测试微信用户', NOW(), NOW());

-- ============================================================
-- 7. Initial System Configuration (初始系统配置)
-- ============================================================

INSERT INTO `t_system_config` (`config_key`, `config_value`, `config_type`, `config_group`, `description`, `is_encrypted`, `create_time`, `update_time`)
VALUES
-- 系统基础配置
('system.name', 'MindMingle', 'string', 'system', '系统名称', 0, NOW(), NOW()),
('system.version', '1.0.0', 'string', 'system', '系统版本', 0, NOW(), NOW()),
('system.copyright', '© 2026 MindMingle', 'string', 'system', '版权信息', 0, NOW(), NOW()),
('system.recordNumber', '', 'string', 'system', 'ICP备案号', 0, NOW(), NOW()),

-- 用户配置
('user.default.password', 'User123456', 'string', 'user', '用户默认密码', 0, NOW(), NOW()),
('user.password.expire.days', '90', 'number', 'user', '密码过期天数（0表示永不过期）', 0, NOW(), NOW()),
('user.max.login.fail', '5', 'number', 'user', '最大登录失败次数', 0, NOW(), NOW()),
('user.password.min.length', '6', 'number', 'user', '密码最小长度', 0, NOW(), NOW()),

-- 安全配置
('security.jwt.expire', '3600', 'number', 'security', 'JWT Token过期时间（秒）', 0, NOW(), NOW()),
('security.session.timeout', '1800', 'number', 'security', '会话超时时间（秒）', 0, NOW(), NOW()),
('security.enable.captcha', 'false', 'boolean', 'security', '是否启用验证码', 0, NOW(), NOW()),
('security.captcha.length', '4', 'number', 'security', '验证码长度', 0, NOW(), NOW()),
('security.enable.csrf', 'false', 'boolean', 'security', '是否启用CSRF防护', 0, NOW(), NOW()),

-- 微信配置
('wechat.token.expire', '7200', 'number', 'wechat', '微信Access Token过期时间（秒）', 0, NOW(), NOW()),
('wechat.message.retry', '3', 'number', 'wechat', '消息发送重试次数', 0, NOW(), NOW()),
('wechat.request.timeout', '5000', 'number', 'wechat', '微信API请求超时时间（毫秒）', 0, NOW(), NOW()),

-- 消息配置
('message.push.enable', 'true', 'boolean', 'message', '是否启用消息推送', 0, NOW(), NOW()),
('message.push.delay', '0', 'number', 'message', '消息推送延迟（秒）', 0, NOW(), NOW()),
('message.batch.size', '100', 'number', 'message', '批量发送消息数量', 0, NOW(), NOW()),

-- 文件上传配置
('upload.max.size', '10485760', 'number', 'upload', '文件上传最大大小（字节，默认10MB）', 0, NOW(), NOW()),
('upload.allowed.types', 'jpg,jpeg,png,gif,pdf,doc,docx,xls,xlsx,ppt,pptx,zip,rar', 'string', 'upload', '允许上传的文件类型', 0, NOW(), NOW()),
('upload.path', '/uploads', 'string', 'upload', '文件上传路径', 0, NOW(), NOW()),
('upload.image.max.size', '5242880', 'number', 'upload', '图片上传最大大小（字节，默认5MB）', 0, NOW(), NOW()),

-- 日志配置
('log.login.retention.days', '90', 'number', 'log', '登录日志保留天数', 0, NOW(), NOW()),
('log.operation.retention.days', '30', 'number', 'log', '操作日志保留天数', 0, NOW(), NOW()),
('log.enable.detail', 'true', 'boolean', 'log', '是否记录详细日志', 0, NOW(), NOW());

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
-- ============================================================
