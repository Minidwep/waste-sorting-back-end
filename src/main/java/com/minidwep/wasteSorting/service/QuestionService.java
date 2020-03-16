package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.Question;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    Question getOne(Integer id);

    List<Question> getPaperQuestion(int count);

    List<Question> getPaperQuestionInRedis(int count);

    void refreshQuestionPool();
}
