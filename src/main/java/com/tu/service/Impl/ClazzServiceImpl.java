package com.tu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tu.mapper.ClazzMapper;
import com.tu.pojo.Clazz;
import com.tu.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public IPage getClazzsByOpr(Page<Clazz> page, Clazz clazz) {
        LambdaQueryWrapper<Clazz> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(clazz.getGradeName()),Clazz::getGradeName,clazz.getGradeName());
        queryWrapper.like(StringUtils.isNotBlank(clazz.getName()),Clazz::getName,clazz.getName());
        Page<Clazz> clazzPage = clazzMapper.selectPage(page, queryWrapper);
        return clazzPage;
    }

    @Override
    public void saveOrUpdateClazz(Clazz clazz) {
        if(clazz.getId() == null){
            clazzMapper.insert(clazz);
        }else{
            clazzMapper.updateById(clazz);
        }

    }
}
