package com.tu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tu.pojo.Grade;
import com.tu.service.GradeService;
import com.tu.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> list = gradeService.list();
        return Result.ok(list);
    }

    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody List<Integer> list){
        gradeService.removeByIds(list);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade){
        boolean isok =  gradeService.saveOrUpdateGrade(grade);
        return  Result.ok();
    }

    //查询年级信息
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(@PathVariable("pageNo") Integer pageNo,
                            @PathVariable("pageSize") Integer pageSize,
                            String gradeName){
        Page page = new Page(pageNo, pageSize);
        IPage<Grade> pageRs =  gradeService.getGradeByOpr(page,gradeName);
        return Result.ok(pageRs);
    }



}
