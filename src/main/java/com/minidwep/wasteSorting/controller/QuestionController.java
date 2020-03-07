package com.minidwep.wasteSorting.controller;

import com.minidwep.wasteSorting.utils.Msg;
import com.minidwep.wasteSorting.bean.Question;
import com.minidwep.wasteSorting.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    /**
     *
     * @param id
     * @return 问题信息
     */
    @GetMapping("/question/{id}")
    @ResponseBody
    public Question getById(@PathVariable("id") Integer id){
        Question question = questionService.getOne(id);
        return question;
    }
    /**
     *
     * @param
     * @return 10个题目信息
     */
    @GetMapping("/question/getTestPaper")
    @ResponseBody
    public Msg getTestPaperQuestions(){
            List<Question> questions = questionService.getPaperQuestion();
        return Msg.success().add("questions",questions);
    }
}
