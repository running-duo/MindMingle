package com.aizz.mindmingle.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.common.ResponseCode;
import com.aizz.mindmingle.domain.dos.TemplateDO;
import com.aizz.mindmingle.domain.dos.ToolDO;
import com.aizz.mindmingle.domain.dos.ToolMemberDO;
import com.aizz.mindmingle.domain.dos.UserDO;
import com.aizz.mindmingle.domain.dto.ToolDTO;
import com.aizz.mindmingle.domain.dto.ToolQueryDTO;
import com.aizz.mindmingle.domain.vo.ToolVO;
import com.aizz.mindmingle.persistence.mapper.TemplateMapper;
import com.aizz.mindmingle.persistence.mapper.ToolMapper;
import com.aizz.mindmingle.persistence.mapper.ToolMemberMapper;
import com.aizz.mindmingle.persistence.mapper.UserMapper;
import com.aizz.mindmingle.security.Access;
import com.aizz.mindmingle.service.ToolService;
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
 * 工具服务实现类
 */
@Service
public class ToolServiceImpl implements ToolService {

    private static final Logger log = LoggerFactory.getLogger(ToolServiceImpl.class);

    @Autowired
    private ToolMapper toolMapper;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ToolMemberMapper toolMemberMapper;

    @Override
    public Response<ToolVO> createTool(ToolDTO dto) {
        try {
            // 获取当前用户的租户ID
            Long tenantId = null;
            if (Access.userId() != null) {
                UserDO currentUser = userMapper.selectById(Access.userId());
                if (currentUser != null) {
                    tenantId = currentUser.getTenantId();
                }
            }

            // 验证工具编码在租户内唯一
            LambdaQueryWrapper<ToolDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ToolDO::getToolCode, dto.toolCode);
            if (tenantId != null) {
                queryWrapper.eq(ToolDO::getTenantId, tenantId);
            }
            Long count = toolMapper.selectCount(queryWrapper);
            if (count > 0) {
                return Response.error(ResponseCode.BAD_REQUEST, "工具编码已存在");
            }

            ToolDO toolDO = new ToolDO();
            toolDO.setTenantId(tenantId);
            toolDO.setToolName(dto.toolName);
            toolDO.setToolCode(dto.toolCode);
            toolDO.setToolType(dto.toolType);
            toolDO.setDescription(dto.description);
            toolDO.setIconUrl(dto.iconUrl);
            toolDO.setTemplateId(dto.templateId);
            toolDO.setConfigJson(dto.configJson);
            toolDO.setCreatorId(Access.userId());
            toolDO.setStatus(1);
            toolDO.setIsPublic(dto.isPublic != null ? dto.isPublic : 0);
            toolDO.setSortOrder(dto.sortOrder != null ? dto.sortOrder : 0);
            toolDO.setViewCount(0);
            toolDO.setUseCount(0);
            toolDO.setCreatedAt(new Date());
            toolDO.setUpdatedAt(new Date());

            if (Access.userId() != null) {
                toolDO.setCreatedBy(Access.userId());
                toolDO.setUpdatedBy(Access.userId());
            }

            toolMapper.insert(toolDO);

            return Response.success(convertToVO(toolDO));
        } catch (Exception e) {
            log.error("Error in createTool", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<ToolVO> updateTool(Long id, ToolDTO dto) {
        try {
            ToolDO toolDO = toolMapper.selectById(id);
            if (toolDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "工具不存在");
            }

            // 验证所有权或管理权限
            if (Access.userId() != null && !toolDO.getCreatorId().equals(Access.userId())) {
                // 检查是否是工具成员
                LambdaQueryWrapper<ToolMemberDO> memberWrapper = new LambdaQueryWrapper<>();
                memberWrapper.eq(ToolMemberDO::getToolId, id);
                memberWrapper.eq(ToolMemberDO::getUserId, Access.userId());
                memberWrapper.ge(ToolMemberDO::getPermissionLevel, 2); // 假设2是管理权限
                Long memberCount = toolMemberMapper.selectCount(memberWrapper);
                if (memberCount == 0) {
                    return Response.error(ResponseCode.FORBIDDEN, "无权限修改此工具");
                }
            }

            // 验证工具编码唯一性
            if (StrUtil.isNotBlank(dto.toolCode) && !dto.toolCode.equals(toolDO.getToolCode())) {
                LambdaQueryWrapper<ToolDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ToolDO::getToolCode, dto.toolCode);
                if (toolDO.getTenantId() != null) {
                    queryWrapper.eq(ToolDO::getTenantId, toolDO.getTenantId());
                }
                queryWrapper.ne(ToolDO::getId, id);
                Long count = toolMapper.selectCount(queryWrapper);
                if (count > 0) {
                    return Response.error(ResponseCode.BAD_REQUEST, "工具编码已存在");
                }
            }

            // 更新字段
            if (StrUtil.isNotBlank(dto.toolName)) {
                toolDO.setToolName(dto.toolName);
            }
            if (StrUtil.isNotBlank(dto.toolCode)) {
                toolDO.setToolCode(dto.toolCode);
            }
            if (StrUtil.isNotBlank(dto.toolType)) {
                toolDO.setToolType(dto.toolType);
            }
            if (StrUtil.isNotBlank(dto.description)) {
                toolDO.setDescription(dto.description);
            }
            if (StrUtil.isNotBlank(dto.iconUrl)) {
                toolDO.setIconUrl(dto.iconUrl);
            }
            if (dto.templateId != null) {
                toolDO.setTemplateId(dto.templateId);
            }
            if (StrUtil.isNotBlank(dto.configJson)) {
                toolDO.setConfigJson(dto.configJson);
            }
            if (dto.isPublic != null) {
                toolDO.setIsPublic(dto.isPublic);
            }
            if (dto.sortOrder != null) {
                toolDO.setSortOrder(dto.sortOrder);
            }

            toolDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                toolDO.setUpdatedBy(Access.userId());
            }

            toolMapper.updateById(toolDO);

            return Response.success(convertToVO(toolDO));
        } catch (Exception e) {
            log.error("Error in updateTool", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> deleteTool(Long id) {
        try {
            ToolDO toolDO = toolMapper.selectById(id);
            if (toolDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "工具不存在");
            }

            // 验证所有权
            if (Access.userId() != null && !toolDO.getCreatorId().equals(Access.userId())) {
                return Response.error(ResponseCode.FORBIDDEN, "无权限删除此工具");
            }

            // MyBatis-Plus的@TableLogic会自动处理软删除
            toolMapper.deleteById(id);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in deleteTool", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<ToolVO> getToolById(Long id) {
        try {
            ToolDO toolDO = toolMapper.selectById(id);
            if (toolDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "工具不存在");
            }

            ToolVO toolVO = convertToVO(toolDO);

            // 查询模板名称
            if (toolDO.getTemplateId() != null) {
                TemplateDO template = templateMapper.selectById(toolDO.getTemplateId());
                if (template != null) {
                    toolVO.setTemplateName(template.getTemplateName());
                }
            }

            // 查询创建者名称
            if (toolDO.getCreatorId() != null) {
                UserDO creator = userMapper.selectById(toolDO.getCreatorId());
                if (creator != null) {
                    toolVO.setCreatorName(creator.getNickname() != null ? creator.getNickname() : creator.getUsername());
                }
            }

            // 查询成员数量
            LambdaQueryWrapper<ToolMemberDO> memberWrapper = new LambdaQueryWrapper<>();
            memberWrapper.eq(ToolMemberDO::getToolId, id);
            Long memberCount = toolMemberMapper.selectCount(memberWrapper);
            toolVO.setMemberCount(memberCount.intValue());

            return Response.success(toolVO);
        } catch (Exception e) {
            log.error("Error in getToolById", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<PageInfo<ToolVO>> listTools(ToolQueryDTO dto) {
        try {
            PageHelper.startPage(dto.pageNum != null ? dto.pageNum : 1,
                               dto.pageSize != null ? dto.pageSize : 10);

            LambdaQueryWrapper<ToolDO> queryWrapper = new LambdaQueryWrapper<>();

            // 租户过滤
            if (Access.userId() != null) {
                UserDO currentUser = userMapper.selectById(Access.userId());
                if (currentUser != null && currentUser.getTenantId() != null) {
                    queryWrapper.eq(ToolDO::getTenantId, currentUser.getTenantId());
                }
            }

            // 关键字搜索
            if (StrUtil.isNotBlank(dto.keyword)) {
                queryWrapper.and(wrapper -> wrapper
                    .like(ToolDO::getToolName, dto.keyword)
                    .or()
                    .like(ToolDO::getToolCode, dto.keyword)
                    .or()
                    .like(ToolDO::getDescription, dto.keyword)
                );
            }

            // 工具类型过滤
            if (StrUtil.isNotBlank(dto.toolType)) {
                queryWrapper.eq(ToolDO::getToolType, dto.toolType);
            }

            // 状态过滤
            if (dto.status != null) {
                queryWrapper.eq(ToolDO::getStatus, dto.status);
            }

            // 是否公开过滤
            if (dto.isPublic != null) {
                queryWrapper.eq(ToolDO::getIsPublic, dto.isPublic);
            }

            queryWrapper.orderByDesc(ToolDO::getCreatedAt);

            List<ToolDO> list = toolMapper.selectList(queryWrapper);
            PageInfo<ToolDO> pageInfo = new PageInfo<>(list);

            List<ToolVO> voList = list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

            PageInfo<ToolVO> result = new PageInfo<>();
            result.setList(voList);
            result.setTotal(pageInfo.getTotal());
            result.setPageNum(pageInfo.getPageNum());
            result.setPageSize(pageInfo.getPageSize());
            result.setPages(pageInfo.getPages());

            return Response.success(result);
        } catch (Exception e) {
            log.error("Error in listTools", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<PageInfo<ToolVO>> listMyTools(ToolQueryDTO dto) {
        try {
            PageHelper.startPage(dto.pageNum != null ? dto.pageNum : 1,
                               dto.pageSize != null ? dto.pageSize : 10);

            LambdaQueryWrapper<ToolDO> queryWrapper = new LambdaQueryWrapper<>();

            if (Access.userId() != null) {
                // 查询我创建的工具或我是成员的工具
                LambdaQueryWrapper<ToolMemberDO> memberWrapper = new LambdaQueryWrapper<>();
                memberWrapper.eq(ToolMemberDO::getUserId, Access.userId());
                List<ToolMemberDO> memberList = toolMemberMapper.selectList(memberWrapper);
                List<Long> memberToolIds = memberList.stream()
                    .map(ToolMemberDO::getToolId)
                    .collect(Collectors.toList());

                queryWrapper.and(wrapper -> {
                    wrapper.eq(ToolDO::getCreatorId, Access.userId());
                    if (!memberToolIds.isEmpty()) {
                        wrapper.or().in(ToolDO::getId, memberToolIds);
                    }
                });
            } else {
                return Response.error(ResponseCode.UNAUTHORIZED, "请先登录");
            }

            // 关键字搜索
            if (StrUtil.isNotBlank(dto.keyword)) {
                queryWrapper.and(wrapper -> wrapper
                    .like(ToolDO::getToolName, dto.keyword)
                    .or()
                    .like(ToolDO::getToolCode, dto.keyword)
                );
            }

            queryWrapper.orderByDesc(ToolDO::getCreatedAt);

            List<ToolDO> list = toolMapper.selectList(queryWrapper);
            PageInfo<ToolDO> pageInfo = new PageInfo<>(list);

            List<ToolVO> voList = list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

            PageInfo<ToolVO> result = new PageInfo<>();
            result.setList(voList);
            result.setTotal(pageInfo.getTotal());
            result.setPageNum(pageInfo.getPageNum());
            result.setPageSize(pageInfo.getPageSize());
            result.setPages(pageInfo.getPages());

            return Response.success(result);
        } catch (Exception e) {
            log.error("Error in listMyTools", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> publishTool(Long id, Integer isPublic) {
        try {
            ToolDO toolDO = toolMapper.selectById(id);
            if (toolDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "工具不存在");
            }

            // 验证所有权
            if (Access.userId() != null && !toolDO.getCreatorId().equals(Access.userId())) {
                return Response.error(ResponseCode.FORBIDDEN, "无权限发布此工具");
            }

            toolDO.setIsPublic(isPublic);
            toolDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                toolDO.setUpdatedBy(Access.userId());
            }

            toolMapper.updateById(toolDO);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in publishTool", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> updateToolStatus(Long id, Integer status) {
        try {
            ToolDO toolDO = toolMapper.selectById(id);
            if (toolDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "工具不存在");
            }

            toolDO.setStatus(status);
            toolDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                toolDO.setUpdatedBy(Access.userId());
            }

            toolMapper.updateById(toolDO);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in updateToolStatus", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void incrementViewCount(Long id) {
        try {
            ToolDO toolDO = toolMapper.selectById(id);
            if (toolDO != null) {
                toolDO.setViewCount(toolDO.getViewCount() != null ? toolDO.getViewCount() + 1 : 1);
                toolMapper.updateById(toolDO);
            }
        } catch (Exception e) {
            log.error("Error in incrementViewCount", e);
        }
    }

    @Override
    public void incrementUseCount(Long id) {
        try {
            ToolDO toolDO = toolMapper.selectById(id);
            if (toolDO != null) {
                toolDO.setUseCount(toolDO.getUseCount() != null ? toolDO.getUseCount() + 1 : 1);
                toolMapper.updateById(toolDO);
            }
        } catch (Exception e) {
            log.error("Error in incrementUseCount", e);
        }
    }

    /**
     * DO转VO
     */
    private ToolVO convertToVO(ToolDO toolDO) {
        if (toolDO == null) {
            return null;
        }

        ToolVO vo = new ToolVO();
        vo.setId(toolDO.getId());
        vo.setToolName(toolDO.getToolName());
        vo.setToolCode(toolDO.getToolCode());
        vo.setToolType(toolDO.getToolType());
        vo.setDescription(toolDO.getDescription());
        vo.setIconUrl(toolDO.getIconUrl());
        vo.setTemplateId(toolDO.getTemplateId());
        vo.setConfigJson(toolDO.getConfigJson());
        vo.setCreatorId(toolDO.getCreatorId());
        vo.setStatus(toolDO.getStatus());
        vo.setIsPublic(toolDO.getIsPublic());
        vo.setSortOrder(toolDO.getSortOrder());
        vo.setViewCount(toolDO.getViewCount());
        vo.setUseCount(toolDO.getUseCount());
        vo.setCreatedAt(toolDO.getCreatedAt());
        vo.setUpdatedAt(toolDO.getUpdatedAt());

        return vo;
    }
}
