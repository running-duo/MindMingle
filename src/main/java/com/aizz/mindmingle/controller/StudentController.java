package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.entity.dos.StudentDO;
import com.aizz.mindmingle.entity.dto.RemoveDTO;
import com.aizz.mindmingle.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生模块
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list")
    public Response<List<StudentDO>> list() {
        return Response.success(studentService.list());
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Response<Void> add(@RequestBody StudentDO studentDO) {
        studentService.add(studentDO);
        return Response.success();
    }

    /**
     * 修改
     */
    @PostMapping("/edit")
    public Response<Void> edit(@RequestBody StudentDO studentDO) {
        studentService.edit(studentDO);
        return Response.success();
    }

    /**
     * 详情
     */
    @GetMapping("/detail")
    public Response<StudentDO> detail(@RequestParam("id") Long id) {
        return Response.success(studentService.detail(id));
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    public Response<Void> remove(@RequestBody RemoveDTO removeDTO) {
        studentService.remove(removeDTO);
        return Response.success();
    }
}
