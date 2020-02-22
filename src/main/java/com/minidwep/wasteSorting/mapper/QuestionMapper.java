package com.minidwep.wasteSorting.mapper;

import com.minidwep.wasteSorting.bean.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionMapper {
    Question getQuestionById(Integer id);

    List<Question> getPaperQuestions();
}
