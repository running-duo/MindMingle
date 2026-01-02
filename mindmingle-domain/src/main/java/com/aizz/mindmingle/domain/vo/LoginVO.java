package com.aizz.mindmingle.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 登录响应VO
 */
@Data
@Builder
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public String token;
    public String accessToken;
    public String tokenType;
    public Long expiresIn;
    public Long userId;
    public String username;
    public String nickname;
    public String realName;
    public String avatar;
    public Long tenantId;
    public String tenantName;
    public Integer userType;
    public Date loginTime;
    @Builder.Default
    public List<String> roles = new ArrayList<>();
    @Builder.Default
    public List<String> permissions = new ArrayList<>();
    @Builder.Default
    public List<MenuVO> menus = new ArrayList<>();

    public LoginVO() {
    }

    public LoginVO(String token, String accessToken, String tokenType, Long expiresIn, Long userId, String username, String nickname, String realName, String avatar, Long tenantId, String tenantName, Integer userType, Date loginTime, List<String> roles, List<String> permissions, List<MenuVO> menus) {
        this.token = token;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.realName = realName;
        this.avatar = avatar;
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.userType = userType;
        this.loginTime = loginTime;
        this.roles = roles;
        this.permissions = permissions;
        this.menus = menus;
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
