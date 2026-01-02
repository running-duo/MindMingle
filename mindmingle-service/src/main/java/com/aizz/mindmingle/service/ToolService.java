package com.aizz.mindmingle.service;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.ToolDTO;
import com.aizz.mindmingle.domain.dto.ToolQueryDTO;
import com.aizz.mindmingle.domain.vo.ToolVO;
import com.github.pagehelper.PageInfo;

/**
 * 工具服务接口
 */
public interface ToolService {

    /**
     * 创建工具
     */
    Response<ToolVO> createTool(ToolDTO dto);

    /**
     * 更新工具
     */
    Response<ToolVO> updateTool(Long id, ToolDTO dto);

    /**
     * 删除工具
     */
    Response<Void> deleteTool(Long id);

    /**
     * 获取工具详情
     */
    Response<ToolVO> getToolById(Long id);

    /**
     * 分页查询工具列表
     */
    Response<PageInfo<ToolVO>> listTools(ToolQueryDTO dto);

    /**
     * 获取我的工具列表
     */
    Response<PageInfo<ToolVO>> listMyTools(ToolQueryDTO dto);

    /**
     * 发布/取消发布工具
     */
    Response<Void> publishTool(Long id, Integer isPublic);

    /**
     * 更新工具状态
     */
    Response<Void> updateToolStatus(Long id, Integer status);

    /**
     * 增加工具浏览次数
     */
    void incrementViewCount(Long id);

    /**
     * 增加工具使用次数
     */
    void incrementUseCount(Long id);
}
