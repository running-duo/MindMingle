package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.common.annotation.RequireToolPermission;
import com.aizz.mindmingle.domain.dto.ToolDTO;
import com.aizz.mindmingle.domain.dto.ToolQueryDTO;
import com.aizz.mindmingle.domain.vo.ToolVO;
import com.aizz.mindmingle.service.ToolService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 工具管理控制器
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/api/v1/tools")
public class ToolController {

    private static final Logger log = LoggerFactory.getLogger(ToolController.class);

    @Autowired
    private ToolService toolService;

    @PostMapping
    public Response<ToolVO> createTool(@RequestBody ToolDTO toolDTO) {
        log.info("Creating tool: {}", toolDTO.getToolName());
        return toolService.createTool(toolDTO);
    }

    @RequireToolPermission(2)
    @PutMapping("/{id}")
    public Response<ToolVO> updateTool(@PathVariable Long id, @RequestBody ToolDTO toolDTO) {
        log.info("Updating tool: {}", id);
        return toolService.updateTool(id, toolDTO);
    }

    @RequireToolPermission(3)
    @DeleteMapping("/{id}")
    public Response<Void> deleteTool(@PathVariable Long id) {
        log.info("Deleting tool: {}", id);
        return toolService.deleteTool(id);
    }

    @GetMapping("/{id}")
    public Response<ToolVO> getToolById(@PathVariable Long id) {
        return toolService.getToolById(id);
    }

    @GetMapping("/list")
    public Response<PageInfo<ToolVO>> listTools(ToolQueryDTO toolQueryDTO) {
        return toolService.listTools(toolQueryDTO);
    }

    @GetMapping("/my")
    public Response<PageInfo<ToolVO>> listMyTools(ToolQueryDTO toolQueryDTO) {
        return toolService.listMyTools(toolQueryDTO);
    }

    @RequireToolPermission(3)
    @PutMapping("/{id}/publish")
    public Response<Void> publishTool(@PathVariable Long id, @RequestParam Integer isPublic) {
        log.info("Publishing tool: {} as {}", id, isPublic == 1 ? "public" : "private");
        return toolService.publishTool(id, isPublic);
    }

    @RequireToolPermission(3)
    @PutMapping("/{id}/status")
    public Response<Void> updateToolStatus(@PathVariable Long id, @RequestParam Integer status) {
        log.info("Updating tool status: {} to {}", id, status);
        return toolService.updateToolStatus(id, status);
    }
}
