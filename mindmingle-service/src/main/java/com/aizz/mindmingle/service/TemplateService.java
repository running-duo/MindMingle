package com.aizz.mindmingle.service;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.PageQueryDTO;
import com.aizz.mindmingle.domain.dto.TemplateDTO;
import com.aizz.mindmingle.domain.vo.TemplateVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 工具模板服务接口
 */
public interface TemplateService {

    /**
     * 创建模板
     */
    Response<TemplateVO> createTemplate(TemplateDTO dto);

    /**
     * 更新模板
     */
    Response<TemplateVO> updateTemplate(Long id, TemplateDTO dto);

    /**
     * 删除模板
     */
    Response<Void> deleteTemplate(Long id);

    /**
     * 获取模板详情
     */
    Response<TemplateVO> getTemplateById(Long id);

    /**
     * 分页查询模板列表
     */
    Response<PageInfo<TemplateVO>> listTemplates(PageQueryDTO dto);

    /**
     * 根据分类获取模板列表
     */
    Response<List<TemplateVO>> listTemplatesByCategory(String category);

    /**
     * 根据类型获取模板列表
     */
    Response<List<TemplateVO>> listTemplatesByType(String templateType);

    /**
     * 增加模板使用次数
     */
    void incrementUseCount(Long id);
}
