package com.aizz.mindmingle.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.common.ResponseCode;
import com.aizz.mindmingle.domain.dos.TemplateDO;
import com.aizz.mindmingle.domain.dto.PageQueryDTO;
import com.aizz.mindmingle.domain.dto.TemplateDTO;
import com.aizz.mindmingle.domain.vo.TemplateVO;
import com.aizz.mindmingle.persistence.mapper.TemplateMapper;
import com.aizz.mindmingle.security.Access;
import com.aizz.mindmingle.service.TemplateService;
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
 * 工具模板服务实现类
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    private static final Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);

    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public Response<TemplateVO> createTemplate(TemplateDTO dto) {
        try {
            // 验证模板编码唯一性
            LambdaQueryWrapper<TemplateDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TemplateDO::getTemplateCode, dto.templateCode);
            Long count = templateMapper.selectCount(queryWrapper);
            if (count > 0) {
                return Response.error(ResponseCode.BAD_REQUEST, "模板编码已存在");
            }

            TemplateDO templateDO = new TemplateDO();
            templateDO.setTemplateName(dto.templateName);
            templateDO.setTemplateCode(dto.templateCode);
            templateDO.setTemplateType(dto.templateType);
            templateDO.setDescription(dto.description);
            templateDO.setIconUrl(dto.iconUrl);
            templateDO.setConfigJson(dto.configJson);
            templateDO.setPreviewImageUrl(dto.previewImageUrl);
            templateDO.setIsSystem(0);
            templateDO.setCategory(dto.category);
            templateDO.setTags(dto.tags);
            templateDO.setUseCount(0);
            templateDO.setCreatedAt(new Date());
            templateDO.setUpdatedAt(new Date());

            if (Access.userId() != null) {
                templateDO.setCreatedBy(Access.userId());
                templateDO.setUpdatedBy(Access.userId());
            }

            templateMapper.insert(templateDO);

            return Response.success(convertToVO(templateDO));
        } catch (Exception e) {
            log.error("Error in createTemplate", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<TemplateVO> updateTemplate(Long id, TemplateDTO dto) {
        try {
            TemplateDO templateDO = templateMapper.selectById(id);
            if (templateDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "模板不存在");
            }

            // 验证模板编码唯一性
            if (StrUtil.isNotBlank(dto.templateCode) && !dto.templateCode.equals(templateDO.getTemplateCode())) {
                LambdaQueryWrapper<TemplateDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(TemplateDO::getTemplateCode, dto.templateCode);
                queryWrapper.ne(TemplateDO::getId, id);
                Long count = templateMapper.selectCount(queryWrapper);
                if (count > 0) {
                    return Response.error(ResponseCode.BAD_REQUEST, "模板编码已存在");
                }
            }

            if (StrUtil.isNotBlank(dto.templateName)) {
                templateDO.setTemplateName(dto.templateName);
            }
            if (StrUtil.isNotBlank(dto.templateCode)) {
                templateDO.setTemplateCode(dto.templateCode);
            }
            if (StrUtil.isNotBlank(dto.templateType)) {
                templateDO.setTemplateType(dto.templateType);
            }
            if (StrUtil.isNotBlank(dto.description)) {
                templateDO.setDescription(dto.description);
            }
            if (StrUtil.isNotBlank(dto.iconUrl)) {
                templateDO.setIconUrl(dto.iconUrl);
            }
            if (StrUtil.isNotBlank(dto.configJson)) {
                templateDO.setConfigJson(dto.configJson);
            }
            if (StrUtil.isNotBlank(dto.previewImageUrl)) {
                templateDO.setPreviewImageUrl(dto.previewImageUrl);
            }
            if (StrUtil.isNotBlank(dto.category)) {
                templateDO.setCategory(dto.category);
            }
            if (StrUtil.isNotBlank(dto.tags)) {
                templateDO.setTags(dto.tags);
            }

            templateDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                templateDO.setUpdatedBy(Access.userId());
            }

            templateMapper.updateById(templateDO);

            return Response.success(convertToVO(templateDO));
        } catch (Exception e) {
            log.error("Error in updateTemplate", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> deleteTemplate(Long id) {
        try {
            TemplateDO templateDO = templateMapper.selectById(id);
            if (templateDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "模板不存在");
            }

            // MyBatis-Plus的@TableLogic会自动处理软删除
            templateMapper.deleteById(id);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in deleteTemplate", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<TemplateVO> getTemplateById(Long id) {
        try {
            TemplateDO templateDO = templateMapper.selectById(id);
            if (templateDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "模板不存在");
            }

            return Response.success(convertToVO(templateDO));
        } catch (Exception e) {
            log.error("Error in getTemplateById", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<PageInfo<TemplateVO>> listTemplates(PageQueryDTO dto) {
        try {
            PageHelper.startPage(dto.pageNum != null ? dto.pageNum : 1,
                               dto.pageSize != null ? dto.pageSize : 10);

            LambdaQueryWrapper<TemplateDO> queryWrapper = new LambdaQueryWrapper<>();

            // 关键字搜索
            if (StrUtil.isNotBlank(dto.keyword)) {
                queryWrapper.and(wrapper -> wrapper
                    .like(TemplateDO::getTemplateName, dto.keyword)
                    .or()
                    .like(TemplateDO::getTemplateCode, dto.keyword)
                    .or()
                    .like(TemplateDO::getDescription, dto.keyword)
                );
            }

            queryWrapper.orderByDesc(TemplateDO::getCreatedAt);

            List<TemplateDO> list = templateMapper.selectList(queryWrapper);
            PageInfo<TemplateDO> pageInfo = new PageInfo<>(list);

            List<TemplateVO> voList = list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

            PageInfo<TemplateVO> result = new PageInfo<>();
            result.setList(voList);
            result.setTotal(pageInfo.getTotal());
            result.setPageNum(pageInfo.getPageNum());
            result.setPageSize(pageInfo.getPageSize());
            result.setPages(pageInfo.getPages());

            return Response.success(result);
        } catch (Exception e) {
            log.error("Error in listTemplates", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<List<TemplateVO>> listTemplatesByCategory(String category) {
        try {
            LambdaQueryWrapper<TemplateDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TemplateDO::getCategory, category);
            queryWrapper.orderByDesc(TemplateDO::getCreatedAt);

            List<TemplateDO> list = templateMapper.selectList(queryWrapper);
            List<TemplateVO> voList = list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

            return Response.success(voList);
        } catch (Exception e) {
            log.error("Error in listTemplatesByCategory", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<List<TemplateVO>> listTemplatesByType(String templateType) {
        try {
            LambdaQueryWrapper<TemplateDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TemplateDO::getTemplateType, templateType);
            queryWrapper.orderByDesc(TemplateDO::getCreatedAt);

            List<TemplateDO> list = templateMapper.selectList(queryWrapper);
            List<TemplateVO> voList = list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

            return Response.success(voList);
        } catch (Exception e) {
            log.error("Error in listTemplatesByType", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void incrementUseCount(Long id) {
        try {
            TemplateDO templateDO = templateMapper.selectById(id);
            if (templateDO != null) {
                templateDO.setUseCount(templateDO.getUseCount() != null ? templateDO.getUseCount() + 1 : 1);
                templateMapper.updateById(templateDO);
            }
        } catch (Exception e) {
            log.error("Error in incrementUseCount", e);
        }
    }

    /**
     * DO转VO
     */
    private TemplateVO convertToVO(TemplateDO templateDO) {
        if (templateDO == null) {
            return null;
        }

        TemplateVO vo = new TemplateVO();
        vo.setId(templateDO.getId());
        vo.setTemplateName(templateDO.getTemplateName());
        vo.setTemplateCode(templateDO.getTemplateCode());
        vo.setTemplateType(templateDO.getTemplateType());
        vo.setDescription(templateDO.getDescription());
        vo.setIconUrl(templateDO.getIconUrl());
        vo.setConfigJson(templateDO.getConfigJson());
        vo.setPreviewImageUrl(templateDO.getPreviewImageUrl());
        vo.setIsSystem(templateDO.getIsSystem());
        vo.setCategory(templateDO.getCategory());
        vo.setTags(templateDO.getTags());
        vo.setUseCount(templateDO.getUseCount());
        vo.setCreatedAt(templateDO.getCreatedAt());

        return vo;
    }
}
