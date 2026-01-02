package com.aizz.mindmingle.domain.dto;

import java.io.Serializable;

/**
 * 提交数据DTO
 */
public class SubmissionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long toolId;
    public String submissionData;
    public Integer status;

    public SubmissionDTO() {
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public String getSubmissionData() {
        return submissionData;
    }

    public void setSubmissionData(String submissionData) {
        this.submissionData = submissionData;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SubmissionDTO{" +
                "toolId=" + toolId +
                ", submissionData='" + submissionData + '\'' +
                ", status=" + status +
                '}';
    }
}
