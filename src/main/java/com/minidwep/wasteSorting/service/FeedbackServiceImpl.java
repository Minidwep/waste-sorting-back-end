package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.Feedback;
import com.minidwep.wasteSorting.bean.Rubbish;
import com.minidwep.wasteSorting.mapper.FeedbackMapper;
import com.minidwep.wasteSorting.utils.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class FeedbackServiceImpl implements  FeedbackService {
    @Resource
    FeedbackMapper feedbackMapper;

    @Autowired
    RubbishService rubbishService;

    @Autowired
    JedisUtil jedisUtil;

    @Override
    public boolean addFeedback(Feedback feedback) {
        feedbackMapper.addFeedback(feedback);
        return false;
    }
    @Override
    public Feedback getFeedbackByRubNameAndType(String rubName,String rubType) {
        return feedbackMapper.getFeedbackByRubNameAndType(rubName,rubType);
//        return feedbackMapper.getFeedbackByRubName(rubName);
    }

    @Override
    public void updateFeedback(Feedback feedback) {
        if(feedback.getWeight() >= 5){
            if(feedback.getStatus() ==0){
                feedback.setStatus(1);
            }
//            当前垃圾表内有没有该反馈垃圾和类型信息
            Rubbish rubbishByRubNameAndType = rubbishService.getRubbishByRubNameAndType(feedback.getRubName(),feedback.getType());
            if(rubbishByRubNameAndType == null){
//                如果没有,添加到垃圾表中
                Rubbish rubbish = new Rubbish();
                rubbish.setRubName(feedback.getRubName());
                rubbish.setType(feedback.getType());
                rubbishService.addRubbish(rubbish);
            } else {
//                如果有,使得垃圾表的weight+1
                rubbishByRubNameAndType.setWeight(rubbishByRubNameAndType.getWeight()+1);
                rubbishService.updateRubbish(rubbishByRubNameAndType);

            }
        }
        feedbackMapper.updateFeedback(feedback);
    }
}
