package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.RolePermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限关联Mapper
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermissionDO> {
}
