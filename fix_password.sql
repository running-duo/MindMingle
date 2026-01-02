-- ============================================================
-- 修复用户密码的 SQL 脚本
-- ============================================================
-- 说明：将所有用户的密码重置为 admin123
-- BCrypt 加密后的密码：$2a$10$5ZF7d7WvXqEzL.oZHqYPce5L7JZr0X3qQJ2K8YnGvQqGQ1YJ3qN3m
-- ============================================================

-- 更新所有用户的密码为 admin123
UPDATE t_user SET password = '$2a$10$5ZF7d7WvXqEzL.oZHqYPce5L7JZr0X3qQJ2K8YnGvQqGQ1YJ3qN3m' WHERE 1=1;

-- 验证更新
SELECT id, username, real_name, LEFT(password, 20) as password_prefix FROM t_user;
