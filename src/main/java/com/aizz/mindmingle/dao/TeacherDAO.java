package com.aizz.mindmingle.dao;

import com.aizz.mindmingle.entity.dos.TeacherDO;
import com.aizz.mindmingle.mapper.TeacherMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDAO extends ServiceImpl<TeacherMapper, TeacherDO> {
}
