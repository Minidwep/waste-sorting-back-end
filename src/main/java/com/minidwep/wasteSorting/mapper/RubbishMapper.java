package com.minidwep.wasteSorting.mapper;

import com.minidwep.wasteSorting.bean.Question;
import com.minidwep.wasteSorting.bean.Rubbish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RubbishMapper {
    Rubbish getRubbishByName(@Param("name") String name);

    Rubbish getRubbishById(Integer id);
}
