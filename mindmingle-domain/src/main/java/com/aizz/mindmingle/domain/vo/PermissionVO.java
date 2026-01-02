package com.aizz.mindmingle.domain.vo;

import java.io.Serializable;

/**
 * 权限响应VO
 */
public class PermissionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long id;
    public String permissionCode;
    public String permissionName;
    public Integer permissionType;
    public String resourceType;
    public String resourceAction;
    public Long parentId;
    public String description;

    public PermissionVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceAction() {
        return resourceAction;
    }

    public void setResourceAction(String resourceAction) {
        this.resourceAction = resourceAction;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PermissionVO{" +
                "id=" + id +
                ", permissionCode='" + permissionCode + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", permissionType=" + permissionType +
                ", resourceType='" + resourceType + '\'' +
                ", resourceAction='" + resourceAction + '\'' +
                ", parentId=" + parentId +
                ", description='" + description + '\'' +
                '}';
    }
}
