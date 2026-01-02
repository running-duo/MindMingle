package com.aizz.mindmingle.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建/更新租户DTO
 */
public class TenantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public String tenantName;
    public String tenantCode;
    public String contactName;
    public String contactPhone;
    public String contactEmail;
    public Integer maxUsers;
    public Integer maxTools;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date expireTime;

    public TenantDTO() {
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public Integer getMaxTools() {
        return maxTools;
    }

    public void setMaxTools(Integer maxTools) {
        this.maxTools = maxTools;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "TenantDTO{" +
                "tenantName='" + tenantName + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", maxUsers=" + maxUsers +
                ", maxTools=" + maxTools +
                ", expireTime=" + expireTime +
                '}';
    }
}
