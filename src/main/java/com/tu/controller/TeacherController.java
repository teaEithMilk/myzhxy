package com.tu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tu.pojo.Student;
import com.tu.pojo.Teacher;
import com.tu.service.TeacherService;
import com.tu.util.Result;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(@PathVariable("pageNo") Integer pageNo,
                              @PathVariable("pageSize") Integer pageSize,
                              Teacher teacher){
        Page page = new Page(pageNo, pageSize);
        IPage<Teacher> pageRs = teacherService.getStudentByOpr(page,teacher);
        return Result.ok(pageRs);
    }

    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher){
        teacherService.saveOrUpdateTeacher(teacher);
        return Result.ok();
    }


    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> list){
        teacherService.removeByIds(list);
        return Result.ok();
    }

}
