package com.tu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tu.pojo.Admin;
import com.tu.pojo.LoginForm;
import org.springframework.stereotype.Service;


public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    IPage getClazzsByOpr(Page<Admin> page, Admin admin);
}
