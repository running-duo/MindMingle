package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户 Mapper 接口
 *
 * @author zhangyuliang
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

}
