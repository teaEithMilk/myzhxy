package com.tu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tu.pojo.Student;
import com.tu.service.StudentService;
import com.tu.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody  Student student){
        studentService.addOrUpdateStudent(student);
        return Result.ok();
    }

    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(@PathVariable("pageNo") Integer pageNo,
                                  @PathVariable("pageSize") Integer pageSize,
                                  Student student){
        Page page = new Page(pageNo, pageSize);
        IPage<Student> pageRs = studentService.getStudentByOpr(page,student);

        return Result.ok(pageRs);
    }

    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> list){
        studentService.removeByIds(list);
        return Result.ok().message("删除成功");
    }



}
