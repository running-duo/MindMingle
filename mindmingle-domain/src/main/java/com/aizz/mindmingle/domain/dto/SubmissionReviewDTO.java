package com.aizz.mindmingle.domain.dto;

import java.io.Serializable;

/**
 * 审核提交数据DTO
 */
public class SubmissionReviewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long submissionId;
    public Integer status;
    public String reviewComment;

    public SubmissionReviewDTO() {
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    @Override
    public String toString() {
        return "SubmissionReviewDTO{" +
                "submissionId=" + submissionId +
                ", status=" + status +
                ", reviewComment='" + reviewComment + '\'' +
                '}';
    }
}
