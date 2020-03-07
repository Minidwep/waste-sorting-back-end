package com.minidwep.wasteSorting.service;

import com.alibaba.fastjson.JSON;
import com.minidwep.wasteSorting.bean.Rubbish;
import com.minidwep.wasteSorting.mapper.RubbishMapper;
import com.minidwep.wasteSorting.utils.*;
//import com.minidwep.wasteSorting.utils.JedisCache;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RubbishServiceImpl implements RubbishService{
    GetAipImageClassify instance = GetAipImageClassify.getInit();
    @Resource
    RubbishMapper rubbishMapper;
    @Autowired
    JedisCache jedisCache;
    @Override
    public Rubbish rubbishByRubNameWithMaxWeight(String rubName) {
        String rubType = jedisCache.get("rubbish"+rubName);
        int flag = 99;
        if(rubType == null){
//            不存在状态
            flag = -99;
        } else if("-1".equals(rubType)){
//            初始化状态
            flag = -1;
        } else {
//            已定义状态
            flag = 1;
        }
        switch (flag){
            case -99:
                log.info("查询内容："+rubName+"是无状态");
                Rubbish rubbishByRubNameWithMaxWeight =
                        rubbishMapper.getRubbishByRubNameWithMaxWeight(rubName);
                if(rubbishByRubNameWithMaxWeight !=null){
                    log.info("查询内容："+rubName+"将要被定义");
                    jedisCache.set("rubbish"+rubbishByRubNameWithMaxWeight.getRubName(),
                            rubbishByRubNameWithMaxWeight.getType()+"");
                    log.info("查询内容："+rubName+"已被定义，成为了已定义态");
                    log.info("rubbishQueryName"+rubName);
                    log.info("rubbishQueryType"+rubbishByRubNameWithMaxWeight.getType());
                    return rubbishByRubNameWithMaxWeight;
                } else {
                    log.info("查询内容："+rubName+"将要被初始化");
                    jedisCache.set("rubbish"+rubName,"-1");
                    log.info("查询内容："+rubName+"已被初始化，成为了初始化态");
                    return null;
                }
            case -1:
                log.info("查询内容："+rubName+"是初始化态");
                return null;
            case 1:
                log.info("查询内容:"+rubName+"是定义态"+"-value："+rubType);
                log.info("rubbishQueryItem"+rubName);
                Rubbish rubbishByRedis = new Rubbish();
                rubbishByRedis.setRubName(rubName);
                rubbishByRedis.setType(Integer.parseInt(rubType));
                return rubbishByRedis;
        }
        return null;

    }

    @Override
    public Rubbish getRubbishById(Integer id) {
        return rubbishMapper.getRubbishById(id);
    }

    @Override
    public void updateRubbish(Rubbish rubbish) {
        rubbishMapper.updateRubbish(rubbish);
    }

    @Override
    public String getStringResultByAipImageClassify(MultipartFile file, String localPath, String originalFilename) {
        if (UploadFile.upload(file, localPath, file.getOriginalFilename())){
            String image = localPath+"/"+file.getOriginalFilename();
            JSONObject res = instance.client.advancedGeneral(image,null);
            String jsonString =JSONObject.valueToString(res);
            return jsonString;
        } else {
            return null;
        }

    }

    @Override
    public void addRubbish(Rubbish rubbish) {
        rubbishMapper.addRubbish(rubbish);
    }

    @Override
    public Rubbish getRubbishByRubNameAndType(String rubName, Integer rubType) {
        return rubbishMapper.getRubbishByRubNameAndType(rubName,rubType);
    }

    @Override
    public List<Rubbish> getRubbishListByString(String jsonString) {
//        JSON转化为 Result对象
        Result result = JSON.parseObject(jsonString, Result.class);
//        通过 score 排序取出对象的值
        List<ResultItem> resultList = result.getResult().stream().sorted(Comparator.comparing(ResultItem::getScore).reversed())
                .collect(Collectors.toList());
//        定义key查询list
        List<Rubbish> rubbishListByKey = new ArrayList<>();
//        定义root查询list
        List<Rubbish> rubbishListByRoot = new ArrayList<>();
//        优先使用key去查询垃圾类型,其次再用root
        for(ResultItem item: resultList){
            Rubbish rubbish;
            rubbish = this.rubbishByRubNameWithMaxWeight(item.getKeyword());
            if(rubbish != null){
                rubbish.setScore(item.getScore());
                rubbishListByKey.add(rubbish);
            } else {
                rubbish = this.rubbishByRubNameWithMaxWeight(item.getRoot());
                if(rubbish != null){
                    rubbish.setScore(item.getScore());
                    rubbishListByRoot.add(rubbish);
                }
            }

        }
//        排序规则为 按照score排序,且key查询>root查询
        rubbishListByKey.addAll(rubbishListByRoot);
        return rubbishListByKey;
    }

    @Override
    public List<ResultItem> getResultListByString(String jsonString) {
        //        JSON转化为 Result对象
        Result result = JSON.parseObject(jsonString, Result.class);
//        通过 score 排序取出对象的值
        List<ResultItem> resultList = result.getResult().stream().sorted(Comparator.comparing(ResultItem::getScore).reversed())
                .collect(Collectors.toList());
        return resultList;
    }


}
