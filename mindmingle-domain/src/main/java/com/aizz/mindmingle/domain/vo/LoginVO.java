package com.aizz.mindmingle.domain.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录响应VO
 */
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public String token;
    public Long userId;
    public String username;
    public String nickname;
    public Long tenantId;
    public String tenantName;
    public Integer userType;
    public List<String> roles = new ArrayList<>();
    public List<String> permissions = new ArrayList<>();
    public List<MenuVO> menus = new ArrayList<>();

    public LoginVO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<MenuVO> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuVO> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "LoginVO{" +
                "token='" + token + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", tenantId=" + tenantId +
                ", tenantName='" + tenantName + '\'' +
                ", userType=" + userType +
                ", roles=" + roles +
                ", permissions=" + permissions +
                ", menus=" + menus +
                '}';
    }
}
