package com.aizz.mindmingle.service;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dos.TenantDO;
import com.aizz.mindmingle.domain.dto.PageQueryDTO;
import com.aizz.mindmingle.domain.dto.TenantDTO;
import com.aizz.mindmingle.domain.vo.TenantVO;
import com.github.pagehelper.PageInfo;

/**
 * 租户服务接口
 */
public interface TenantService {

    /**
     * 创建租户
     */
    Response<TenantVO> createTenant(TenantDTO dto);

    /**
     * 更新租户
     */
    Response<TenantVO> updateTenant(Long id, TenantDTO dto);

    /**
     * 删除租户
     */
    Response<Void> deleteTenant(Long id);

    /**
     * 获取租户详情
     */
    Response<TenantVO> getTenantById(Long id);

    /**
     * 根据租户编码获取租户
     */
    Response<TenantVO> getTenantByCode(String tenantCode);

    /**
     * 分页查询租户列表
     */
    Response<PageInfo<TenantVO>> listTenants(PageQueryDTO dto);

    /**
     * 启用/禁用租户
     */
    Response<Void> updateTenantStatus(Long id, Integer status);
}
