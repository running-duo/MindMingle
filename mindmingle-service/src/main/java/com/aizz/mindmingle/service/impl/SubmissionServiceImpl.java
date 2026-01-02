package com.aizz.mindmingle.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.common.ResponseCode;
import com.aizz.mindmingle.domain.dos.SubmissionDO;
import com.aizz.mindmingle.domain.dos.ToolDO;
import com.aizz.mindmingle.domain.dos.UserDO;
import com.aizz.mindmingle.domain.dto.SubmissionDTO;
import com.aizz.mindmingle.domain.dto.SubmissionQueryDTO;
import com.aizz.mindmingle.domain.dto.SubmissionReviewDTO;
import com.aizz.mindmingle.domain.vo.SubmissionVO;
import com.aizz.mindmingle.persistence.mapper.SubmissionMapper;
import com.aizz.mindmingle.persistence.mapper.ToolMapper;
import com.aizz.mindmingle.persistence.mapper.UserMapper;
import com.aizz.mindmingle.security.Access;
import com.aizz.mindmingle.service.SubmissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工具提交数据服务实现类
 */
@Service
public class SubmissionServiceImpl implements SubmissionService {

    private static final Logger log = LoggerFactory.getLogger(SubmissionServiceImpl.class);

    @Autowired
    private SubmissionMapper submissionMapper;

    @Autowired
    private ToolMapper toolMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Response<SubmissionVO> createSubmission(SubmissionDTO dto) {
        try {
            // 获取当前用户的租户ID
            Long tenantId = null;
            if (Access.userId() != null) {
                UserDO currentUser = userMapper.selectById(Access.userId());
                if (currentUser != null) {
                    tenantId = currentUser.getTenantId();
                }
            }

            SubmissionDO submissionDO = new SubmissionDO();
            submissionDO.setTenantId(tenantId);
            submissionDO.setToolId(dto.toolId);
            submissionDO.setUserId(Access.userId());
            submissionDO.setSubmissionData(dto.submissionData);
            submissionDO.setStatus(0); // 0=草稿
            submissionDO.setCreatedAt(new Date());
            submissionDO.setUpdatedAt(new Date());

            if (Access.userId() != null) {
                submissionDO.setCreatedBy(Access.userId());
                submissionDO.setUpdatedBy(Access.userId());
            }

            submissionMapper.insert(submissionDO);

            return Response.success(convertToVO(submissionDO));
        } catch (Exception e) {
            log.error("Error in createSubmission", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<SubmissionVO> updateSubmission(Long id, SubmissionDTO dto) {
        try {
            SubmissionDO submissionDO = submissionMapper.selectById(id);
            if (submissionDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "提交数据不存在");
            }

            // 验证所有权
            if (Access.userId() != null && !submissionDO.getUserId().equals(Access.userId())) {
                return Response.error(ResponseCode.FORBIDDEN, "无权限修改此提交");
            }

            // 更新字段
            if (dto.toolId != null) {
                submissionDO.setToolId(dto.toolId);
            }
            if (StrUtil.isNotBlank(dto.submissionData)) {
                submissionDO.setSubmissionData(dto.submissionData);
            }

            submissionDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                submissionDO.setUpdatedBy(Access.userId());
            }

            submissionMapper.updateById(submissionDO);

            return Response.success(convertToVO(submissionDO));
        } catch (Exception e) {
            log.error("Error in updateSubmission", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<SubmissionVO> submitData(Long id) {
        try {
            SubmissionDO submissionDO = submissionMapper.selectById(id);
            if (submissionDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "提交数据不存在");
            }

            // 验证所有权
            if (Access.userId() != null && !submissionDO.getUserId().equals(Access.userId())) {
                return Response.error(ResponseCode.FORBIDDEN, "无权限提交此数据");
            }

            // 更新状态：0(草稿) -> 1(待审核)
            submissionDO.setStatus(1);
            submissionDO.setSubmitTime(new Date());
            submissionDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                submissionDO.setUpdatedBy(Access.userId());
            }

            submissionMapper.updateById(submissionDO);

            return Response.success(convertToVO(submissionDO));
        } catch (Exception e) {
            log.error("Error in submitData", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> reviewSubmission(SubmissionReviewDTO dto) {
        try {
            SubmissionDO submissionDO = submissionMapper.selectById(dto.submissionId);
            if (submissionDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "提交数据不存在");
            }

            // TODO: 这里应该验证审核权限，可以通过Access.userPermissions()检查

            submissionDO.setStatus(dto.status);
            submissionDO.setReviewUserId(Access.userId());
            submissionDO.setReviewTime(new Date());
            submissionDO.setReviewComment(dto.reviewComment);
            submissionDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                submissionDO.setUpdatedBy(Access.userId());
            }

            submissionMapper.updateById(submissionDO);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in reviewSubmission", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> deleteSubmission(Long id) {
        try {
            SubmissionDO submissionDO = submissionMapper.selectById(id);
            if (submissionDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "提交数据不存在");
            }

            // 验证所有权
            if (Access.userId() != null && !submissionDO.getUserId().equals(Access.userId())) {
                return Response.error(ResponseCode.FORBIDDEN, "无权限删除此提交");
            }

            // MyBatis-Plus的@TableLogic会自动处理软删除
            submissionMapper.deleteById(id);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in deleteSubmission", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<SubmissionVO> getSubmissionById(Long id) {
        try {
            SubmissionDO submissionDO = submissionMapper.selectById(id);
            if (submissionDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "提交数据不存在");
            }

            SubmissionVO submissionVO = convertToVO(submissionDO);

            // 查询工具名称
            if (submissionDO.getToolId() != null) {
                ToolDO tool = toolMapper.selectById(submissionDO.getToolId());
                if (tool != null) {
                    submissionVO.setToolName(tool.getToolName());
                }
            }

            // 查询用户名
            if (submissionDO.getUserId() != null) {
                UserDO user = userMapper.selectById(submissionDO.getUserId());
                if (user != null) {
                    submissionVO.setUsername(user.getNickname() != null ? user.getNickname() : user.getUsername());
                }
            }

            // 查询审核者名称
            if (submissionDO.getReviewUserId() != null) {
                UserDO reviewer = userMapper.selectById(submissionDO.getReviewUserId());
                if (reviewer != null) {
                    submissionVO.setReviewUserName(reviewer.getNickname() != null ? reviewer.getNickname() : reviewer.getUsername());
                }
            }

            return Response.success(submissionVO);
        } catch (Exception e) {
            log.error("Error in getSubmissionById", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<PageInfo<SubmissionVO>> listSubmissions(SubmissionQueryDTO dto) {
        try {
            PageHelper.startPage(dto.pageNum != null ? dto.pageNum : 1,
                               dto.pageSize != null ? dto.pageSize : 10);

            LambdaQueryWrapper<SubmissionDO> queryWrapper = new LambdaQueryWrapper<>();

            // 租户过滤
            if (Access.userId() != null) {
                UserDO currentUser = userMapper.selectById(Access.userId());
                if (currentUser != null && currentUser.getTenantId() != null) {
                    queryWrapper.eq(SubmissionDO::getTenantId, currentUser.getTenantId());
                }
            }

            // 工具ID过滤
            if (dto.toolId != null) {
                queryWrapper.eq(SubmissionDO::getToolId, dto.toolId);
            }

            // 用户ID过滤
            if (dto.userId != null) {
                queryWrapper.eq(SubmissionDO::getUserId, dto.userId);
            }

            // 状态过滤
            if (dto.status != null) {
                queryWrapper.eq(SubmissionDO::getStatus, dto.status);
            }

            // 日期范围过滤
            if (dto.startTime != null) {
                queryWrapper.ge(SubmissionDO::getSubmitTime, dto.startTime);
            }
            if (dto.endTime != null) {
                queryWrapper.le(SubmissionDO::getSubmitTime, dto.endTime);
            }

            queryWrapper.orderByDesc(SubmissionDO::getCreatedAt);

            List<SubmissionDO> list = submissionMapper.selectList(queryWrapper);
            PageInfo<SubmissionDO> pageInfo = new PageInfo<>(list);

            List<SubmissionVO> voList = list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

            PageInfo<SubmissionVO> result = new PageInfo<>();
            result.setList(voList);
            result.setTotal(pageInfo.getTotal());
            result.setPageNum(pageInfo.getPageNum());
            result.setPageSize(pageInfo.getPageSize());
            result.setPages(pageInfo.getPages());

            return Response.success(result);
        } catch (Exception e) {
            log.error("Error in listSubmissions", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<byte[]> exportSubmissions(SubmissionQueryDTO dto) {
        try {
            // TODO: 实现导出功能
            log.info("Export submissions feature to be implemented");
            return Response.success(new byte[0]);
        } catch (Exception e) {
            log.error("Error in exportSubmissions", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DO转VO
     */
    private SubmissionVO convertToVO(SubmissionDO submissionDO) {
        if (submissionDO == null) {
            return null;
        }

        SubmissionVO vo = new SubmissionVO();
        vo.setId(submissionDO.getId());
        vo.setToolId(submissionDO.getToolId());
        vo.setUserId(submissionDO.getUserId());
        vo.setSubmissionData(submissionDO.getSubmissionData());
        vo.setStatus(submissionDO.getStatus());
        vo.setSubmitTime(submissionDO.getSubmitTime());
        vo.setReviewUserId(submissionDO.getReviewUserId());
        vo.setReviewTime(submissionDO.getReviewTime());
        vo.setReviewComment(submissionDO.getReviewComment());
        vo.setCreatedAt(submissionDO.getCreatedAt());

        return vo;
    }
}
