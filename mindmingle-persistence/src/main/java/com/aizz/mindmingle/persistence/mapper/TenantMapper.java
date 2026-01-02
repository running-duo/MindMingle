package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.TenantDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租户Mapper
 */
@Mapper
public interface TenantMapper extends BaseMapper<TenantDO> {
}
