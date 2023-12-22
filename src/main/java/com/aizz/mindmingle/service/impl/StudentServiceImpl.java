package com.aizz.mindmingle.service.impl;

import com.aizz.mindmingle.dao.StudentDAO;
import com.aizz.mindmingle.entity.dos.StudentDO;
import com.aizz.mindmingle.entity.dto.RemoveDTO;
import com.aizz.mindmingle.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDAO studentDAO;

    @Override
    public List<StudentDO> list() {
        return studentDAO.list();
    }

    @Override
    public void add(StudentDO studentDO) {
        studentDO.setCreateTime(new Date());
        studentDAO.save(studentDO);
    }

    @Override
    public void edit(StudentDO studentDO) {
        studentDO.setUpdateTime(new Date());
        studentDAO.updateById(studentDO);
    }

    @Override
    public StudentDO detail(Long id) {
        return studentDAO.getById(id);
    }

    @Override
    public void remove(RemoveDTO removeDTO) {
        List<Long> ids = removeDTO.getIds();
        ids.forEach(id ->{
            studentDAO.removeById(id);
        });
    }
}
