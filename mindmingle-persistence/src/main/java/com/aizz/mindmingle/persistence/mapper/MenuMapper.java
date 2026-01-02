package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.MenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单Mapper
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuDO> {
}
