package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.Question;
import com.minidwep.wasteSorting.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class QuestionServiceImpl implements QuestionService {
    @Resource
    QuestionMapper questionMapper;
    @Override
    public Question getOne(Integer id) {
        return questionMapper.getQuestionById(id);
    }

    @Override
    public List<Question> getPaperQuestion() {

        return questionMapper.getPaperQuestions();
    }
}
