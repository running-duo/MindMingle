package com.aizz.mindmingle.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 工具协作者响应VO
 */
public class ToolMemberVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long id;
    public Long toolId;
    public Long userId;
    public String username;
    public String nickname;
    public Integer permissionLevel;
    public Long invitedBy;
    public String invitedByName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date invitedAt;

    public ToolMemberVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
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

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public Long getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(Long invitedBy) {
        this.invitedBy = invitedBy;
    }

    public String getInvitedByName() {
        return invitedByName;
    }

    public void setInvitedByName(String invitedByName) {
        this.invitedByName = invitedByName;
    }

    public Date getInvitedAt() {
        return invitedAt;
    }

    public void setInvitedAt(Date invitedAt) {
        this.invitedAt = invitedAt;
    }

    @Override
    public String toString() {
        return "ToolMemberVO{" +
                "id=" + id +
                ", toolId=" + toolId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", permissionLevel=" + permissionLevel +
                ", invitedBy=" + invitedBy +
                ", invitedByName='" + invitedByName + '\'' +
                ", invitedAt=" + invitedAt +
                '}';
    }
}
