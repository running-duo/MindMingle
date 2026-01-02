package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.PageQueryDTO;
import com.aizz.mindmingle.domain.dto.TenantDTO;
import com.aizz.mindmingle.domain.vo.TenantVO;
import com.aizz.mindmingle.service.TenantService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 租户管理控制器
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/api/v1/tenants")
public class TenantController {

    private static final Logger log = LoggerFactory.getLogger(TenantController.class);

    @Autowired
    private TenantService tenantService;

    @PostMapping
    public Response<TenantVO> createTenant(@RequestBody TenantDTO tenantDTO) {
        log.info("Creating tenant: {}", tenantDTO.getTenantName());
        return tenantService.createTenant(tenantDTO);
    }

    @PutMapping("/{id}")
    public Response<TenantVO> updateTenant(@PathVariable Long id, @RequestBody TenantDTO tenantDTO) {
        log.info("Updating tenant: {}", id);
        return tenantService.updateTenant(id, tenantDTO);
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteTenant(@PathVariable Long id) {
        log.info("Deleting tenant: {}", id);
        return tenantService.deleteTenant(id);
    }

    @GetMapping("/{id}")
    public Response<TenantVO> getTenantById(@PathVariable Long id) {
        return tenantService.getTenantById(id);
    }

    @GetMapping("/code/{code}")
    public Response<TenantVO> getTenantByCode(@PathVariable String code) {
        return tenantService.getTenantByCode(code);
    }

    @GetMapping("/list")
    public Response<PageInfo<TenantVO>> listTenants(PageQueryDTO pageQueryDTO) {
        return tenantService.listTenants(pageQueryDTO);
    }

    @PutMapping("/{id}/status")
    public Response<Void> updateTenantStatus(@PathVariable Long id, @RequestParam Integer status) {
        log.info("Updating tenant status: {} to {}", id, status);
        return tenantService.updateTenantStatus(id, status);
    }
}
