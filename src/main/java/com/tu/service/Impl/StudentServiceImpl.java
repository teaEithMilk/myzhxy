package com.tu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tu.mapper.StudentMapper;
import com.tu.pojo.LoginForm;
import com.tu.pojo.Student;
import com.tu.service.StudentService;
import com.tu.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password",MD5.encrypt(loginForm.getPassword()));
        Student student = studentMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public IPage<Student> getStudentByOpr(Page page, Student student) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(student.getClazzName()),Student::getClazzName,student.getClazzName());
        queryWrapper.like(StringUtils.isNotBlank(student.getName()),Student::getName,student.getName());
        Page page1 = studentMapper.selectPage(page, queryWrapper);
        return page1;
    }

    @Override
    public void addOrUpdateStudent(Student student) {
        if(student.getId() == null){
            studentMapper.insert(student);
        }else {
            studentMapper.updateById(student);
        }
    }
}
