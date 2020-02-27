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
    Rubbish getRubbishByName(@Param("rubName") String rubName);

    Rubbish getRubbishById(@Param("id") Integer id);

    void addRubbish(@Param("rubbish")Rubbish rubbish);

    void updateRubbish(@Param("rubbish")Rubbish rubbish);

}
