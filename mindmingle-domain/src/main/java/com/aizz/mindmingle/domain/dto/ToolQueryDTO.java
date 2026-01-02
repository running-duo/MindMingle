package com.aizz.mindmingle.domain.dto;

/**
 * 工具查询DTO
 */
public class ToolQueryDTO extends PageQueryDTO {
    private static final long serialVersionUID = 1L;

    public String toolType;
    public Integer status;
    public Long creatorId;
    public Integer isPublic;

    public ToolQueryDTO() {
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return "ToolQueryDTO{" +
                "toolType='" + toolType + '\'' +
                ", status=" + status +
                ", creatorId=" + creatorId +
                ", isPublic=" + isPublic +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", keyword='" + keyword + '\'' +
                ", sortField='" + sortField + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }
}
