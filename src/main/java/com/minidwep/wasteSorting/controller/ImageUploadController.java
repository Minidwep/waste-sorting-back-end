package com.minidwep.wasteSorting.controller;

import com.alibaba.fastjson.JSON;
import com.minidwep.wasteSorting.Util.Msg;
import com.minidwep.wasteSorting.utils.GetAipImageClassify;
import com.minidwep.wasteSorting.utils.UploadFile;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class ImageUploadController {

    @PostMapping("/fileUpload")
    @ResponseBody
    public Msg upload(@RequestParam("file") MultipartFile file){
        // 要上传的目标文件存放路径
        String localPath = "E:/Photos";
        System.out.println(file.getOriginalFilename());
        if (UploadFile.upload(file, localPath, file.getOriginalFilename())){
            GetAipImageClassify instance = GetAipImageClassify.getInit();
            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("baike_num", "5");
            // 参数为本地路径
            String image = "E:\\Photos\\1.jpg";
            JSONObject jsonObj = instance.client.advancedGeneral(image, options);
            String jsonAllStr =JSONObject.valueToString(jsonObj);
            String getJsonb = JSON.parseObject(jsonAllStr).getString("result");
            System.out.println(getJsonb);



            JSONObject result1 = jsonObj.getJSONObject("result");
            System.out.println(result1);
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }
}
