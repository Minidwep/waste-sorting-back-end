package com.minidwep.wasteSorting.utils;

import com.minidwep.wasteSorting.bean.Rubbish;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Set;

@Aspect
@Component
@Slf4j
public class WebLogAspect {
    @Autowired
    JedisCluster jedisCluster;

    //    @Pointcut("execution(public * com.minidwep.wasteSorting.service.RubbishService.rubbishByRubNameWithMaxWeight())")//切入点描述 这个是controller包的切入点
    @Pointcut("execution(public * com.minidwep.wasteSorting.service.RubbishService.rubbishByRubNameWithMaxWeight*(..))")
    public void controllerLog() {
    }//签名，可以理解成这个切入点的一个名称


    @Before("controllerLog()") //在切入点的方法run之前要干的
    public void logBeforeController(JoinPoint joinPoint) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 记录下请求内容
        log.info("################URL : " + request.getRequestURL().toString());
        log.info("################HTTP_METHOD : " + request.getMethod());
        log.info("################IP : " + request.getRemoteAddr());
        log.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));

        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        log.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //log.info("################TARGET: " + joinPoint.getTarget());//返回的是需要加强的目标类的对象
        //log.info("################THIS: " + joinPoint.getThis());//返回的是经过加强后的代理类的对象
    }

    @After("controllerLog()")
    public void afterMethod() {
        log.info("################After : after");

    }

    @AfterReturning(returning = "returnOb", pointcut = "controllerLog()")
    public void AfterReturningMethod(JoinPoint joinPoint, Object returnOb) {
        if (returnOb != null) {
            Rubbish rubbish = (Rubbish) returnOb;
            log.info(rubbish.toString());
            try {
//                jedisCluster.del("rank");
                Long isHave = jedisCluster.zrank("rank", rubbish.getRubName());
                if (isHave == null) {
                    jedisCluster.zadd("rank", 0, rubbish.getRubName());
                } else {
                    Double zscore = jedisCluster.zscore("rank", rubbish.getRubName());
                    jedisCluster.zincrby("rank", 1, rubbish.getRubName());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        log.info("################AfterReturningMethod : AfterReturningMethod" +returnOb);
    }

}