package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.common.annotation.RequireToolPermission;
import com.aizz.mindmingle.domain.dto.SubmissionDTO;
import com.aizz.mindmingle.domain.dto.SubmissionQueryDTO;
import com.aizz.mindmingle.domain.dto.SubmissionReviewDTO;
import com.aizz.mindmingle.domain.vo.SubmissionVO;
import com.aizz.mindmingle.service.SubmissionService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 提交数据管理控制器
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {

    private static final Logger log = LoggerFactory.getLogger(SubmissionController.class);

    @Autowired
    private SubmissionService submissionService;

    @RequireToolPermission(1)
    @PostMapping
    public Response<SubmissionVO> createSubmission(@RequestBody SubmissionDTO submissionDTO) {
        log.info("Creating submission for tool: {}", submissionDTO.getToolId());
        return submissionService.createSubmission(submissionDTO);
    }

    @PutMapping("/{id}")
    public Response<SubmissionVO> updateSubmission(@PathVariable Long id, @RequestBody SubmissionDTO submissionDTO) {
        log.info("Updating submission: {}", id);
        return submissionService.updateSubmission(id, submissionDTO);
    }

    @PostMapping("/{id}/submit")
    public Response<SubmissionVO> submitData(@PathVariable Long id) {
        log.info("Submitting data: {}", id);
        return submissionService.submitData(id);
    }

    @PostMapping("/review")
    public Response<Void> reviewSubmission(@RequestBody SubmissionReviewDTO reviewDTO) {
        log.info("Reviewing submission: {}", reviewDTO.getSubmissionId());
        return submissionService.reviewSubmission(reviewDTO);
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteSubmission(@PathVariable Long id) {
        log.info("Deleting submission: {}", id);
        return submissionService.deleteSubmission(id);
    }

    @GetMapping("/{id}")
    public Response<SubmissionVO> getSubmissionById(@PathVariable Long id) {
        return submissionService.getSubmissionById(id);
    }

    @GetMapping("/list")
    public Response<PageInfo<SubmissionVO>> listSubmissions(SubmissionQueryDTO queryDTO) {
        return submissionService.listSubmissions(queryDTO);
    }

    @GetMapping(value = "/export", produces = "application/octet-stream")
    public Response<byte[]> exportSubmissions(SubmissionQueryDTO queryDTO) {
        log.info("Exporting submissions");
        return submissionService.exportSubmissions(queryDTO);
    }
}
