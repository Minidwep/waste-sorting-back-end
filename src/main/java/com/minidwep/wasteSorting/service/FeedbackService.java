package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.Feedback;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackService {

    boolean addFeedback(Feedback feedback);

    Feedback getFeedbackByRubNameAndType(String rubName,String rubType);

    void updateFeedback(Feedback feedback);
}
