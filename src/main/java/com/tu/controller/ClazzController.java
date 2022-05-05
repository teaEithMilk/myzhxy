package com.tu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tu.pojo.Clazz;
import com.tu.service.ClazzService;
import com.tu.util.Result;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping("/getClazzs")
    public Result getClazzs(){
        return Result.ok(clazzService.list());
    }

    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> list){
        clazzService.removeByIds(list);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz){
        clazzService.saveOrUpdateClazz(clazz);
        return Result.ok();
    }

    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(@PathVariable  Integer pageNo,
                                 @PathVariable Integer pageSize,
                                 Clazz clazz){
        Page<Clazz> page = new Page<>(pageNo,pageSize);
        IPage page2  =  clazzService.getClazzsByOpr(page,clazz);
        return Result.ok(page2);
    }

}
