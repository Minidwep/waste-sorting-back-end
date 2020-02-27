package com.minidwep.wasteSorting.controller;

import com.minidwep.wasteSorting.Util.Msg;
import com.minidwep.wasteSorting.bean.Feedback;
import com.minidwep.wasteSorting.bean.Rubbish;
import com.minidwep.wasteSorting.service.FeedbackService;
import com.minidwep.wasteSorting.service.RubbishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @Autowired
    RubbishService rubbishService;

    @GetMapping("/imgFeedback")
    @ResponseBody
    public Msg imgFeedback(@RequestParam("resultChecked") String resultChecked,
                           @RequestParam("rubbishChecked") String rubbishChecked){
//        1 根据resultChecked(反馈的垃圾名字进行检索)
        Feedback feedback = feedbackService.getFeedbackByRubName(resultChecked);
//        2 如果feedback(检索结果)不为null
//              1 进行weight+1,并且检查weight是否超过5
//              2 如果weight超过5 则status设置为1 切将该feed插入到rubbish表中
//        3 如果feedback(检索结果)为null 将其插入到 feedback表中
        if(feedback != null){
            feedback.setWeight(feedback.getWeight()+1);
            feedbackService.updateFeedback(feedback);
            if(feedback.getWeight() >= 5){
                if(feedback.getStatus() ==0){
                    feedback.setStatus(1);
                    feedbackService.updateFeedback(feedback);
                }
                Rubbish rubbishByName = rubbishService.getRubbishByName(feedback.getRubName(), 0);
                if(rubbishByName == null){
                    Rubbish rubbish = new Rubbish();
                    rubbish.setRubName(feedback.getRubName());
                    rubbish.setType(feedback.getType());
                    rubbishService.addRubbish(rubbish);
                }
            }
            System.out.println(feedback.toString());
        } else {
            Feedback feedbackAdd = new Feedback();
            System.out.println(resultChecked+" "+rubbishChecked);
            feedbackAdd.setRubName(resultChecked);
            feedbackAdd.setType(Integer.parseInt(rubbishChecked));
            feedbackService.addFeedback(feedbackAdd);
        }

//        Feedback feedback = new Feedback();
//        feedbackService.addFeedback(feedback);
        return Msg.success();
    }

}
