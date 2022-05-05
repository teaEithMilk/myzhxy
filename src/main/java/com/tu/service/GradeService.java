package com.tu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tu.pojo.Grade;
import org.springframework.stereotype.Service;


public interface GradeService extends IService<Grade> {
    IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName);

    boolean saveOrUpdateGrade(Grade grade);
}
