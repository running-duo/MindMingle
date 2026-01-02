package com.aizz.mindmingle.infrastructure.mybatis;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 多租户处理器 - 自动添加租户ID过滤条件
 */
public class XiaofengTenantHandler implements TenantLineHandler {

    private static final Logger log = LoggerFactory.getLogger(XiaofengTenantHandler.class);

    /**
     * 不需要租户隔离的表
     */
    private static final List<String> IGNORE_TABLES = Arrays.asList(
        "t_tenant",              // 租户表本身不需要过滤
        "t_permission",          // 权限表(系统级)
        "t_template",            // 模板表(部分系统模板)
        "t_user_role",           // 用户角色关联表(通过user表间接过滤)
        "t_role_permission",     // 角色权限关联表(通过role表间接过滤)
        "t_menu_permission"      // 菜单权限关联表(通过menu表间接过滤)
    );

    @Override
    public Expression getTenantId() {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            log.warn("TenantContext.getTenantId() returned null, using default tenant ID: 1");
            return new LongValue(1L);
        }
        return new LongValue(tenantId);
    }

    @Override
    public String getTenantIdColumn() {
        return "tenant_id";
    }

    @Override
    public boolean ignoreTable(String tableName) {
        // 忽略不需要租户隔离的表
        return IGNORE_TABLES.contains(tableName.toLowerCase());
    }
}
