package com.aizz.mindmingle.persistence.dao;

import com.aizz.mindmingle.domain.dos.StudentDO;
import com.aizz.mindmingle.persistence.mapper.StudentMapper;
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
