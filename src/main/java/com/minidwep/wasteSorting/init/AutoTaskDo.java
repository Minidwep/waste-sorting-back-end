package com.minidwep.wasteSorting.init;

import com.minidwep.wasteSorting.utils.JedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class AutoTaskDo {
    @Autowired
    private JedisCluster jedisCluster;

        //3.添加定时任务
        @Scheduled(cron = "0 0 23 * * ?")
        //每天23点刷新排行榜
//        @Scheduled(fixedRate=10000)
        private void configureTasks() {
            jedisCluster.del("rank");
            log.info("执行定时任务,每天的11点情况排行榜数据");
        }
}
