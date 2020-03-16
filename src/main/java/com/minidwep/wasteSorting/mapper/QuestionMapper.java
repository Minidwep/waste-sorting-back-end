package com.minidwep.wasteSorting.mapper;

import com.minidwep.wasteSorting.bean.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionMapper {
    Question getQuestionById(@Param("id")Integer id);

    List<Question> getPaperQuestions(@Param("count")Integer count);
}
