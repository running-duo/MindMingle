package com.aizz.mindmingle.service;

import com.aizz.mindmingle.entity.dos.TeacherDO;
import com.aizz.mindmingle.entity.dto.RemoveDTO;

import java.util.List;

public interface TeacherService {

    List<TeacherDO> list();

    void add(TeacherDO teacherDO);

    void edit(TeacherDO teacherDO);

    TeacherDO detail(Long id);

    void remove(RemoveDTO removeDTO);
}
