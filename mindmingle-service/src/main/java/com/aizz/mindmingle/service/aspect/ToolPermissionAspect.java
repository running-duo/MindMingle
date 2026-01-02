package com.aizz.mindmingle.service.aspect;

import com.aizz.mindmingle.common.annotation.RequireToolPermission;
import com.aizz.mindmingle.domain.dos.ToolDO;
import com.aizz.mindmingle.domain.dos.ToolMemberDO;
import com.aizz.mindmingle.persistence.mapper.ToolMapper;
import com.aizz.mindmingle.persistence.mapper.ToolMemberMapper;
import com.aizz.mindmingle.security.Access;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 工具权限切面
 * 用于校验用户对工具的访问权限
 */
@Aspect
@Component
public class ToolPermissionAspect {

    private static final Logger log = LoggerFactory.getLogger(ToolPermissionAspect.class);

    @Autowired
    private ToolMapper toolMapper;

    @Autowired
    private ToolMemberMapper toolMemberMapper;

    @Around("@annotation(com.aizz.mindmingle.common.annotation.RequireToolPermission)")
    public Object checkToolPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 获取注解
        RequireToolPermission annotation = method.getAnnotation(RequireToolPermission.class);
        if (annotation == null) {
            return joinPoint.proceed();
        }

        // 获取当前用户ID
        Long currentUserId = Access.userId();
        if (currentUserId == null) {
            throw new AccessDeniedException("用户未登录");
        }

        // 获取工具ID
        Long toolId = getToolIdFromArgs(joinPoint, annotation.toolIdParam());
        if (toolId == null) {
            log.warn("无法获取工具ID, 参数名: {}", annotation.toolIdParam());
            throw new IllegalArgumentException("缺少工具ID参数");
        }

        // 检查工具是否存在
        ToolDO tool = toolMapper.selectById(toolId);
        if (tool == null) {
            throw new IllegalArgumentException("工具不存在");
        }

        // 检查权限
        boolean hasPermission = checkPermission(tool, currentUserId, annotation.value());
        if (!hasPermission) {
            log.warn("用户 {} 对工具 {} 没有权限, 需要权限级别: {}", currentUserId, toolId, annotation.value());
            throw new AccessDeniedException("没有权限访问该工具");
        }

        // 权限检查通过, 继续执行
        return joinPoint.proceed();
    }

    /**
     * 从方法参数中获取工具ID
     */
    private Long getToolIdFromArgs(ProceedingJoinPoint joinPoint, String paramName) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getName().equals(paramName)) {
                Object arg = args[i];
                if (arg instanceof Long) {
                    return (Long) arg;
                } else if (arg instanceof Integer) {
                    return ((Integer) arg).longValue();
                } else if (arg instanceof String) {
                    try {
                        return Long.parseLong((String) arg);
                    } catch (NumberFormatException e) {
                        log.error("无法将参数转换为Long: {}", arg);
                    }
                }
            }
        }

        return null;
    }

    /**
     * 检查用户对工具的权限
     * @param tool 工具
     * @param userId 用户ID
     * @param requiredLevel 需要的权限级别 (1-查看, 2-编辑, 3-管理)
     * @return 是否有权限
     */
    private boolean checkPermission(ToolDO tool, Long userId, int requiredLevel) {
        // 1. 工具创建者拥有所有权限
        if (tool.getCreatorId().equals(userId)) {
            return true;
        }

        // 2. 超级管理员拥有所有权限 (可以通过角色判断)
        if (Access.accessToken() != null && Access.accessToken().roles != null) {
            if (Access.accessToken().roles.contains("super_admin") ||
                Access.accessToken().roles.contains("tenant_admin")) {
                return true;
            }
        }

        // 3. 如果工具是公开的且只需要查看权限
        if (tool.getIsPublic() == 1 && requiredLevel == 1) {
            return true;
        }

        // 4. 检查是否是工具协作者
        LambdaQueryWrapper<ToolMemberDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ToolMemberDO::getToolId, tool.getId())
               .eq(ToolMemberDO::getUserId, userId);

        ToolMemberDO member = toolMemberMapper.selectOne(wrapper);
        if (member != null) {
            // 权限级别: 1-查看, 2-编辑, 3-管理
            // 用户的权限级别必须 >= 需要的权限级别
            return member.getPermissionLevel() >= requiredLevel;
        }

        // 没有权限
        return false;
    }
}
