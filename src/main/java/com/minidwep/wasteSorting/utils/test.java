package com.minidwep.wasteSorting.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class test {

    public static JedisCluster getJedis() {
        final String redisURL1 = "39.106.72.218";
        final String redisURL2 = "47.113.113.196";
        final String redisURL3 = "39.96.72.169";
        final String auth = "";
        final int port7001 = 7001;
        final int port7002 = 7002;


        final int MAX_IDLE = 200;
        final int MAX_TOTAL = 1024;
        final int CONN_TIME_OUT = 1000;//链接server超时
        final int SO_TIME_OUT = 1000;//等待response超时
        final int MAX_ATTEMPTS = 1;//尝试重新链接次数

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(MAX_TOTAL);
        poolConfig.setMaxIdle(MAX_IDLE);

        HostAndPort hostAndPort1 = new HostAndPort(redisURL1, port7001);
        HostAndPort hostAndPort2 = new HostAndPort(redisURL1, port7002);
        HostAndPort hostAndPort3 = new HostAndPort(redisURL2, port7001);
        HostAndPort hostAndPort4 = new HostAndPort(redisURL2, port7002);
        HostAndPort hostAndPort5 = new HostAndPort(redisURL3, port7001);
        HostAndPort hostAndPort6 = new HostAndPort(redisURL3, port7002);

        Set<HostAndPort> hostAndPortSet = new HashSet<>(6);
        hostAndPortSet.add(hostAndPort1);
        hostAndPortSet.add(hostAndPort2);
        hostAndPortSet.add(hostAndPort3);
        hostAndPortSet.add(hostAndPort4);
        hostAndPortSet.add(hostAndPort5);
        hostAndPortSet.add(hostAndPort6);

        JedisCluster jedisCluster1 = new JedisCluster(hostAndPortSet, CONN_TIME_OUT, SO_TIME_OUT, MAX_ATTEMPTS, poolConfig);
        return jedisCluster1;
    }

    public static void main(String[] args) {


//        try {
//            JedisCluster jedisCluster = getJedis();
//            String rubbish = jedisCluster.get("rubbish王冠锐");
//            System.out.println(rubbish);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            JedisCluster jedisCluster = getJedis();
            jedisCluster.del("test1");
            jedisCluster.zadd("test1", 10, "香蕉皮");
            jedisCluster.zadd("test1", 20, "南瓜20皮");
            jedisCluster.zadd("test1", 50, "苹果50皮");
            Long isHave = jedisCluster.zrank("test1", "西瓜皮");
            if(isHave == null){
                System.out.println("不存在");
                jedisCluster.zadd("test1", 0, "西瓜皮");
                System.out.println("加入");
            } else {
                System.out.println(isHave);
                System.out.println("增量1");
                Double zscore = jedisCluster.zscore("test1", "西瓜皮");
                System.out.println(zscore);
                jedisCluster.zincrby("test1",1,"西瓜皮");
            }
            Set<String> test1 = jedisCluster.zrevrangeByScore("rank", 10000, -1);
            for(String str :test1){
                Double zscore = jedisCluster.zscore("rank", str);
                System.out.println(zscore+"-"+str);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        String time = "0";
        if(time.equals("24")){
            SimpleDateFormat df = new SimpleDateFormat("HH");//设置日期格式
            time =df.format(new Date());

        }
    }

}
