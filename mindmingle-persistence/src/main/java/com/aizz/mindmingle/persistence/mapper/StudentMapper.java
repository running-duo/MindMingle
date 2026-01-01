package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.StudentDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhangyuliang
 */
@Mapper
public interface StudentMapper extends BaseMapper<StudentDO> {
}
