package com.minidwep.wasteSorting.aspect;

import com.minidwep.wasteSorting.bean.Rubbish;
import com.minidwep.wasteSorting.utils.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RedisRankAspect {
    @Autowired
    JedisUtil jedisUtil;

    //    @Pointcut("execution(public * com.minidwep.wasteSorting.service.RubbishService.rubbishByRubNameWithMaxWeight())")//切入点描述 这个是controller包的切入点
    @Pointcut("execution(public * com.minidwep.wasteSorting.service.RubbishService.rubbishByRubNameWithMaxWeight*(..))")
    public void redisRank() {
    }//签名，可以理解成这个切入点的一个名称

    @AfterReturning(returning = "returnOb", pointcut = "redisRank()")
    public void AfterReturningMethod(JoinPoint joinPoint, Object returnOb) {
        if (returnOb != null) {
            Rubbish rubbish = (Rubbish) returnOb;
            Long isHave = jedisUtil.zrank("rank", rubbish.getRubName());
            if (isHave == null) {
                jedisUtil.zadd("rank", 0, rubbish.getRubName());
            } else {
                Double zscore = jedisUtil.zscore("rank", rubbish.getRubName());
                jedisUtil.zincrby("rank", 1, rubbish.getRubName());
            }


        }
//        log.info("################AfterReturningMethod : AfterReturningMethod" +returnOb);
    }

}