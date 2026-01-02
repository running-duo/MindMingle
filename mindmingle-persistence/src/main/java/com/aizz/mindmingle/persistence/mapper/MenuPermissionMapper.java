package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.MenuPermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单权限关联Mapper
 */
@Mapper
public interface MenuPermissionMapper extends BaseMapper<MenuPermissionDO> {
}
