package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.PageQueryDTO;
import com.aizz.mindmingle.domain.dto.TemplateDTO;
import com.aizz.mindmingle.domain.vo.TemplateVO;
import com.aizz.mindmingle.service.TemplateService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模板管理控制器
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/api/v1/templates")
public class TemplateController {

    private static final Logger log = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    private TemplateService templateService;

    @PostMapping
    public Response<TemplateVO> createTemplate(@RequestBody TemplateDTO templateDTO) {
        log.info("Creating template: {}", templateDTO.getTemplateName());
        return templateService.createTemplate(templateDTO);
    }

    @PutMapping("/{id}")
    public Response<TemplateVO> updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
        log.info("Updating template: {}", id);
        return templateService.updateTemplate(id, templateDTO);
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteTemplate(@PathVariable Long id) {
        log.info("Deleting template: {}", id);
        return templateService.deleteTemplate(id);
    }

    @GetMapping("/{id}")
    public Response<TemplateVO> getTemplateById(@PathVariable Long id) {
        return templateService.getTemplateById(id);
    }

    @GetMapping("/list")
    public Response<PageInfo<TemplateVO>> listTemplates(PageQueryDTO pageQueryDTO) {
        return templateService.listTemplates(pageQueryDTO);
    }

    @GetMapping("/category/{category}")
    public Response<List<TemplateVO>> listTemplatesByCategory(@PathVariable String category) {
        return templateService.listTemplatesByCategory(category);
    }

    @GetMapping("/type/{type}")
    public Response<List<TemplateVO>> listTemplatesByType(@PathVariable String type) {
        return templateService.listTemplatesByType(type);
    }
}
