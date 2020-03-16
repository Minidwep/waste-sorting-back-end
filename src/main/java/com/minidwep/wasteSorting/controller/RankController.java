package com.minidwep.wasteSorting.controller;

import com.minidwep.wasteSorting.pojo.RankBean;
import com.minidwep.wasteSorting.service.RankService;
import com.minidwep.wasteSorting.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class RankController {
    @Autowired
    RankService rankService;

    @GetMapping("/rank")
    @ResponseBody
    public Msg getRank(){
        ArrayList<RankBean> rank= rankService.getRank();
        return Msg.success().add("rankList",rank);
    }

    @GetMapping("/admin/rankAll")
    @ResponseBody
    public Msg getRank1(){
        ArrayList<RankBean> rank= rankService.getRankAll();
        return Msg.success().add("rankList",rank);
    }
}
