package com.minidwep.wasteSorting.controller;

import com.minidwep.wasteSorting.config.RedisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RestController
@Slf4j
public class testController {
    @Autowired
    private StringEncryptor encryptor;


    @GetMapping("/jasypt/encr/{key}")
    public void testJasypt(@PathVariable("key") String key) {
        String password = key;
        String encryptPwd = encryptor.encrypt(password);
        System.out.println("加密:：" + encryptPwd);
        System.out.println("解密：" + encryptor.decrypt(encryptPwd));
    }

    @GetMapping("/jasypt/decr/{key}")
    public void getKey(@PathVariable("key") String key) {
        String password = key;
        System.out.println("解密：" + encryptor.decrypt(key));
    }



}
