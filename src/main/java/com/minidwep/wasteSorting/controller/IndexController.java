package com.minidwep.wasteSorting.controller;

import com.alibaba.fastjson.JSON;
import com.minidwep.wasteSorting.utils.Msg;
import com.minidwep.wasteSorting.bean.Rubbish;
import com.minidwep.wasteSorting.service.RubbishService;
import com.minidwep.wasteSorting.utils.Result;
import com.minidwep.wasteSorting.utils.ResultItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    @Autowired
    RubbishService rubbishService;

    @PostMapping("/fileUpload")
    @ResponseBody
    public Msg upload(@RequestParam("file") MultipartFile file){
        // 要上传的目标文件存放路径
        String localPath = "E:/Photos";
        String jsonString = rubbishService.getStringResultByAipImageClassify(file, localPath, file.getOriginalFilename());
        if(jsonString == null){
            return Msg.fail();
        }
        Result result = JSON.parseObject(jsonString, Result.class);
        List<ResultItem> resultList = result.getResult().stream().sorted(Comparator.comparing(ResultItem::getScore).reversed())
                    .collect(Collectors.toList());
        List<Rubbish> rubbishList = new ArrayList<>();
        for(ResultItem item: resultList){
            Rubbish rubbish;
            rubbish = rubbishService.rubbishByRubNameWithMaxWeight(item.getKeyword(),1);
            if(rubbish == null){
                rubbish = rubbishService.rubbishByRubNameWithMaxWeight(item.getRoot(),0);
            }
            if(rubbish != null){
                rubbish.setScore(item.getScore());
                rubbishList.add(rubbish);
            }
        }
        return Msg.success().add("rubbishList",rubbishList).add("resultList",resultList);
    }

    @GetMapping("/searchKeyword")
    @ResponseBody
    public Msg searchKeyword(@RequestParam("keyword") String keyword){
        Rubbish rubbish = rubbishService.rubbishByRubNameWithMaxWeight(keyword,1);

        return Msg.success().add("rubbish",rubbish);
    }

}
