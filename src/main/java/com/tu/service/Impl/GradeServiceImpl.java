package com.tu.service.Impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tu.mapper.GradeMapper;
import com.tu.pojo.Grade;
import com.tu.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;




    @Override
    public boolean saveOrUpdateGrade(Grade grade) {
        int i = 0;
        if(grade.getId() == null){
            //添加
             i = gradeMapper.insert(grade);

        }else{
            //修改
             i = gradeMapper.updateById(grade);
        }
        return i>0;
    }

    //获取所有年级分页加条件
    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        // 设置查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        if(!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("name",gradeName);
        }
        // 设置排序规则
        queryWrapper.orderByDesc("id");
        queryWrapper.orderByAsc("name");
        // 分页查询数据
        Page page2 = baseMapper.selectPage(page, queryWrapper);

        return page2;
    }

}
