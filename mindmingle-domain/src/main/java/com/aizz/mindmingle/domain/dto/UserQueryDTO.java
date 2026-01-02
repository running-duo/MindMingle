package com.aizz.mindmingle.domain.dto;

/**
 * 用户查询DTO
 */
public class UserQueryDTO extends PageQueryDTO {
    private static final long serialVersionUID = 1L;

    public Integer userType;
    public Integer status;
    public String email;

    public UserQueryDTO() {
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserQueryDTO{" +
                "userType=" + userType +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", keyword='" + keyword + '\'' +
                ", sortField='" + sortField + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }
}
