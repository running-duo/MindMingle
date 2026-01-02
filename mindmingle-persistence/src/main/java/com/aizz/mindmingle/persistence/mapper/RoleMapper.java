package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.RoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色Mapper
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {
}
