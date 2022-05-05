package com.tu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tu.mapper.AdminMapper;
import com.tu.pojo.Admin;
import com.tu.pojo.Clazz;
import com.tu.pojo.LoginForm;
import com.tu.pojo.Student;
import com.tu.service.AdminService;
import com.tu.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("adminServiceImpl")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password",MD5.encrypt(loginForm.getPassword()));
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public IPage getClazzsByOpr(Page<Admin> page, Admin admin) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(admin.getName()),Admin::getName,admin.getName());
        Page<Admin> clazzPage = adminMapper.selectPage(page, queryWrapper);
        return clazzPage;
    }


}
