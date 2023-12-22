package com.aizz.mindmingle.service.impl;

import com.aizz.mindmingle.dao.TeacherDAO;
import com.aizz.mindmingle.entity.dos.TeacherDO;
import com.aizz.mindmingle.entity.dto.RemoveDTO;
import com.aizz.mindmingle.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDAO teacherDAO;

    @Override
    public List<TeacherDO> list() {
        return teacherDAO.list();
    }

    @Override
    public void add(TeacherDO teacherDO) {
        teacherDO.setCreateTime(new Date());
        teacherDAO.save(teacherDO);
    }

    @Override
    public void edit(TeacherDO teacherDO) {
        teacherDO.setUpdateTime(new Date());
        teacherDAO.updateById(teacherDO);
    }

    @Override
    public TeacherDO detail(Long id) {
        return teacherDAO.getById(id);
    }

    @Override
    public void remove(RemoveDTO removeDTO) {
        List<Long> ids = removeDTO.getIds();
        ids.forEach(id -> {
            teacherDAO.removeById(id);
        });
    }
}
