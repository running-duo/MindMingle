package com.aizz.mindmingle.service;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.PageQueryDTO;
import com.aizz.mindmingle.domain.dto.RoleDTO;
import com.aizz.mindmingle.domain.vo.RoleVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService {

    /**
     * 创建角色
     */
    Response<RoleVO> createRole(RoleDTO dto);

    /**
     * 更新角色
     */
    Response<RoleVO> updateRole(Long id, RoleDTO dto);

    /**
     * 删除角色
     */
    Response<Void> deleteRole(Long id);

    /**
     * 获取角色详情
     */
    Response<RoleVO> getRoleById(Long id);

    /**
     * 分页查询角色列表
     */
    Response<PageInfo<RoleVO>> listRoles(PageQueryDTO dto);

    /**
     * 获取用户的角色列表
     */
    Response<List<RoleVO>> getUserRoles(Long userId);

    /**
     * 为角色分配权限
     */
    Response<Void> assignPermissions(Long roleId, List<Long> permissionIds);
}
