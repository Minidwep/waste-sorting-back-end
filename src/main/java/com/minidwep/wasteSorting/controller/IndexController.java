package com.minidwep.wasteSorting.controller;

import com.minidwep.wasteSorting.utils.CheckUtil;
import com.minidwep.wasteSorting.utils.Msg;
import com.minidwep.wasteSorting.bean.Rubbish;
import com.minidwep.wasteSorting.service.RubbishService;
import com.minidwep.wasteSorting.pojo.ResultItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@Slf4j
public class IndexController {
    @Autowired
    RubbishService rubbishService;


    /**
     *
     * @param
     * file 上传的文件
     * @return
     * rubbishList 垃圾识别结果
     * resultList 图像识别结果
     *
     */
    @PostMapping("/fileUpload")
    @ResponseBody
    public Msg upload(@RequestParam("file") MultipartFile file){

        String jsonString = rubbishService.getStringResultByAipImageClassify(file);
        if(jsonString == null){
            return Msg.fail();
        }
//        取出识别图像的结果
        List<ResultItem> resultList = rubbishService.getResultListByString(jsonString);

//        取出识别垃圾的结果
        List<Rubbish> rubbishList = rubbishService.getRubbishListByString(jsonString);

        return Msg.success().add("rubbishList",rubbishList).add("resultList",resultList);
    }

    /**
     *
     * @param keyword
     * @return rubbish 垃圾信息
     */
    @GetMapping("/searchKeyword")
    @ResponseBody
    public Msg searchKeyword(@RequestParam("keyword") String keyword){

        boolean flag = CheckUtil.CheckStr(keyword);
        if(flag){
            Rubbish rubbish = rubbishService.rubbishByRubNameWithMaxWeight(keyword);
            return Msg.success().add("rubbish",rubbish);
        }else {
            return Msg.fail();
        }



    }

}
