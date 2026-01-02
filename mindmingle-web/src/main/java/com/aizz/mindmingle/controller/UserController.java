package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.UserDTO;
import com.aizz.mindmingle.domain.dto.UserQueryDTO;
import com.aizz.mindmingle.domain.vo.UserVO;
import com.aizz.mindmingle.service.UserService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public Response<UserVO> createUser(@RequestBody UserDTO userDTO) {
        log.info("Creating user: {}", userDTO.getUsername());
        return userService.createUser(userDTO);
    }

    @PutMapping("/{id}")
    public Response<UserVO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        log.info("Updating user: {}", id);
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user: {}", id);
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public Response<UserVO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/list")
    public Response<PageInfo<UserVO>> listUsers(UserQueryDTO userQueryDTO) {
        return userService.listUsers(userQueryDTO);
    }

    @PostMapping("/{id}/roles")
    public Response<Void> assignRoles(@PathVariable Long id,
                                     @RequestParam List<Long> roleIds,
                                     @RequestParam(required = false) Integer scopeType,
                                     @RequestParam(required = false) Long scopeId) {
        log.info("Assigning roles to user: {}, roles: {}", id, roleIds);
        return userService.assignRoles(id, roleIds, scopeType, scopeId);
    }

    @PutMapping("/{id}/password")
    public Response<Void> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        log.info("Resetting password for user: {}", id);
        return userService.resetPassword(id, newPassword);
    }

    @PutMapping("/{id}/status")
    public Response<Void> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        log.info("Updating user status: {} to {}", id, status);
        return userService.updateUserStatus(id, status);
    }
}
