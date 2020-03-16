package com.minidwep.wasteSorting.init;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.minidwep.wasteSorting.bean.Question;
import com.minidwep.wasteSorting.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class AfterStartDo implements CommandLineRunner {
    @Autowired
    QuestionService questionService;

    @Autowired
    JedisCluster jedisCluster;

    @Override
    public void run(String... args) throws Exception {
//        在数据库中抽取100个题目。
        List<Question> questions = questionService.getPaperQuestion(100);
//        存入redis
        log.info("装载数据到redis");
        for (Question question : questions) {
            Object obj = JSONArray.toJSON(question);
            String json = obj.toString();
            jedisCluster.sadd("questionPool",json);
        }


    }
}
