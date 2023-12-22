package com.aizz.mindmingle.mapper;

import com.aizz.mindmingle.entity.dos.StudentDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhangyuliang
 */
@Mapper
public interface StudentMapper extends BaseMapper<StudentDO> {
}
