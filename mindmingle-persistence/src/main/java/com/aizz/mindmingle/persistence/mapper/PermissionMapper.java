package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.PermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限Mapper
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionDO> {
}
