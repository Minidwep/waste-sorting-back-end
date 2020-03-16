package com.minidwep.wasteSorting.init;

import com.alibaba.fastjson.JSONArray;
import com.minidwep.wasteSorting.bean.Question;
import com.minidwep.wasteSorting.service.QuestionService;
import com.minidwep.wasteSorting.utils.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class AutoTaskDo {
    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    QuestionService questionService;

    @Autowired
    JedisCluster jedisCluster;


    //3.添加定时任务
    @Scheduled(cron = "0 0 23 * * ?")
    //每天23点刷新排行榜
//        @Scheduled(fixedRate=10000)
    private void delRankTasks() {
        log.info("执行定时任务,每天的11点清空排行榜数据。");
        jedisUtil.del("rank");
        String rankType = jedisUtil.type("rank");
        if(!"node".equals(rankType)){
            log.warn("排行榜数据失败。");
        } else {
            log.warn("排行榜数据成功。");
        }
    }


    //3.添加定时任务
    @Scheduled(cron = "0 0 0 * * ?")
    //每天23点刷新排行榜
//        @Scheduled(fixedRate=10000)
    private void reloadRank() {
        log.info("执行定时任务,每天的0点刷新题库。");
//              在数据库中抽取100个题目。
        jedisCluster.del("questionPool");
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
