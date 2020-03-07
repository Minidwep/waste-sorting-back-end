package com.minidwep.wasteSorting.controller;

import com.minidwep.wasteSorting.bean.RankBean;
import com.minidwep.wasteSorting.service.RankService;
import com.minidwep.wasteSorting.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Set;

@Controller
public class RankController {
    @Autowired
    RankService rankService;

    @GetMapping("/rank")
    @ResponseBody
    public Msg getRank(){
        ArrayList<RankBean> rank= rankService.getRank();
        return Msg.success().add("rank",rank);
    }
}
