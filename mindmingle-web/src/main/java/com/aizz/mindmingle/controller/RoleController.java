package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.PageQueryDTO;
import com.aizz.mindmingle.domain.dto.RoleDTO;
import com.aizz.mindmingle.domain.vo.RoleVO;
import com.aizz.mindmingle.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @PostMapping
    public Response<RoleVO> createRole(@RequestBody RoleDTO roleDTO) {
        log.info("Creating role: {}", roleDTO.getRoleName());
        return roleService.createRole(roleDTO);
    }

    @PutMapping("/{id}")
    public Response<RoleVO> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        log.info("Updating role: {}", id);
        return roleService.updateRole(id, roleDTO);
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteRole(@PathVariable Long id) {
        log.info("Deleting role: {}", id);
        return roleService.deleteRole(id);
    }

    @GetMapping("/{id}")
    public Response<RoleVO> getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping("/list")
    public Response<PageInfo<RoleVO>> listRoles(PageQueryDTO pageQueryDTO) {
        return roleService.listRoles(pageQueryDTO);
    }

    @GetMapping("/user/{userId}")
    public Response<List<RoleVO>> getUserRoles(@PathVariable Long userId) {
        return roleService.getUserRoles(userId);
    }

    @PostMapping("/{id}/permissions")
    public Response<Void> assignPermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        log.info("Assigning permissions to role: {}, permissions: {}", id, permissionIds);
        return roleService.assignPermissions(id, permissionIds);
    }
}
