package com.aizz.mindmingle.persistence.dao;

import com.aizz.mindmingle.domain.dos.TeacherDO;
import com.aizz.mindmingle.persistence.mapper.TeacherMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 教师DAO层
 *
 * @author zhangyuliang
 */
@Repository
public class TeacherDAO extends ServiceImpl<TeacherMapper, TeacherDO> {
}
