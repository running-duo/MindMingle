package com.aizz.mindmingle.domain.dto;

import java.io.Serializable;

/**
 * 创建/更新模板DTO
 */
public class TemplateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public String templateName;
    public String templateCode;
    public String templateType;
    public String description;
    public String iconUrl;
    public String configJson;
    public String previewImageUrl;
    public String category;
    public String tags;

    public TemplateDTO() {
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
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

    public String getConfigJson() {
        return configJson;
    }

    public void setConfigJson(String configJson) {
        this.configJson = configJson;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TemplateDTO{" +
                "templateName='" + templateName + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", templateType='" + templateType + '\'' +
                ", description='" + description + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", configJson='" + configJson + '\'' +
                ", previewImageUrl='" + previewImageUrl + '\'' +
                ", category='" + category + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
