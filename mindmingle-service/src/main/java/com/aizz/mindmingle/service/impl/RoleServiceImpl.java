package com.aizz.mindmingle.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.common.ResponseCode;
import com.aizz.mindmingle.domain.dos.PermissionDO;
import com.aizz.mindmingle.domain.dos.RoleDO;
import com.aizz.mindmingle.domain.dos.RolePermissionDO;
import com.aizz.mindmingle.domain.dos.UserDO;
import com.aizz.mindmingle.domain.dos.UserRoleDO;
import com.aizz.mindmingle.domain.dto.PageQueryDTO;
import com.aizz.mindmingle.domain.dto.RoleDTO;
import com.aizz.mindmingle.domain.vo.PermissionVO;
import com.aizz.mindmingle.domain.vo.RoleVO;
import com.aizz.mindmingle.persistence.mapper.PermissionMapper;
import com.aizz.mindmingle.persistence.mapper.RoleMapper;
import com.aizz.mindmingle.persistence.mapper.RolePermissionMapper;
import com.aizz.mindmingle.persistence.mapper.UserMapper;
import com.aizz.mindmingle.persistence.mapper.UserRoleMapper;
import com.aizz.mindmingle.security.Access;
import com.aizz.mindmingle.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现类
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Response<RoleVO> createRole(RoleDTO dto) {
        try {
            // 获取当前用户的租户ID
            Long tenantId = null;
            if (Access.userId() != null) {
                UserDO currentUser = userMapper.selectById(Access.userId());
                if (currentUser != null) {
                    tenantId = currentUser.getTenantId();
                }
            }

            // 验证角色编码在租户内唯一
            LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RoleDO::getRoleCode, dto.roleCode);
            if (tenantId != null) {
                queryWrapper.eq(RoleDO::getTenantId, tenantId);
            }
            Long count = roleMapper.selectCount(queryWrapper);
            if (count > 0) {
                return Response.error(ResponseCode.BAD_REQUEST, "角色编码已存在");
            }

            RoleDO roleDO = new RoleDO();
            roleDO.setTenantId(tenantId);
            roleDO.setRoleCode(dto.roleCode);
            roleDO.setRoleName(dto.roleName);
            roleDO.setRoleType(dto.roleType);
            roleDO.setDescription(dto.description);
            roleDO.setIsSystem(0);
            roleDO.setSortOrder(0);
            roleDO.setCreatedAt(new Date());
            roleDO.setUpdatedAt(new Date());

            if (Access.userId() != null) {
                roleDO.setCreatedBy(Access.userId());
                roleDO.setUpdatedBy(Access.userId());
            }

            roleMapper.insert(roleDO);

            return Response.success(convertToVO(roleDO));
        } catch (Exception e) {
            log.error("Error in createRole", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<RoleVO> updateRole(Long id, RoleDTO dto) {
        try {
            RoleDO roleDO = roleMapper.selectById(id);
            if (roleDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "角色不存在");
            }

            // 检查系统角色不可修改
            if (roleDO.getIsSystem() != null && roleDO.getIsSystem() == 1) {
                return Response.error(ResponseCode.BAD_REQUEST, "系统角色不可修改");
            }

            // 验证角色编码唯一性
            if (StrUtil.isNotBlank(dto.roleCode) && !dto.roleCode.equals(roleDO.getRoleCode())) {
                LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(RoleDO::getRoleCode, dto.roleCode);
                if (roleDO.getTenantId() != null) {
                    queryWrapper.eq(RoleDO::getTenantId, roleDO.getTenantId());
                }
                queryWrapper.ne(RoleDO::getId, id);
                Long count = roleMapper.selectCount(queryWrapper);
                if (count > 0) {
                    return Response.error(ResponseCode.BAD_REQUEST, "角色编码已存在");
                }
            }

            if (StrUtil.isNotBlank(dto.roleCode)) {
                roleDO.setRoleCode(dto.roleCode);
            }
            if (StrUtil.isNotBlank(dto.roleName)) {
                roleDO.setRoleName(dto.roleName);
            }
            if (dto.roleType != null) {
                roleDO.setRoleType(dto.roleType);
            }
            if (StrUtil.isNotBlank(dto.description)) {
                roleDO.setDescription(dto.description);
            }

            roleDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                roleDO.setUpdatedBy(Access.userId());
            }

            roleMapper.updateById(roleDO);

            return Response.success(convertToVO(roleDO));
        } catch (Exception e) {
            log.error("Error in updateRole", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> deleteRole(Long id) {
        try {
            RoleDO roleDO = roleMapper.selectById(id);
            if (roleDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "角色不存在");
            }

            // 检查系统角色不可删除
            if (roleDO.getIsSystem() != null && roleDO.getIsSystem() == 1) {
                return Response.error(ResponseCode.BAD_REQUEST, "系统角色不可删除");
            }

            // 检查角色是否被使用
            LambdaQueryWrapper<UserRoleDO> userRoleWrapper = new LambdaQueryWrapper<>();
            userRoleWrapper.eq(UserRoleDO::getRoleId, id);
            Long userRoleCount = userRoleMapper.selectCount(userRoleWrapper);
            if (userRoleCount > 0) {
                return Response.error(ResponseCode.BAD_REQUEST, "该角色已被用户使用，不可删除");
            }

            // MyBatis-Plus的@TableLogic会自动处理软删除
            roleMapper.deleteById(id);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in deleteRole", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<RoleVO> getRoleById(Long id) {
        try {
            RoleDO roleDO = roleMapper.selectById(id);
            if (roleDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "角色不存在");
            }

            RoleVO roleVO = convertToVO(roleDO);

            // 查询角色权限
            LambdaQueryWrapper<RolePermissionDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RolePermissionDO::getRoleId, id);
            List<RolePermissionDO> rolePermissions = rolePermissionMapper.selectList(queryWrapper);

            if (CollUtil.isNotEmpty(rolePermissions)) {
                List<Long> permissionIds = rolePermissions.stream()
                    .map(RolePermissionDO::getPermissionId)
                    .collect(Collectors.toList());

                List<PermissionDO> permissions = permissionMapper.selectBatchIds(permissionIds);
                List<PermissionVO> permissionVOs = permissions.stream()
                    .map(this::convertPermissionToVO)
                    .collect(Collectors.toList());

                roleVO.setPermissions(permissionVOs);
            }

            return Response.success(roleVO);
        } catch (Exception e) {
            log.error("Error in getRoleById", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<PageInfo<RoleVO>> listRoles(PageQueryDTO dto) {
        try {
            PageHelper.startPage(dto.pageNum != null ? dto.pageNum : 1,
                               dto.pageSize != null ? dto.pageSize : 10);

            LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();

            // 租户过滤：包括租户角色和系统角色(tenant_id为null)
            if (Access.userId() != null) {
                UserDO currentUser = userMapper.selectById(Access.userId());
                if (currentUser != null && currentUser.getTenantId() != null) {
                    queryWrapper.and(wrapper -> wrapper
                        .eq(RoleDO::getTenantId, currentUser.getTenantId())
                        .or()
                        .isNull(RoleDO::getTenantId)
                    );
                }
            }

            // 关键字搜索
            if (StrUtil.isNotBlank(dto.keyword)) {
                queryWrapper.and(wrapper -> wrapper
                    .like(RoleDO::getRoleName, dto.keyword)
                    .or()
                    .like(RoleDO::getRoleCode, dto.keyword)
                );
            }

            queryWrapper.orderByDesc(RoleDO::getCreatedAt);

            List<RoleDO> list = roleMapper.selectList(queryWrapper);
            PageInfo<RoleDO> pageInfo = new PageInfo<>(list);

            List<RoleVO> voList = list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

            PageInfo<RoleVO> result = new PageInfo<>();
            result.setList(voList);
            result.setTotal(pageInfo.getTotal());
            result.setPageNum(pageInfo.getPageNum());
            result.setPageSize(pageInfo.getPageSize());
            result.setPages(pageInfo.getPages());

            return Response.success(result);
        } catch (Exception e) {
            log.error("Error in listRoles", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<List<RoleVO>> getUserRoles(Long userId) {
        try {
            // 查询用户角色关联
            LambdaQueryWrapper<UserRoleDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserRoleDO::getUserId, userId);
            List<UserRoleDO> userRoles = userRoleMapper.selectList(queryWrapper);

            if (CollUtil.isEmpty(userRoles)) {
                return Response.success(new ArrayList<>());
            }

            List<Long> roleIds = userRoles.stream()
                .map(UserRoleDO::getRoleId)
                .collect(Collectors.toList());

            List<RoleDO> roles = roleMapper.selectBatchIds(roleIds);
            List<RoleVO> roleVOs = roles.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

            return Response.success(roleVOs);
        } catch (Exception e) {
            log.error("Error in getUserRoles", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Void> assignPermissions(Long roleId, List<Long> permissionIds) {
        try {
            RoleDO roleDO = roleMapper.selectById(roleId);
            if (roleDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "角色不存在");
            }

            // 删除旧的角色权限关联
            LambdaQueryWrapper<RolePermissionDO> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.eq(RolePermissionDO::getRoleId, roleId);
            rolePermissionMapper.delete(deleteWrapper);

            // 插入新的角色权限关联
            if (CollUtil.isNotEmpty(permissionIds)) {
                for (Long permissionId : permissionIds) {
                    RolePermissionDO rolePermissionDO = new RolePermissionDO();
                    rolePermissionDO.setRoleId(roleId);
                    rolePermissionDO.setPermissionId(permissionId);
                    rolePermissionDO.setCreatedAt(new Date());
                    if (Access.userId() != null) {
                        rolePermissionDO.setCreatedBy(Access.userId());
                    }
                    rolePermissionMapper.insert(rolePermissionDO);
                }
            }

            return Response.success();
        } catch (Exception e) {
            log.error("Error in assignPermissions", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * RoleDO转RoleVO
     */
    private RoleVO convertToVO(RoleDO roleDO) {
        if (roleDO == null) {
            return null;
        }

        RoleVO vo = new RoleVO();
        vo.setId(roleDO.getId());
        vo.setRoleCode(roleDO.getRoleCode());
        vo.setRoleName(roleDO.getRoleName());
        vo.setRoleType(roleDO.getRoleType());
        vo.setDescription(roleDO.getDescription());
        vo.setIsSystem(roleDO.getIsSystem());
        vo.setCreatedAt(roleDO.getCreatedAt());
        vo.setPermissions(new ArrayList<>());

        return vo;
    }

    /**
     * PermissionDO转PermissionVO
     */
    private PermissionVO convertPermissionToVO(PermissionDO permissionDO) {
        if (permissionDO == null) {
            return null;
        }

        PermissionVO vo = new PermissionVO();
        vo.setId(permissionDO.getId());
        vo.setPermissionCode(permissionDO.getPermissionCode());
        vo.setPermissionName(permissionDO.getPermissionName());
        vo.setPermissionType(permissionDO.getPermissionType());
        vo.setResourceType(permissionDO.getResourceType());
        vo.setResourceAction(permissionDO.getResourceAction());
        vo.setParentId(permissionDO.getParentId());
        vo.setDescription(permissionDO.getDescription());

        return vo;
    }
}
