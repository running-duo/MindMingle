package com.aizz.mindmingle.domain.dto;

import java.io.Serializable;

/**
 * 微信登录DTO
 */
public class WxLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public String code;
    public String tenantCode;

    public WxLoginDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "WxLoginDTO{" +
                "code='" + code + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}
