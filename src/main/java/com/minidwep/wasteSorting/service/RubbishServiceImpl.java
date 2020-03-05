package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.Rubbish;
import com.minidwep.wasteSorting.mapper.RubbishMapper;
import com.minidwep.wasteSorting.utils.GetAipImageClassify;
//import com.minidwep.wasteSorting.utils.JedisCache;
import com.minidwep.wasteSorting.utils.JedisCache;
import com.minidwep.wasteSorting.utils.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

@Component
@Slf4j
public class RubbishServiceImpl implements RubbishService{
    GetAipImageClassify instance = GetAipImageClassify.getInit();
    @Resource
    RubbishMapper rubbishMapper;
    @Autowired
    JedisCache jedisCache;
    @Override
    public Rubbish rubbishByRubNameWithMaxWeight(String rubName, int i) {
        String rubType = jedisCache.get(rubName);
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
                    jedisCache.set(rubbishByRubNameWithMaxWeight.getRubName(),
                            rubbishByRubNameWithMaxWeight.getType()+"");
                    log.info("查询内容："+rubName+"已被定义，成为了已定义态");
                    log.warn("rubbishQueryName"+rubName);
                    log.warn("rubbishQueryType"+rubbishByRubNameWithMaxWeight.getType());
                    return rubbishByRubNameWithMaxWeight;
                } else {
                    log.info("查询内容："+rubName+"将要被初始化");
                    jedisCache.set(rubName,"-1");
                    log.info("查询内容："+rubName+"已被初始化，成为了初始化态");
                    return null;
                }
            case -1:
                log.info("查询内容："+rubName+"是初始化态");
                return null;
            case 1:
                log.info("查询内容:"+rubName+"是定义态");
                log.warn("rubbishQueryItem"+rubName);
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


}
