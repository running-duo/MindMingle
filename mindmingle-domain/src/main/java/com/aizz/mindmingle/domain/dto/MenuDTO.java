package com.aizz.mindmingle.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建/更新菜单DTO
 */
public class MenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public String menuName;
    public String menuCode;
    public Integer menuType;
    public Long parentId;
    public String path;
    public String component;
    public String icon;
    public Integer sortOrder;
    public Integer isVisible;
    public Integer status;
    public List<Long> permissionIds = new ArrayList<>();

    public MenuDTO() {
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "menuName='" + menuName + '\'' +
                ", menuCode='" + menuCode + '\'' +
                ", menuType=" + menuType +
                ", parentId=" + parentId +
                ", path='" + path + '\'' +
                ", component='" + component + '\'' +
                ", icon='" + icon + '\'' +
                ", sortOrder=" + sortOrder +
                ", isVisible=" + isVisible +
                ", status=" + status +
                ", permissionIds=" + permissionIds +
                '}';
    }
}
