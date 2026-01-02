package com.aizz.mindmingle.service;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.SubmissionDTO;
import com.aizz.mindmingle.domain.dto.SubmissionQueryDTO;
import com.aizz.mindmingle.domain.dto.SubmissionReviewDTO;
import com.aizz.mindmingle.domain.vo.SubmissionVO;
import com.github.pagehelper.PageInfo;

/**
 * 工具提交数据服务接口
 */
public interface SubmissionService {

    /**
     * 创建提交数据(草稿)
     */
    Response<SubmissionVO> createSubmission(SubmissionDTO dto);

    /**
     * 更新提交数据
     */
    Response<SubmissionVO> updateSubmission(Long id, SubmissionDTO dto);

    /**
     * 提交数据(提交审核)
     */
    Response<SubmissionVO> submitData(Long id);

    /**
     * 审核提交数据
     */
    Response<Void> reviewSubmission(SubmissionReviewDTO dto);

    /**
     * 删除提交数据
     */
    Response<Void> deleteSubmission(Long id);

    /**
     * 获取提交数据详情
     */
    Response<SubmissionVO> getSubmissionById(Long id);

    /**
     * 分页查询提交数据列表
     */
    Response<PageInfo<SubmissionVO>> listSubmissions(SubmissionQueryDTO dto);

    /**
     * 导出提交数据
     */
    Response<byte[]> exportSubmissions(SubmissionQueryDTO dto);
}
