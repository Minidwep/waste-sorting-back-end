package com.minidwep.wasteSorting.utils;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class test {
    public static void main(String[] args) {
        String instance1 = GetAipImageClassify.getInit().getUuid();
        String instance2 = GetAipImageClassify.getInit().getUuid();
        AipImageClassify client = GetAipImageClassify.getClient();
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("baike_num", "5");
        // 参数为本地路径
        String image = "E:\\Photos\\1.jpg";
        JSONObject res = client.advancedGeneral(image, options);
//        System.out.println(res.toString(2));

        String jsonAllStr =JSONObject.valueToString(res);
        Result result = JSON.parseObject(jsonAllStr, Result.class);
//        Result strjsonb = JSON.parseArray(jsonAllStr, Result.class);
        System.out.println(result.toString());
        List<ResultItem> items = result.getResult();
        for(ResultItem item : items){
            System.out.println(item.toString());
        }
//        String jsonAllStr =JSONObject.valueToString(res);
//        String getJsonb = JSON.parseObject(jsonAllStr).getString("result");
//        System.out.println(getJsonb);
//
//        List<ResultItem> strjsonb = JSON.parseArray(getJsonb, ResultItem.class);
//        for(ResultItem item : strjsonb){
//            System.out.println(item.toString());
//        }
//        System.out.println(strjsonb.get(1).toString());
    }
}
