package com.aizz.mindmingle.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加工具协作者DTO
 */
public class ToolMemberDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long toolId;
    public List<Long> userIds = new ArrayList<>();
    public Integer permissionLevel;

    public ToolMemberDTO() {
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    @Override
    public String toString() {
        return "ToolMemberDTO{" +
                "toolId=" + toolId +
                ", userIds=" + userIds +
                ", permissionLevel=" + permissionLevel +
                '}';
    }
}
