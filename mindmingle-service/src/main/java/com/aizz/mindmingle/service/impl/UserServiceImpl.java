package com.aizz.mindmingle.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.common.ResponseCode;
import com.aizz.mindmingle.domain.dos.RoleDO;
import com.aizz.mindmingle.domain.dos.UserDO;
import com.aizz.mindmingle.domain.dos.UserRoleDO;
import com.aizz.mindmingle.domain.dto.UserDTO;
import com.aizz.mindmingle.domain.dto.UserQueryDTO;
import com.aizz.mindmingle.domain.vo.RoleVO;
import com.aizz.mindmingle.domain.vo.UserVO;
import com.aizz.mindmingle.persistence.mapper.RoleMapper;
import com.aizz.mindmingle.persistence.mapper.UserMapper;
import com.aizz.mindmingle.persistence.mapper.UserRoleMapper;
import com.aizz.mindmingle.security.Access;
import com.aizz.mindmingle.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<UserVO> createUser(UserDTO dto) {
        try {
            // 获取当前用户的租户ID
            UserDO currentUser = null;
            Long tenantId = null;
            if (Access.userId() != null) {
                currentUser = userMapper.selectById(Access.userId());
                if (currentUser != null) {
                    tenantId = currentUser.getTenantId();
                }
            }

            // 验证用户名在租户内唯一
            LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserDO::getUsername, dto.username);
            if (tenantId != null) {
                queryWrapper.eq(UserDO::getTenantId, tenantId);
            }
            Long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                return Response.error(ResponseCode.BAD_REQUEST, "用户名已存在");
            }

            // 创建用户
            UserDO userDO = new UserDO();
            userDO.setTenantId(tenantId);
            userDO.setUsername(dto.username);

            // BCrypt加密密码
            if (StrUtil.isNotBlank(dto.password)) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                userDO.setPassword(encoder.encode(dto.password));
            }

            userDO.setNickname(dto.nickname);
            userDO.setRealName(dto.realName);
            userDO.setEmail(dto.email);
            userDO.setPhone(dto.phone);
            userDO.setAvatarUrl(dto.avatarUrl);
            userDO.setUserType(dto.userType != null ? dto.userType : 0);
            userDO.setStatus(dto.status != null ? dto.status : 1);
            userDO.setCreatedAt(new Date());
            userDO.setUpdatedAt(new Date());

            if (Access.userId() != null) {
                userDO.setCreatedBy(Access.userId());
                userDO.setUpdatedBy(Access.userId());
            }

            userMapper.insert(userDO);

            return Response.success(convertToVO(userDO));
        } catch (Exception e) {
            log.error("Error in createUser", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<UserVO> updateUser(Long id, UserDTO dto) {
        try {
            UserDO userDO = userMapper.selectById(id);
            if (userDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "用户不存在");
            }

            // 验证用户名唯一性(如果修改)
            if (StrUtil.isNotBlank(dto.username) && !dto.username.equals(userDO.getUsername())) {
                LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(UserDO::getUsername, dto.username);
                if (userDO.getTenantId() != null) {
                    queryWrapper.eq(UserDO::getTenantId, userDO.getTenantId());
                }
                queryWrapper.ne(UserDO::getId, id);
                Long count = userMapper.selectCount(queryWrapper);
                if (count > 0) {
                    return Response.error(ResponseCode.BAD_REQUEST, "用户名已存在");
                }
            }

            // 更新字段(不包括密码)
            if (StrUtil.isNotBlank(dto.username)) {
                userDO.setUsername(dto.username);
            }
            if (StrUtil.isNotBlank(dto.nickname)) {
                userDO.setNickname(dto.nickname);
            }
            if (StrUtil.isNotBlank(dto.realName)) {
                userDO.setRealName(dto.realName);
            }
            if (StrUtil.isNotBlank(dto.email)) {
                userDO.setEmail(dto.email);
            }
            if (StrUtil.isNotBlank(dto.phone)) {
                userDO.setPhone(dto.phone);
            }
            if (StrUtil.isNotBlank(dto.avatarUrl)) {
                userDO.setAvatarUrl(dto.avatarUrl);
            }
            if (dto.userType != null) {
                userDO.setUserType(dto.userType);
            }
            if (dto.status != null) {
                userDO.setStatus(dto.status);
            }

            userDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                userDO.setUpdatedBy(Access.userId());
            }

            userMapper.updateById(userDO);

            return Response.success(convertToVO(userDO));
        } catch (Exception e) {
            log.error("Error in updateUser", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> deleteUser(Long id) {
        try {
            UserDO userDO = userMapper.selectById(id);
            if (userDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "用户不存在");
            }

            // MyBatis-Plus的@TableLogic会自动处理软删除
            userMapper.deleteById(id);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in deleteUser", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<UserVO> getUserById(Long id) {
        try {
            UserDO userDO = userMapper.selectById(id);
            if (userDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "用户不存在");
            }

            UserVO userVO = convertToVO(userDO);

            // 查询用户角色
            LambdaQueryWrapper<UserRoleDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserRoleDO::getUserId, id);
            List<UserRoleDO> userRoles = userRoleMapper.selectList(queryWrapper);

            if (CollUtil.isNotEmpty(userRoles)) {
                List<Long> roleIds = userRoles.stream()
                    .map(UserRoleDO::getRoleId)
                    .collect(Collectors.toList());

                List<RoleDO> roles = roleMapper.selectBatchIds(roleIds);
                List<RoleVO> roleVOs = roles.stream()
                    .map(this::convertRoleToVO)
                    .collect(Collectors.toList());

                userVO.setRoles(roleVOs);
            }

            return Response.success(userVO);
        } catch (Exception e) {
            log.error("Error in getUserById", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<UserVO> getUserByUsername(String username, Long tenantId) {
        try {
            LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserDO::getUsername, username);
            if (tenantId != null) {
                queryWrapper.eq(UserDO::getTenantId, tenantId);
            }

            UserDO userDO = userMapper.selectOne(queryWrapper);
            if (userDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "用户不存在");
            }

            return Response.success(convertToVO(userDO));
        } catch (Exception e) {
            log.error("Error in getUserByUsername", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<PageInfo<UserVO>> listUsers(UserQueryDTO dto) {
        try {
            PageHelper.startPage(dto.pageNum != null ? dto.pageNum : 1,
                               dto.pageSize != null ? dto.pageSize : 10);

            LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();

            // 租户过滤
            UserDO currentUser = null;
            if (Access.userId() != null) {
                currentUser = userMapper.selectById(Access.userId());
                if (currentUser != null && currentUser.getTenantId() != null) {
                    queryWrapper.eq(UserDO::getTenantId, currentUser.getTenantId());
                }
            }

            // 关键字搜索
            if (StrUtil.isNotBlank(dto.keyword)) {
                queryWrapper.and(wrapper -> wrapper
                    .like(UserDO::getUsername, dto.keyword)
                    .or()
                    .like(UserDO::getNickname, dto.keyword)
                    .or()
                    .like(UserDO::getRealName, dto.keyword)
                );
            }

            // 用户类型过滤
            if (dto.userType != null) {
                queryWrapper.eq(UserDO::getUserType, dto.userType);
            }

            // 状态过滤
            if (dto.status != null) {
                queryWrapper.eq(UserDO::getStatus, dto.status);
            }

            // 邮箱过滤
            if (StrUtil.isNotBlank(dto.email)) {
                queryWrapper.eq(UserDO::getEmail, dto.email);
            }

            queryWrapper.orderByDesc(UserDO::getCreatedAt);

            List<UserDO> list = userMapper.selectList(queryWrapper);
            PageInfo<UserDO> pageInfo = new PageInfo<>(list);

            List<UserVO> voList = list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

            PageInfo<UserVO> result = new PageInfo<>();
            result.setList(voList);
            result.setTotal(pageInfo.getTotal());
            result.setPageNum(pageInfo.getPageNum());
            result.setPageSize(pageInfo.getPageSize());
            result.setPages(pageInfo.getPages());

            return Response.success(result);
        } catch (Exception e) {
            log.error("Error in listUsers", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Void> assignRoles(Long userId, List<Long> roleIds, Integer scopeType, Long scopeId) {
        try {
            UserDO userDO = userMapper.selectById(userId);
            if (userDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "用户不存在");
            }

            // 删除旧的用户角色关联
            LambdaQueryWrapper<UserRoleDO> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.eq(UserRoleDO::getUserId, userId);
            userRoleMapper.delete(deleteWrapper);

            // 插入新的用户角色关联
            if (CollUtil.isNotEmpty(roleIds)) {
                for (Long roleId : roleIds) {
                    UserRoleDO userRoleDO = new UserRoleDO();
                    userRoleDO.setUserId(userId);
                    userRoleDO.setRoleId(roleId);
                    userRoleDO.setScopeType(scopeType);
                    userRoleDO.setScopeId(scopeId);
                    userRoleDO.setCreatedAt(new Date());
                    if (Access.userId() != null) {
                        userRoleDO.setCreatedBy(Access.userId());
                    }
                    userRoleMapper.insert(userRoleDO);
                }
            }

            return Response.success();
        } catch (Exception e) {
            log.error("Error in assignRoles", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> resetPassword(Long userId, String newPassword) {
        try {
            UserDO userDO = userMapper.selectById(userId);
            if (userDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "用户不存在");
            }

            // BCrypt加密新密码
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userDO.setPassword(encoder.encode(newPassword));
            userDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                userDO.setUpdatedBy(Access.userId());
            }

            userMapper.updateById(userDO);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in resetPassword", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> updateUserStatus(Long userId, Integer status) {
        try {
            UserDO userDO = userMapper.selectById(userId);
            if (userDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "用户不存在");
            }

            userDO.setStatus(status);
            userDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                userDO.setUpdatedBy(Access.userId());
            }

            userMapper.updateById(userDO);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in updateUserStatus", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateLoginInfo(Long userId, String loginIp) {
        try {
            UserDO userDO = userMapper.selectById(userId);
            if (userDO != null) {
                userDO.setLastLoginTime(new Date());
                userDO.setLastLoginIp(loginIp);
                userMapper.updateById(userDO);
            }
        } catch (Exception e) {
            log.error("Error in updateLoginInfo", e);
        }
    }

    /**
     * DO转VO
     */
    private UserVO convertToVO(UserDO userDO) {
        if (userDO == null) {
            return null;
        }

        UserVO vo = new UserVO();
        vo.setId(userDO.getId());
        vo.setTenantId(userDO.getTenantId());
        vo.setUsername(userDO.getUsername());
        vo.setNickname(userDO.getNickname());
        vo.setRealName(userDO.getRealName());
        vo.setEmail(userDO.getEmail());
        vo.setPhone(userDO.getPhone());
        vo.setAvatarUrl(userDO.getAvatarUrl());
        vo.setUserType(userDO.getUserType());
        vo.setStatus(userDO.getStatus());
        vo.setLastLoginTime(userDO.getLastLoginTime());
        vo.setCreatedAt(userDO.getCreatedAt());
        vo.setRoles(new ArrayList<>());

        return vo;
    }

    /**
     * RoleDO转RoleVO
     */
    private RoleVO convertRoleToVO(RoleDO roleDO) {
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
}
