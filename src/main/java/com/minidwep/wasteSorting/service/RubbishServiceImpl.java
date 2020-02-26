package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.Rubbish;
import com.minidwep.wasteSorting.mapper.QuestionMapper;
import com.minidwep.wasteSorting.mapper.RubbishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RubbishServiceImpl implements RubbishService{
    @Resource
    RubbishMapper rubbishMapper;
    @Override
    public Rubbish getRubbishByName(String name) {
        return rubbishMapper.getRubbishByName(name);
    }

    @Override
    public Rubbish getRubbishById(Integer id) {
        return rubbishMapper.getRubbishById(id);
    }
}
