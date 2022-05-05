package com.tu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tu.mapper.TeacherMapper;
import com.tu.pojo.LoginForm;
import com.tu.pojo.Student;
import com.tu.pojo.Teacher;
import com.tu.service.TeacherService;
import com.tu.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("teacherServiceImpl")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;


    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password",MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public IPage<Teacher> getStudentByOpr(Page page, Teacher teacher) {

        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotBlank(teacher.getClazzName()),Teacher::getClazzName,teacher.getClazzName());
        queryWrapper.like(StringUtils.isNotBlank(teacher.getName()),Teacher::getName,teacher.getName());
        Page page1 = teacherMapper.selectPage(page, queryWrapper);
        return page1;
    }

    @Override
    public void saveOrUpdateTeacher(Teacher teacher) {
        if(teacher.getId() == null){
            teacherMapper.insert(teacher);
        }else {
            teacherMapper.updateById(teacher);
        }
    }
}
