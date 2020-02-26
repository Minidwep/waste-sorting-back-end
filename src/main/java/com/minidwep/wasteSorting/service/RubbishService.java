package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.Rubbish;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface RubbishService {
    Rubbish getRubbishByName(String name);
    Rubbish getRubbishById(Integer id);
}
