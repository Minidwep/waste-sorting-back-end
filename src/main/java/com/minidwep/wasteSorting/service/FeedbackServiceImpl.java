package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.Feedback;
import com.minidwep.wasteSorting.mapper.FeedbackMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FeedbackServiceImpl implements  FeedbackService {
    @Resource
    FeedbackMapper feedbackMapper;
    @Override
    public boolean addFeedback(Feedback feedback) {
        feedbackMapper.addFeedback(feedback);
        return false;
    }

    @Override
    public Feedback getFeedbackByRubName(String rubName) {
        return feedbackMapper.getFeedbackByRubName(rubName);
    }

    @Override
    public void updateFeedback(Feedback feedback) {
        feedbackMapper.updateFeedback(feedback);
    }
}
