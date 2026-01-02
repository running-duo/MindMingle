package com.aizz.mindmingle.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建/更新角色DTO
 */
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public String roleCode;
    public String roleName;
    public Integer roleType;
    public String description;
    public List<Long> permissionIds = new ArrayList<>();

    public RoleDTO() {
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleType=" + roleType +
                ", description='" + description + '\'' +
                ", permissionIds=" + permissionIds +
                '}';
    }
}
