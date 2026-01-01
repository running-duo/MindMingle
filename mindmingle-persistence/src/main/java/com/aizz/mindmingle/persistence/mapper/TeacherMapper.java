package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.TeacherDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 教师mapper层
 */
@Mapper
public interface TeacherMapper extends BaseMapper<TeacherDO> {
}
