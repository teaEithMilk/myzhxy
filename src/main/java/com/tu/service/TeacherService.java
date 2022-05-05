package com.tu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tu.pojo.LoginForm;
import com.tu.pojo.Student;
import com.tu.pojo.Teacher;
import org.springframework.stereotype.Service;

public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);


    IPage<Teacher> getStudentByOpr(Page page, Teacher teacher);

    void saveOrUpdateTeacher(Teacher teacher);
}
