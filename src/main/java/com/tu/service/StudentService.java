package com.tu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tu.pojo.LoginForm;
import com.tu.pojo.Student;
import org.springframework.stereotype.Service;


public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    IPage<Student> getStudentByOpr(Page page, Student student);

    void addOrUpdateStudent(Student student);
}
