package com.minidwep.wasteSorting.service;

import com.alibaba.fastjson.JSONArray;
import com.minidwep.wasteSorting.bean.Question;
import com.minidwep.wasteSorting.mapper.QuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    @Resource
    QuestionMapper questionMapper;

    @Autowired
    JedisCluster jedisCluster;

    @Override
    public Question getOne(Integer id) {
        return questionMapper.getQuestionById(id);
    }

    @Override
    public List<Question> getPaperQuestion(int count) {

        return questionMapper.getPaperQuestions(count);
    }

    @Override
    public List<Question> getPaperQuestionInRedis(int count) {
        List<String> questJsons = jedisCluster.srandmember("questionPool", 10);
        List<Question> questions = new ArrayList<>();
        for (String questJson : questJsons) {
            Question question = JSONArray.parseObject(questJson, Question.class);
            questions.add(question);
        }
        return questions;
    }

    @Override
    public void refreshQuestionPool() {
        //        在数据库中抽取100个题目。
        List<Question> questions = this.getPaperQuestion(100);
//        存入redis
        log.info("管理员重新拉取数据");
        jedisCluster.del("questionPool");
        for (Question question : questions) {
            Object obj = JSONArray.toJSON(question);
            String json = obj.toString();
            jedisCluster.sadd("questionPool",json);
        }

    }
}
