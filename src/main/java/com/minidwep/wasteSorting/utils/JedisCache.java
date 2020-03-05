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
public class JedisCache {
    @Autowired
    private JedisCluster jedisCluster;

    public  String get(String key) {

        try {
            String value = jedisCluster.get(key);
            return value;
        } catch (Exception e) {

            log.error(e.getMessage());
            return "0";
        }

    }

    public boolean set(String key, String value) {

        try {
            jedisCluster.set(key,value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }




    }
