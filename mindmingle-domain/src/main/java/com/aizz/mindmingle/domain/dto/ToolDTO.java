package com.aizz.mindmingle.domain.dto;

import java.io.Serializable;

/**
 * 创建/更新工具DTO
 */
public class ToolDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public String toolName;
    public String toolCode;
    public String toolType;
    public String description;
    public String iconUrl;
    public Long templateId;
    public String configJson;
    public Integer isPublic;
    public Integer sortOrder;

    public ToolDTO() {
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getConfigJson() {
        return configJson;
    }

    public void setConfigJson(String configJson) {
        this.configJson = configJson;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "ToolDTO{" +
                "toolName='" + toolName + '\'' +
                ", toolCode='" + toolCode + '\'' +
                ", toolType='" + toolType + '\'' +
                ", description='" + description + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", templateId=" + templateId +
                ", configJson='" + configJson + '\'' +
                ", isPublic=" + isPublic +
                ", sortOrder=" + sortOrder +
                '}';
    }
}
