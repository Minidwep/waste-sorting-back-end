package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.pojo.RankBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Set;

@Component
public class RankServiceImpl implements RankService{
    @Autowired
    JedisCluster jedisCluster;

    @Override
    public ArrayList<RankBean> getRank() {
        Set<String> redisRank = jedisCluster.zrevrangeByScore("rank", 1000000, -1,0, 10);
        ArrayList<RankBean> rank = new ArrayList<>();
        for(String str :redisRank){
            Double zscore = jedisCluster.zscore("rank", str);
            RankBean rankBean = new RankBean();
            rankBean.setName(str);
            rankBean.setValue(zscore+"");
            rank.add(rankBean);
        }
        return rank;
    }

    @Override
    public ArrayList<RankBean> getRankAll() {
        Set<String> redisRank = jedisCluster.zrevrangeByScore("rank", 1000000, -1);
        ArrayList<RankBean> rank = new ArrayList<>();
        for(String str :redisRank){
            Double zscore = jedisCluster.zscore("rank", str);
            RankBean rankBean = new RankBean();
            rankBean.setName(str);
            rankBean.setValue(zscore+"");
            rank.add(rankBean);
        }
        return rank;
    }

}
