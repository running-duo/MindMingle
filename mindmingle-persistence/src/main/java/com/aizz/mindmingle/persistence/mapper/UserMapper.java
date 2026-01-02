package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
