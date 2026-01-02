package com.aizz.mindmingle.service;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.UserDTO;
import com.aizz.mindmingle.domain.dto.UserQueryDTO;
import com.aizz.mindmingle.domain.vo.UserVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 创建用户
     */
    Response<UserVO> createUser(UserDTO dto);

    /**
     * 更新用户
     */
    Response<UserVO> updateUser(Long id, UserDTO dto);

    /**
     * 删除用户
     */
    Response<Void> deleteUser(Long id);

    /**
     * 获取用户详情
     */
    Response<UserVO> getUserById(Long id);

    /**
     * 根据用户名获取用户
     */
    Response<UserVO> getUserByUsername(String username, Long tenantId);

    /**
     * 分页查询用户列表
     */
    Response<PageInfo<UserVO>> listUsers(UserQueryDTO dto);

    /**
     * 为用户分配角色
     */
    Response<Void> assignRoles(Long userId, List<Long> roleIds, Integer scopeType, Long scopeId);

    /**
     * 重置用户密码
     */
    Response<Void> resetPassword(Long userId, String newPassword);

    /**
     * 启用/禁用用户
     */
    Response<Void> updateUserStatus(Long userId, Integer status);

    /**
     * 更新用户登录信息
     */
    void updateLoginInfo(Long userId, String loginIp);
}
