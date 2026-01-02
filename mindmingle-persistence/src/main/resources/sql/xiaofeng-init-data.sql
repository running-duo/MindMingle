-- Xiaofeng Platform Initial Data
-- Predefined Roles, Permissions, and Menus

-- ========================================
-- 1. 系统角色 (System Roles)
-- ========================================
INSERT INTO t_role (id, tenant_id, role_code, role_name, role_type, description, is_system, sort_order) VALUES
(1, NULL, 'super_admin', '超级管理员', 0, '平台超级管理员，拥有所有权限', 1, 1),
(2, NULL, 'tenant_admin', '租户管理员', 1, '租户管理员，管理租户内的所有资源和用户', 1, 2),
(3, NULL, 'tool_creator', '工具创建者', 2, '工具创建者，可以创建和管理自己的工具', 1, 3),
(4, NULL, 'tool_operator', '工具操作员', 2, '工具操作员，可以使用和编辑工具数据', 1, 4),
(5, NULL, 'data_viewer', '数据查看员', 2, '数据查看员，仅可查看工具数据', 1, 5),
(6, NULL, 'end_user', '终端用户', 2, '终端用户，仅可填写和提交工具表单', 1, 6),
(7, NULL, 'developer', '开发者', 0, '开发者，可访问API和技术文档', 1, 7);

-- ========================================
-- 2. 系统权限 (System Permissions)
-- ========================================
-- 租户管理权限
INSERT INTO t_permission (id, permission_code, permission_name, permission_type, resource_type, resource_action, parent_id, description, sort_order) VALUES
(100, 'tenant:view', '查看租户', 3, 'tenant', 'view', 0, '查看租户信息', 100),
(101, 'tenant:create', '创建租户', 3, 'tenant', 'create', 0, '创建新租户', 101),
(102, 'tenant:edit', '编辑租户', 3, 'tenant', 'edit', 0, '编辑租户信息', 102),
(103, 'tenant:delete', '删除租户', 3, 'tenant', 'delete', 0, '删除租户', 103),
(104, 'tenant:config', '配置租户', 3, 'tenant', 'config', 0, '配置租户参数', 104);

-- 用户管理权限
INSERT INTO t_permission (id, permission_code, permission_name, permission_type, resource_type, resource_action, parent_id, description, sort_order) VALUES
(200, 'user:view', '查看用户', 3, 'user', 'view', 0, '查看用户列表和详情', 200),
(201, 'user:create', '创建用户', 3, 'user', 'create', 0, '创建新用户', 201),
(202, 'user:edit', '编辑用户', 3, 'user', 'edit', 0, '编辑用户信息', 202),
(203, 'user:delete', '删除用户', 3, 'user', 'delete', 0, '删除用户', 203),
(204, 'user:assign_role', '分配角色', 3, 'user', 'assign_role', 0, '为用户分配角色', 204),
(205, 'user:reset_password', '重置密码', 3, 'user', 'reset_password', 0, '重置用户密码', 205);

-- 工具管理权限
INSERT INTO t_permission (id, permission_code, permission_name, permission_type, resource_type, resource_action, parent_id, description, sort_order) VALUES
(300, 'tool:view', '查看工具', 3, 'tool', 'view', 0, '查看工具列表和详情', 300),
(301, 'tool:create', '创建工具', 3, 'tool', 'create', 0, '创建新工具', 301),
(302, 'tool:edit', '编辑工具', 3, 'tool', 'edit', 0, '编辑工具配置', 302),
(303, 'tool:delete', '删除工具', 3, 'tool', 'delete', 0, '删除工具', 303),
(304, 'tool:publish', '发布工具', 3, 'tool', 'publish', 0, '发布工具为公开', 304),
(305, 'tool:config', '配置工具', 3, 'tool', 'config', 0, '配置工具页面和字段', 305),
(306, 'tool:member', '管理协作者', 3, 'tool', 'member', 0, '添加/删除工具协作者', 306);

-- 模板管理权限
INSERT INTO t_permission (id, permission_code, permission_name, permission_type, resource_type, resource_action, parent_id, description, sort_order) VALUES
(400, 'template:view', '查看模板', 3, 'template', 'view', 0, '查看模板列表和详情', 400),
(401, 'template:create', '创建模板', 3, 'template', 'create', 0, '创建新模板', 401),
(402, 'template:edit', '编辑模板', 3, 'template', 'edit', 0, '编辑模板配置', 402),
(403, 'template:delete', '删除模板', 3, 'template', 'delete', 0, '删除模板', 403),
(404, 'template:use', '使用模板', 3, 'template', 'use', 0, '基于模板创建工具', 404);

-- 数据管理权限
INSERT INTO t_permission (id, permission_code, permission_name, permission_type, resource_type, resource_action, parent_id, description, sort_order) VALUES
(500, 'data:view', '查看数据', 3, 'submission', 'view', 0, '查看提交数据', 500),
(501, 'data:create', '创建数据', 3, 'submission', 'create', 0, '创建/提交数据', 501),
(502, 'data:edit', '编辑数据', 3, 'submission', 'edit', 0, '编辑提交数据', 502),
(503, 'data:delete', '删除数据', 3, 'submission', 'delete', 0, '删除提交数据', 503),
(504, 'data:export', '导出数据', 3, 'submission', 'export', 0, '导出数据为Excel/CSV', 504),
(505, 'data:review', '审核数据', 3, 'submission', 'review', 0, '审核提交的数据', 505);

-- 角色权限管理
INSERT INTO t_permission (id, permission_code, permission_name, permission_type, resource_type, resource_action, parent_id, description, sort_order) VALUES
(600, 'role:view', '查看角色', 3, 'role', 'view', 0, '查看角色列表', 600),
(601, 'role:create', '创建角色', 3, 'role', 'create', 0, '创建新角色', 601),
(602, 'role:edit', '编辑角色', 3, 'role', 'edit', 0, '编辑角色信息', 602),
(603, 'role:delete', '删除角色', 3, 'role', 'delete', 0, '删除角色', 603),
(604, 'role:assign_permission', '分配权限', 3, 'role', 'assign_permission', 0, '为角色分配权限', 604);

-- 菜单管理权限
INSERT INTO t_permission (id, permission_code, permission_name, permission_type, resource_type, resource_action, parent_id, description, sort_order) VALUES
(700, 'menu:view', '查看菜单', 3, 'menu', 'view', 0, '查看菜单列表', 700),
(701, 'menu:create', '创建菜单', 3, 'menu', 'create', 0, '创建新菜单', 701),
(702, 'menu:edit', '编辑菜单', 3, 'menu', 'edit', 0, '编辑菜单信息', 702),
(703, 'menu:delete', '删除菜单', 3, 'menu', 'delete', 0, '删除菜单', 703);

-- ========================================
-- 3. 角色权限关联 (Role-Permission Mapping)
-- ========================================
-- super_admin: 所有权限
INSERT INTO t_role_permission (role_id, permission_id) VALUES
-- 租户权限
(1, 100), (1, 101), (1, 102), (1, 103), (1, 104),
-- 用户权限
(1, 200), (1, 201), (1, 202), (1, 203), (1, 204), (1, 205),
-- 工具权限
(1, 300), (1, 301), (1, 302), (1, 303), (1, 304), (1, 305), (1, 306),
-- 模板权限
(1, 400), (1, 401), (1, 402), (1, 403), (1, 404),
-- 数据权限
(1, 500), (1, 501), (1, 502), (1, 503), (1, 504), (1, 505),
-- 角色权限
(1, 600), (1, 601), (1, 602), (1, 603), (1, 604),
-- 菜单权限
(1, 700), (1, 701), (1, 702), (1, 703);

-- tenant_admin: 租户内所有资源管理权限
INSERT INTO t_role_permission (role_id, permission_id) VALUES
-- 用户权限
(2, 200), (2, 201), (2, 202), (2, 203), (2, 204), (2, 205),
-- 工具权限
(2, 300), (2, 301), (2, 302), (2, 303), (2, 304), (2, 305), (2, 306),
-- 模板权限
(2, 400), (2, 404),
-- 数据权限
(2, 500), (2, 501), (2, 502), (2, 503), (2, 504), (2, 505),
-- 角色权限
(2, 600), (2, 601), (2, 602), (2, 603), (2, 604);

-- tool_creator: 工具创建和管理权限
INSERT INTO t_role_permission (role_id, permission_id) VALUES
(3, 300), (3, 301), (3, 302), (3, 303), (3, 304), (3, 305), (3, 306),
(3, 400), (3, 404),
(3, 500), (3, 501), (3, 502), (3, 503), (3, 504), (3, 505);

-- tool_operator: 工具使用和数据编辑权限
INSERT INTO t_role_permission (role_id, permission_id) VALUES
(4, 300),
(4, 500), (4, 501), (4, 502), (4, 503), (4, 504);

-- data_viewer: 仅查看权限
INSERT INTO t_role_permission (role_id, permission_id) VALUES
(5, 300),
(5, 500), (5, 504);

-- end_user: 终端用户权限
INSERT INTO t_role_permission (role_id, permission_id) VALUES
(6, 300),
(6, 501);

-- developer: 开发者权限
INSERT INTO t_role_permission (role_id, permission_id) VALUES
(7, 300), (7, 301), (7, 302), (7, 305),
(7, 400), (7, 401), (7, 402), (7, 404);

-- ========================================
-- 4. 系统菜单 (System Menus)
-- ========================================
-- 一级菜单
INSERT INTO t_menu (id, tenant_id, menu_name, menu_code, menu_type, parent_id, path, component, icon, sort_order, is_visible, status) VALUES
(1, NULL, '工作台', 'dashboard', 1, 0, '/dashboard', NULL, 'dashboard', 1, 1, 1),
(2, NULL, '工具管理', 'tool_management', 1, 0, '/tools', NULL, 'tool', 2, 1, 1),
(3, NULL, '模板中心', 'template_center', 1, 0, '/templates', NULL, 'template', 3, 1, 1),
(4, NULL, '租户管理', 'tenant_management', 1, 0, '/tenants', NULL, 'apartment', 4, 1, 1),
(5, NULL, '用户管理', 'user_management', 1, 0, '/users', NULL, 'user', 5, 1, 1),
(6, NULL, '系统设置', 'system_settings', 1, 0, '/settings', NULL, 'setting', 6, 1, 1);

-- 二级菜单 - 工具管理
INSERT INTO t_menu (id, tenant_id, menu_name, menu_code, menu_type, parent_id, path, component, icon, sort_order, is_visible, status) VALUES
(201, NULL, '我的工具', 'my_tools', 2, 2, '/tools/my', 'views/tools/MyTools', NULL, 1, 1, 1),
(202, NULL, '全部工具', 'all_tools', 2, 2, '/tools/all', 'views/tools/AllTools', NULL, 2, 1, 1),
(203, NULL, '创建工具', 'create_tool', 2, 2, '/tools/create', 'views/tools/CreateTool', NULL, 3, 1, 1),
(204, NULL, '工具配置', 'tool_config', 2, 2, '/tools/:id/config', 'views/tools/ToolConfig', NULL, 4, 0, 1),
(205, NULL, '工具数据', 'tool_data', 2, 2, '/tools/:id/data', 'views/tools/ToolData', NULL, 5, 0, 1);

-- 二级菜单 - 模板中心
INSERT INTO t_menu (id, tenant_id, menu_name, menu_code, menu_type, parent_id, path, component, icon, sort_order, is_visible, status) VALUES
(301, NULL, '模板列表', 'template_list', 2, 3, '/templates/list', 'views/templates/TemplateList', NULL, 1, 1, 1),
(302, NULL, '创建模板', 'create_template', 2, 3, '/templates/create', 'views/templates/CreateTemplate', NULL, 2, 1, 1),
(303, NULL, '模板分类', 'template_category', 2, 3, '/templates/category', 'views/templates/TemplateCategory', NULL, 3, 1, 1);

-- 二级菜单 - 租户管理
INSERT INTO t_menu (id, tenant_id, menu_name, menu_code, menu_type, parent_id, path, component, icon, sort_order, is_visible, status) VALUES
(401, NULL, '租户列表', 'tenant_list', 2, 4, '/tenants/list', 'views/tenants/TenantList', NULL, 1, 1, 1),
(402, NULL, '创建租户', 'create_tenant', 2, 4, '/tenants/create', 'views/tenants/CreateTenant', NULL, 2, 1, 1),
(403, NULL, '租户配置', 'tenant_config', 2, 4, '/tenants/:id/config', 'views/tenants/TenantConfig', NULL, 3, 0, 1);

-- 二级菜单 - 用户管理
INSERT INTO t_menu (id, tenant_id, menu_name, menu_code, menu_type, parent_id, path, component, icon, sort_order, is_visible, status) VALUES
(501, NULL, '用户列表', 'user_list', 2, 5, '/users/list', 'views/users/UserList', NULL, 1, 1, 1),
(502, NULL, '创建用户', 'create_user', 2, 5, '/users/create', 'views/users/CreateUser', NULL, 2, 1, 1),
(503, NULL, '角色管理', 'role_management', 2, 5, '/users/roles', 'views/users/RoleManagement', NULL, 3, 1, 1),
(504, NULL, '权限管理', 'permission_management', 2, 5, '/users/permissions', 'views/users/PermissionManagement', NULL, 4, 1, 1);

-- 二级菜单 - 系统设置
INSERT INTO t_menu (id, tenant_id, menu_name, menu_code, menu_type, parent_id, path, component, icon, sort_order, is_visible, status) VALUES
(601, NULL, '菜单配置', 'menu_config', 2, 6, '/settings/menu', 'views/settings/MenuConfig', NULL, 1, 1, 1),
(602, NULL, '系统配置', 'system_config', 2, 6, '/settings/system', 'views/settings/SystemConfig', NULL, 2, 1, 1),
(603, NULL, 'API文档', 'api_docs', 2, 6, '/settings/api-docs', 'views/settings/ApiDocs', NULL, 3, 1, 1);

-- 三级菜单 - 按钮权限
INSERT INTO t_menu (id, tenant_id, menu_name, menu_code, menu_type, parent_id, path, component, icon, sort_order, is_visible, status) VALUES
(20101, NULL, '编辑工具', 'btn_tool_edit', 3, 201, NULL, NULL, NULL, 1, 0, 1),
(20102, NULL, '删除工具', 'btn_tool_delete', 3, 201, NULL, NULL, NULL, 2, 0, 1),
(20103, NULL, '发布工具', 'btn_tool_publish', 3, 201, NULL, NULL, NULL, 3, 0, 1),
(20501, NULL, '导出数据', 'btn_data_export', 3, 205, NULL, NULL, NULL, 1, 0, 1),
(20502, NULL, '删除数据', 'btn_data_delete', 3, 205, NULL, NULL, NULL, 2, 0, 1),
(20503, NULL, '审核数据', 'btn_data_review', 3, 205, NULL, NULL, NULL, 3, 0, 1);

-- ========================================
-- 5. 菜单权限关联 (Menu-Permission Mapping)
-- ========================================
-- 工具管理菜单关联权限
INSERT INTO t_menu_permission (menu_id, permission_id) VALUES
(201, 300), (202, 300), (203, 301), (204, 302), (204, 305), (205, 500);

-- 模板中心菜单关联权限
INSERT INTO t_menu_permission (menu_id, permission_id) VALUES
(301, 400), (302, 401), (303, 400);

-- 租户管理菜单关联权限
INSERT INTO t_menu_permission (menu_id, permission_id) VALUES
(401, 100), (402, 101), (403, 102), (403, 104);

-- 用户管理菜单关联权限
INSERT INTO t_menu_permission (menu_id, permission_id) VALUES
(501, 200), (502, 201), (503, 600), (503, 601), (504, 600);

-- 系统设置菜单关联权限
INSERT INTO t_menu_permission (menu_id, permission_id) VALUES
(601, 700), (601, 701), (602, 100);

-- 按钮权限关联
INSERT INTO t_menu_permission (menu_id, permission_id) VALUES
(20101, 302), (20102, 303), (20103, 304),
(20501, 504), (20502, 503), (20503, 505);

-- ========================================
-- 6. 创建默认超级管理员账号
-- ========================================
-- Password: admin123 (BCrypt encrypted)
INSERT INTO t_user (id, tenant_id, username, password, nickname, real_name, email, user_type, status) VALUES
(1, 1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lZ9lH0vsQOwvhcMpO', '超级管理员', '系统管理员', 'admin@xiaofeng.com', 0, 1);

-- 分配超级管理员角色
INSERT INTO t_user_role (user_id, role_id, scope_type, scope_id) VALUES
(1, 1, 0, NULL);

-- ========================================
-- 7. 创建默认租户
-- ========================================
INSERT INTO t_tenant (id, tenant_name, tenant_code, contact_name, contact_email, status, max_users, max_tools) VALUES
(1, '默认租户', 'default', '系统管理员', 'admin@xiaofeng.com', 1, 1000, 500);

-- ========================================
-- 8. 系统工具模板 (System Templates)
-- ========================================
INSERT INTO t_template (id, template_name, template_code, template_type, description, is_system, category, tags) VALUES
(1, '基础表单', 'basic_form', 'form', '基础表单模板，包含常用字段类型', 1, '表单', '基础,表单'),
(2, '数据仪表板', 'data_dashboard', 'dashboard', '数据可视化仪表板模板', 1, '仪表板', '数据,可视化'),
(3, '审批流程', 'approval_workflow', 'workflow', '审批流程模板', 1, '流程', '审批,流程'),
(4, '问卷调查', 'survey', 'form', '问卷调查表单模板', 1, '表单', '问卷,调查'),
(5, '报名表', 'registration', 'form', '活动报名表单模板', 1, '表单', '报名,活动');
