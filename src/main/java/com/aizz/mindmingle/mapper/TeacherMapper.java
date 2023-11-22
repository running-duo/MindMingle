package com.aizz.mindmingle.mapper;

import com.aizz.mindmingle.entity.dos.TeacherDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 教师mapper层
 */
@Mapper
public interface TeacherMapper extends BaseMapper<TeacherDO> {
}
