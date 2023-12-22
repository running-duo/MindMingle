package com.aizz.mindmingle.dao;

import com.aizz.mindmingle.entity.dos.StudentDO;
import com.aizz.mindmingle.mapper.StudentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 学生DAO层
 *
 * @author zhangyuliang
 */
@Repository
public class StudentDAO extends ServiceImpl<StudentMapper, StudentDO> {
}
