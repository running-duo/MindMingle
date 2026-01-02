-- Xiaofeng Platform Database Schema
-- Multi-tenant SaaS Platform with RBAC Permission System

-- ========================================
-- 1. 租户表 (Tenant Table)
-- ========================================
CREATE TABLE IF NOT EXISTS t_tenant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '租户ID',
    tenant_name VARCHAR(100) NOT NULL COMMENT '租户名称',
    tenant_code VARCHAR(50) NOT NULL UNIQUE COMMENT '租户编码',
    contact_name VARCHAR(50) COMMENT '联系人姓名',
    contact_phone VARCHAR(20) COMMENT '联系人电话',
    contact_email VARCHAR(100) COMMENT '联系人邮箱',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    max_users INT DEFAULT 100 COMMENT '最大用户数',
    max_tools INT DEFAULT 50 COMMENT '最大工具数',
    expire_time DATETIME COMMENT '过期时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    created_by BIGINT COMMENT '创建人ID',
    updated_by BIGINT COMMENT '更新人ID',
    is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    INDEX idx_tenant_code (tenant_code),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- ========================================
-- 2. 用户表 (User Table) - Enhanced
-- ========================================
CREATE TABLE IF NOT EXISTS t_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码(BCrypt加密)',
    nickname VARCHAR(50) COMMENT '昵称',
    real_name VARCHAR(50) COMMENT '真实姓名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    avatar_url VARCHAR(255) COMMENT '头像URL',
    wx_openid VARCHAR(100) COMMENT '微信OpenID',
    wx_unionid VARCHAR(100) COMMENT '微信UnionID',
    user_type TINYINT NOT NULL DEFAULT 1 COMMENT '用户类型: 0-超级管理员, 1-租户管理员, 2-普通用户',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    last_login_time DATETIME COMMENT '最后登录时间',
    last_login_ip VARCHAR(50) COMMENT '最后登录IP',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    created_by BIGINT COMMENT '创建人ID',
    updated_by BIGINT COMMENT '更新人ID',
    is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    UNIQUE KEY uk_tenant_username (tenant_id, username),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_email (email),
    INDEX idx_phone (phone),
    INDEX idx_wx_openid (wx_openid),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ========================================
-- 3. 工具表 (Tool Table) - Core Business
-- ========================================
CREATE TABLE IF NOT EXISTS t_tool (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '工具ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    tool_name VARCHAR(100) NOT NULL COMMENT '工具名称',
    tool_code VARCHAR(50) NOT NULL COMMENT '工具编码(唯一标识)',
    tool_type VARCHAR(50) NOT NULL COMMENT '工具类型: form-表单工具, dashboard-仪表板, workflow-流程工具',
    description TEXT COMMENT '工具描述',
    icon_url VARCHAR(255) COMMENT '工具图标URL',
    template_id BIGINT COMMENT '关联模板ID(可选)',
    config_json JSON COMMENT '工具配置(JSON格式: 页面配置、字段定义等)',
    creator_id BIGINT NOT NULL COMMENT '创建人ID',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用, 2-草稿',
    is_public TINYINT NOT NULL DEFAULT 0 COMMENT '是否公开: 0-私有, 1-公开',
    sort_order INT DEFAULT 0 COMMENT '排序',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    use_count INT DEFAULT 0 COMMENT '使用次数',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    created_by BIGINT COMMENT '创建人ID',
    updated_by BIGINT COMMENT '更新人ID',
    is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    UNIQUE KEY uk_tenant_tool_code (tenant_id, tool_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_creator_id (creator_id),
    INDEX idx_tool_type (tool_type),
    INDEX idx_template_id (template_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工具表';

-- ========================================
-- 4. 工具模板表 (Tool Template Table)
-- ========================================
CREATE TABLE IF NOT EXISTS t_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '模板ID',
    template_name VARCHAR(100) NOT NULL COMMENT '模板名称',
    template_code VARCHAR(50) NOT NULL UNIQUE COMMENT '模板编码',
    template_type VARCHAR(50) NOT NULL COMMENT '模板类型: form, dashboard, workflow',
    description TEXT COMMENT '模板描述',
    icon_url VARCHAR(255) COMMENT '模板图标URL',
    config_json JSON COMMENT '模板配置(JSON格式)',
    preview_image_url VARCHAR(255) COMMENT '预览图URL',
    is_system TINYINT NOT NULL DEFAULT 0 COMMENT '是否系统模板: 0-否, 1-是',
    category VARCHAR(50) COMMENT '模板分类',
    tags VARCHAR(255) COMMENT '标签(逗号分隔)',
    use_count INT DEFAULT 0 COMMENT '使用次数',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    created_by BIGINT COMMENT '创建人ID',
    updated_by BIGINT COMMENT '更新人ID',
    is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    INDEX idx_template_code (template_code),
    INDEX idx_template_type (template_type),
    INDEX idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工具模板表';

-- ========================================
-- 5. 工具提交数据表 (Tool Submission Table)
-- ========================================
CREATE TABLE IF NOT EXISTS t_submission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '提交ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    tool_id BIGINT NOT NULL COMMENT '工具ID',
    user_id BIGINT NOT NULL COMMENT '提交用户ID',
    submission_data JSON COMMENT '提交数据(JSON格式)',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0-草稿, 1-已提交, 2-审核中, 3-已通过, 4-已驳回',
    submit_time DATETIME COMMENT '提交时间',
    review_user_id BIGINT COMMENT '审核人ID',
    review_time DATETIME COMMENT '审核时间',
    review_comment TEXT COMMENT '审核意见',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    created_by BIGINT COMMENT '创建人ID',
    updated_by BIGINT COMMENT '更新人ID',
    is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_tool_id (tool_id),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_submit_time (submit_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工具提交数据表';

-- ========================================
-- 6. 工具协作者表 (Tool Member Table)
-- ========================================
CREATE TABLE IF NOT EXISTS t_tool_member (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '记录ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    tool_id BIGINT NOT NULL COMMENT '工具ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    permission_level TINYINT NOT NULL DEFAULT 1 COMMENT '权限级别: 1-查看, 2-编辑, 3-管理',
    invited_by BIGINT COMMENT '邀请人ID',
    invited_at DATETIME COMMENT '邀请时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    UNIQUE KEY uk_tool_user (tool_id, user_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_tool_id (tool_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工具协作者表';

-- ========================================
-- 7. 角色表 (Role Table)
-- ========================================
CREATE TABLE IF NOT EXISTS t_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    tenant_id BIGINT COMMENT '租户ID(NULL表示系统角色)',
    role_code VARCHAR(50) NOT NULL COMMENT '角色编码',
    role_name VARCHAR(100) NOT NULL COMMENT '角色名称',
    role_type TINYINT NOT NULL DEFAULT 1 COMMENT '角色类型: 0-系统角色, 1-租户角色, 2-工具角色',
    description TEXT COMMENT '角色描述',
    is_system TINYINT NOT NULL DEFAULT 0 COMMENT '是否系统内置: 0-否, 1-是',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    created_by BIGINT COMMENT '创建人ID',
    updated_by BIGINT COMMENT '更新人ID',
    is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    UNIQUE KEY uk_tenant_role_code (tenant_id, role_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ========================================
-- 8. 权限表 (Permission Table)
-- ========================================
CREATE TABLE IF NOT EXISTS t_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID',
    permission_code VARCHAR(100) NOT NULL UNIQUE COMMENT '权限编码',
    permission_name VARCHAR(100) NOT NULL COMMENT '权限名称',
    permission_type TINYINT NOT NULL DEFAULT 1 COMMENT '权限类型: 1-菜单, 2-按钮, 3-API, 4-数据',
    resource_type VARCHAR(50) COMMENT '资源类型: tenant, tool, template, user等',
    resource_action VARCHAR(50) COMMENT '资源操作: view, create, edit, delete等',
    parent_id BIGINT DEFAULT 0 COMMENT '父权限ID',
    description TEXT COMMENT '权限描述',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    INDEX idx_permission_code (permission_code),
    INDEX idx_resource_type (resource_type),
    INDEX idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ========================================
-- 9. 菜单表 (Menu Table)
-- ========================================
CREATE TABLE IF NOT EXISTS t_menu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '菜单ID',
    tenant_id BIGINT COMMENT '租户ID(NULL表示系统菜单)',
    menu_name VARCHAR(100) NOT NULL COMMENT '菜单名称',
    menu_code VARCHAR(50) NOT NULL COMMENT '菜单编码',
    menu_type TINYINT NOT NULL DEFAULT 1 COMMENT '菜单类型: 1-目录, 2-菜单, 3-按钮',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    path VARCHAR(255) COMMENT '路由路径',
    component VARCHAR(255) COMMENT '组件路径',
    icon VARCHAR(100) COMMENT '图标',
    sort_order INT DEFAULT 0 COMMENT '排序',
    is_visible TINYINT NOT NULL DEFAULT 1 COMMENT '是否可见: 0-隐藏, 1-显示',
    is_cache TINYINT NOT NULL DEFAULT 0 COMMENT '是否缓存: 0-否, 1-是',
    is_frame TINYINT NOT NULL DEFAULT 0 COMMENT '是否外链: 0-否, 1-是',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    created_by BIGINT COMMENT '创建人ID',
    updated_by BIGINT COMMENT '更新人ID',
    is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_menu_code (menu_code),
    INDEX idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ========================================
-- 10. 用户角色关联表 (User-Role Relation)
-- ========================================
CREATE TABLE IF NOT EXISTS t_user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    scope_type TINYINT DEFAULT 0 COMMENT '作用域类型: 0-全局, 1-租户, 2-工具',
    scope_id BIGINT COMMENT '作用域ID(租户ID或工具ID)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by BIGINT COMMENT '创建人ID',
    UNIQUE KEY uk_user_role_scope (user_id, role_id, scope_type, scope_id),
    INDEX idx_user_id (user_id),
    INDEX idx_role_id (role_id),
    INDEX idx_scope (scope_type, scope_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ========================================
-- 11. 角色权限关联表 (Role-Permission Relation)
-- ========================================
CREATE TABLE IF NOT EXISTS t_role_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by BIGINT COMMENT '创建人ID',
    UNIQUE KEY uk_role_permission (role_id, permission_id),
    INDEX idx_role_id (role_id),
    INDEX idx_permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- ========================================
-- 12. 菜单权限关联表 (Menu-Permission Relation)
-- ========================================
CREATE TABLE IF NOT EXISTS t_menu_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by BIGINT COMMENT '创建人ID',
    UNIQUE KEY uk_menu_permission (menu_id, permission_id),
    INDEX idx_menu_id (menu_id),
    INDEX idx_permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限关联表';
