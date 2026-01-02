package com.aizz.mindmingle.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.common.ResponseCode;
import com.aizz.mindmingle.domain.dos.TenantDO;
import com.aizz.mindmingle.domain.dto.PageQueryDTO;
import com.aizz.mindmingle.domain.dto.TenantDTO;
import com.aizz.mindmingle.domain.vo.TenantVO;
import com.aizz.mindmingle.persistence.mapper.TenantMapper;
import com.aizz.mindmingle.security.Access;
import com.aizz.mindmingle.service.TenantService;
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
 * 租户服务实现类
 */
@Service
public class TenantServiceImpl implements TenantService {

    private static final Logger log = LoggerFactory.getLogger(TenantServiceImpl.class);

    @Autowired
    private TenantMapper tenantMapper;

    @Override
    public Response<TenantVO> createTenant(TenantDTO dto) {
        try {
            // 验证租户编码唯一性
            LambdaQueryWrapper<TenantDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TenantDO::getTenantCode, dto.tenantCode);
            Long count = tenantMapper.selectCount(queryWrapper);
            if (count > 0) {
                return Response.error(ResponseCode.BAD_REQUEST, "租户编码已存在");
            }

            TenantDO tenantDO = new TenantDO();
            tenantDO.setTenantName(dto.tenantName);
            tenantDO.setTenantCode(dto.tenantCode);
            tenantDO.setContactName(dto.contactName);
            tenantDO.setContactPhone(dto.contactPhone);
            tenantDO.setContactEmail(dto.contactEmail);
            tenantDO.setStatus(1);
            tenantDO.setMaxUsers(dto.maxUsers);
            tenantDO.setMaxTools(dto.maxTools);
            tenantDO.setExpireTime(dto.expireTime);
            tenantDO.setCreatedAt(new Date());
            tenantDO.setUpdatedAt(new Date());

            if (Access.userId() != null) {
                tenantDO.setCreatedBy(Access.userId());
                tenantDO.setUpdatedBy(Access.userId());
            }

            tenantMapper.insert(tenantDO);

            return Response.success(convertToVO(tenantDO));
        } catch (Exception e) {
            log.error("Error in createTenant", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<TenantVO> updateTenant(Long id, TenantDTO dto) {
        try {
            TenantDO tenantDO = tenantMapper.selectById(id);
            if (tenantDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "租户不存在");
            }

            // 如果修改租户编码，验证唯一性
            if (StrUtil.isNotBlank(dto.tenantCode) && !dto.tenantCode.equals(tenantDO.getTenantCode())) {
                LambdaQueryWrapper<TenantDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(TenantDO::getTenantCode, dto.tenantCode);
                queryWrapper.ne(TenantDO::getId, id);
                Long count = tenantMapper.selectCount(queryWrapper);
                if (count > 0) {
                    return Response.error(ResponseCode.BAD_REQUEST, "租户编码已存在");
                }
            }

            if (StrUtil.isNotBlank(dto.tenantName)) {
                tenantDO.setTenantName(dto.tenantName);
            }
            if (StrUtil.isNotBlank(dto.tenantCode)) {
                tenantDO.setTenantCode(dto.tenantCode);
            }
            if (StrUtil.isNotBlank(dto.contactName)) {
                tenantDO.setContactName(dto.contactName);
            }
            if (StrUtil.isNotBlank(dto.contactPhone)) {
                tenantDO.setContactPhone(dto.contactPhone);
            }
            if (StrUtil.isNotBlank(dto.contactEmail)) {
                tenantDO.setContactEmail(dto.contactEmail);
            }
            if (dto.maxUsers != null) {
                tenantDO.setMaxUsers(dto.maxUsers);
            }
            if (dto.maxTools != null) {
                tenantDO.setMaxTools(dto.maxTools);
            }
            if (dto.expireTime != null) {
                tenantDO.setExpireTime(dto.expireTime);
            }

            tenantDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                tenantDO.setUpdatedBy(Access.userId());
            }

            tenantMapper.updateById(tenantDO);

            return Response.success(convertToVO(tenantDO));
        } catch (Exception e) {
            log.error("Error in updateTenant", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> deleteTenant(Long id) {
        try {
            TenantDO tenantDO = tenantMapper.selectById(id);
            if (tenantDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "租户不存在");
            }

            // MyBatis-Plus的@TableLogic会自动处理软删除
            tenantMapper.deleteById(id);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in deleteTenant", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<TenantVO> getTenantById(Long id) {
        try {
            TenantDO tenantDO = tenantMapper.selectById(id);
            if (tenantDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "租户不存在");
            }

            return Response.success(convertToVO(tenantDO));
        } catch (Exception e) {
            log.error("Error in getTenantById", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<TenantVO> getTenantByCode(String tenantCode) {
        try {
            LambdaQueryWrapper<TenantDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TenantDO::getTenantCode, tenantCode);
            TenantDO tenantDO = tenantMapper.selectOne(queryWrapper);

            if (tenantDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "租户不存在");
            }

            return Response.success(convertToVO(tenantDO));
        } catch (Exception e) {
            log.error("Error in getTenantByCode", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<PageInfo<TenantVO>> listTenants(PageQueryDTO dto) {
        try {
            PageHelper.startPage(dto.pageNum != null ? dto.pageNum : 1,
                               dto.pageSize != null ? dto.pageSize : 10);

            LambdaQueryWrapper<TenantDO> queryWrapper = new LambdaQueryWrapper<>();

            // 关键字搜索
            if (StrUtil.isNotBlank(dto.keyword)) {
                queryWrapper.and(wrapper -> wrapper
                    .like(TenantDO::getTenantName, dto.keyword)
                    .or()
                    .like(TenantDO::getTenantCode, dto.keyword)
                );
            }

            queryWrapper.orderByDesc(TenantDO::getCreatedAt);

            List<TenantDO> list = tenantMapper.selectList(queryWrapper);
            PageInfo<TenantDO> pageInfo = new PageInfo<>(list);

            List<TenantVO> voList = list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

            PageInfo<TenantVO> result = new PageInfo<>();
            result.setList(voList);
            result.setTotal(pageInfo.getTotal());
            result.setPageNum(pageInfo.getPageNum());
            result.setPageSize(pageInfo.getPageSize());
            result.setPages(pageInfo.getPages());

            return Response.success(result);
        } catch (Exception e) {
            log.error("Error in listTenants", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> updateTenantStatus(Long id, Integer status) {
        try {
            TenantDO tenantDO = tenantMapper.selectById(id);
            if (tenantDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "租户不存在");
            }

            tenantDO.setStatus(status);
            tenantDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                tenantDO.setUpdatedBy(Access.userId());
            }

            tenantMapper.updateById(tenantDO);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in updateTenantStatus", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DO转VO
     */
    private TenantVO convertToVO(TenantDO tenantDO) {
        if (tenantDO == null) {
            return null;
        }

        TenantVO vo = new TenantVO();
        vo.setId(tenantDO.getId());
        vo.setTenantName(tenantDO.getTenantName());
        vo.setTenantCode(tenantDO.getTenantCode());
        vo.setContactName(tenantDO.getContactName());
        vo.setContactEmail(tenantDO.getContactEmail());
        vo.setStatus(tenantDO.getStatus());
        vo.setMaxUsers(tenantDO.getMaxUsers());
        vo.setMaxTools(tenantDO.getMaxTools());
        vo.setExpireTime(tenantDO.getExpireTime());
        vo.setCreatedAt(tenantDO.getCreatedAt());

        return vo;
    }
}
