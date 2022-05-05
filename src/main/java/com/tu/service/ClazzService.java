package com.tu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tu.pojo.Clazz;
import org.springframework.stereotype.Service;


public interface ClazzService extends IService<Clazz> {

    IPage getClazzsByOpr(Page<Clazz> page, Clazz clazz);

    void saveOrUpdateClazz(Clazz clazz);


}
