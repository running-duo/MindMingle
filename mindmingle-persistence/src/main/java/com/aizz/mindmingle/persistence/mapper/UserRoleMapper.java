package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.UserRoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联Mapper
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {
}
