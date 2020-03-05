package com.minidwep.wasteSorting.controller;

import com.minidwep.wasteSorting.config.RedisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@Controller
@RestController
@Slf4j
public class testController {
    @Autowired
    private StringEncryptor encryptor;

        @GetMapping("/logtest1")
        public String test1(){
            log.debug("debug测试日志消息");
            log.info("info 测试日志信息");
            log.error("info 测试日志信息");
            log.warn("warn 测试日志信息");
//            ApplicationContext context = new AnnotationConfigApplicationContext(RedisAutoConfiguration.class);
//            JedisCluster jedisCluster = (JedisCluster)context.getBean("jedisCluster");
//            String java = jedisCluster.get("java");
//            System.out.println(java);

            return "ok";
        }

    @GetMapping("/jasypt/{key}")
    public void testJasypt(@PathVariable("key")String key) {
        String password = key;
        String encryptPwd = encryptor.encrypt(password);
        System.out.println("加密:：" + encryptPwd);
        System.out.println("解密：" + encryptor.decrypt(encryptPwd));
    }

}
