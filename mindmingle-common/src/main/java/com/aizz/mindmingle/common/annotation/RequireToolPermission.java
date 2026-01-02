package com.aizz.mindmingle.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 工具权限注解
 * 用于标记需要工具级权限校验的接口
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireToolPermission {

    /**
     * 工具权限级别
     * 1-查看(view), 2-编辑(edit), 3-管理(manage)
     */
    int value() default 1;

    /**
     * 工具ID参数名称
     * 默认从方法参数中获取名为toolId的参数
     */
    String toolIdParam() default "toolId";
}
