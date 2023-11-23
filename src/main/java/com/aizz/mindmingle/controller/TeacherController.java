package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.entity.dos.TeacherDO;
import com.aizz.mindmingle.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/list")
    public Response<List<TeacherDO>> list(){
        return Response.success(teacherService.list());
    }

    @PostMapping("/add")
    public Response<Void> add(@RequestBody TeacherDO teacherDO){
        teacherService.add(teacherDO);
        return Response.success();
    }

    @PostMapping("/edit")
    public Response<Void> edit(@RequestBody TeacherDO teacherDO){
        teacherService.edit(teacherDO);
        return Response.success();
    }

    @GetMapping("/detail")
    public Response<TeacherDO> detail(@RequestParam("id") Long id){
        return Response.success(teacherService.detail(id));
    }

    @PostMapping("/remove")
    public Response<Void> remove(@RequestParam("id") Long id){
        teacherService.remove(id);
        return Response.success();
    }
}
