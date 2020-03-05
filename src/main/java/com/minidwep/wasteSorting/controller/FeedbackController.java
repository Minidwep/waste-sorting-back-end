package com.minidwep.wasteSorting.controller;

import com.minidwep.wasteSorting.utils.Msg;
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

    @GetMapping("/feedback")
    @ResponseBody
    public Msg feedback(@RequestParam("resultChecked") String resultChecked,
                           @RequestParam("rubbishChecked") String rubbishChecked){
//        1 根据resultChecked,rubbishChecked(反馈的垃圾名字和类型)进行检索
        Feedback feedback = feedbackService.getFeedbackByRubNameAndType(resultChecked,rubbishChecked);
//        2 如果feedback(检索结果)不为null
//              1 进行feedback.weight+1,并且检查feedback.weight是否超过5
//              2 如果weight超过5 则status设置为1 切将该feedback插入到rubbish表中
//              3 如果该feedback在rubbish表中存在,那么rubbish.weight+1
//        3 如果feedback(检索结果)为null 将其插入到 feedback表中
        if(feedback != null){
            feedback.setWeight(feedback.getWeight()+1);
            feedbackService.updateFeedback(feedback);
            if(feedback.getWeight() >= 5){
                if(feedback.getStatus() ==0){
                    feedback.setStatus(1);
                    feedbackService.updateFeedback(feedback);
                }
                Rubbish rubbishByRubNameAndType = rubbishService.getRubbishByRubNameAndType(feedback.getRubName(),feedback.getType());
                if(rubbishByRubNameAndType == null){
                    Rubbish rubbish = new Rubbish();
                    rubbish.setRubName(feedback.getRubName());
                    rubbish.setType(feedback.getType());
                    rubbishService.addRubbish(rubbish);
                } else {
                    rubbishByRubNameAndType.setWeight(rubbishByRubNameAndType.getWeight()+1);
                    rubbishService.updateRubbish(rubbishByRubNameAndType);
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

        return Msg.success();
    }

}
