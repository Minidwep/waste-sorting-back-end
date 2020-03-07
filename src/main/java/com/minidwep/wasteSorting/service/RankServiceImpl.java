package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.RankBean;
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
        Set<String> redisRank = jedisCluster.zrevrangeByScore("rank", 1000000, -1);
        RankBean rankBean = new RankBean();
        ArrayList<RankBean> rank = new ArrayList<>();
        for(String str :redisRank){
            Double zscore = jedisCluster.zscore("rank", str);
            rankBean.setName(str);
            rankBean.setValue(zscore+"");
            rank.add(rankBean);
            if(rank.size() >=10){
                break;
            }
        }
        return rank;
    }
}
