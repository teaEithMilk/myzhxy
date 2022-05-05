package com.tu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tu.pojo.Admin;
import com.tu.pojo.Clazz;
import com.tu.service.AdminService;
import com.tu.util.Result;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@PathVariable Integer pageNo,
                              @PathVariable Integer pageSize,
                              Admin admin) {
        Page<Admin> page = new Page<>(pageNo, pageSize);
        IPage page2 = adminService.getClazzsByOpr(page, admin);
        return Result.ok(page2);
    }

    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> list){
        adminService.removeByIds(list);
        return Result.ok();
    }


}
