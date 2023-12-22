package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.entity.dos.TeacherDO;
import com.aizz.mindmingle.entity.dto.RemoveDTO;
import com.aizz.mindmingle.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师模块
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list")
    public Response<List<TeacherDO>> list() {
        return Response.success(teacherService.list());
    }

    /**
     * 新增
     *
     * @param teacherDO
     * @return
     */
    @PostMapping("/add")
    public Response<Void> add(@RequestBody TeacherDO teacherDO) {
        teacherService.add(teacherDO);
        return Response.success();
    }

    /**
     * 修改
     *
     * @param teacherDO
     * @return
     */
    @PostMapping("/edit")
    public Response<Void> edit(@RequestBody TeacherDO teacherDO) {
        teacherService.edit(teacherDO);
        return Response.success();
    }

    /**
     * 详情
     *
     * @param id 教师id
     * @return
     */
    @GetMapping("/detail")
    public Response<TeacherDO> detail(@RequestParam("id") Long id) {
        return Response.success(teacherService.detail(id));
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    public Response<Void> remove(@RequestBody RemoveDTO removeDTO) {
        teacherService.remove(removeDTO);
        return Response.success();
    }
}
