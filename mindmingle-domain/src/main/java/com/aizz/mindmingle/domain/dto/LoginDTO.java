package com.aizz.mindmingle.domain.dto;

import java.io.Serializable;

/**
 * 登录请求DTO
 */
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public String username;
    public String password;
    public String tenantCode;

    public LoginDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}
