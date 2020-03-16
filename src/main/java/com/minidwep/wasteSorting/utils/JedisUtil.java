package com.minidwep.wasteSorting.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Component
@Slf4j
public class JedisUtil {
    @Autowired
    private JedisCluster jedisCluster;

    public String get(String key) {

        try {
            String value = jedisCluster.get(key);
            return value;
        } catch (Exception e) {

            log.error(e.getMessage());
            return "0";
        }

    }

    public boolean setWithExpire(String key, String value) {

        try {
            jedisCluster.set(key, value);
            int i = (int) (Math.random() * 300 + 120);
            jedisCluster.expire(key, i);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void del(String key) {
        try {
            jedisCluster.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  Long isHave = jedisCluster.zrank("rank", rubbish.getRubName());
    public Long zrank(String key, String value) {
        try {
            Long isHave = jedisCluster.zrank(key, value);
            return isHave;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

//     jedisCluster.zadd("rank", 0, rubbish.getRubName());

    public void zadd(String key,int count ,String name){
        try {
            jedisCluster.zadd(key, count, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//
//     jedisCache.zincrby("rank", 1, rubbish.getRubName());
    public Double zscore(String key, String value) {
        try {
            Double zscore = jedisCluster.zscore(key, value);
            return zscore;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void zincrby(String key, int i, String value) {
        try {
            jedisCluster.zincrby(key, 1, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void expire(String key,int i){
        try {
            jedisCluster.expire(key, i);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String type(String key){
        String type = null;
        try {
            type = jedisCluster.type(key);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

}

