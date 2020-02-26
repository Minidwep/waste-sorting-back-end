package com.minidwep.wasteSorting.controller;

import com.alibaba.fastjson.JSON;
import com.minidwep.wasteSorting.Util.Msg;
import com.minidwep.wasteSorting.bean.Rubbish;
import com.minidwep.wasteSorting.service.RubbishService;
import com.minidwep.wasteSorting.utils.GetAipImageClassify;
import com.minidwep.wasteSorting.utils.Result;
import com.minidwep.wasteSorting.utils.ResultItem;
import com.minidwep.wasteSorting.utils.UploadFile;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ImageUploadController {
    @Autowired
    RubbishService rubbishService;
    GetAipImageClassify instance = GetAipImageClassify.getInit();
    @PostMapping("/fileUpload")
    @ResponseBody
    public Msg upload(@RequestParam("file") MultipartFile file){
        // 要上传的目标文件存放路径
        String localPath = "E:/Photos";
        System.out.println(file.getOriginalFilename());
        if (UploadFile.upload(file, localPath, file.getOriginalFilename())){

            String image = localPath+"/"+file.getOriginalFilename();
            JSONObject res = instance.client.advancedGeneral(image,null);
            String jsonString =JSONObject.valueToString(res);
            Result result = JSON.parseObject(jsonString, Result.class);
            List<ResultItem> resultItems = result.getResult();
            List<ResultItem> resultList = resultItems.stream().sorted(Comparator.comparing(ResultItem::getScore).reversed())
                    .collect(Collectors.toList());
            List<Rubbish> rubbishes = new ArrayList<>();
            for(ResultItem item: resultList){
                Rubbish rubbish;
                rubbish = rubbishService.getRubbishByName(item.getKeyword());
                if(rubbish == null){
                    rubbish = rubbishService.getRubbishByName(item.getRoot());
                }
                if(rubbish != null){
                    rubbish.setScore(item.getScore());
                    rubbishes.add(rubbish);
                }



            }
            return Msg.success().add("rubbishes",rubbishes).add("resultList",resultList);
        }else {
            return Msg.fail();
        }
    }
}
