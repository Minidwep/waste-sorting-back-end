package com.minidwep.wasteSorting.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
    /**
     * 匹配字符串有没有除中文的其他字符
     *
     * @param str
     * @return 有：false
     * 无：true
     */
    public static boolean CheckStr(String str) {
        //定义正则表达式
        String reg = "[^\\u4e00-\\u9fa5]+";

        Pattern patten = Pattern.compile(reg);//编译正则表达式
        Matcher matcher = patten.matcher(str);// 指定要匹配的字符串

        return matcher.find() ? false : true;


    }


}
