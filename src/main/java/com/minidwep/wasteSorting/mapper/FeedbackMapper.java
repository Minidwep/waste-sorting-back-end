package com.minidwep.wasteSorting.mapper;

import com.minidwep.wasteSorting.bean.Feedback;
import com.minidwep.wasteSorting.bean.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FeedbackMapper {
    void addFeedback(@Param("feedback")Feedback feedback);
    Feedback getFeedbackByRubName(@Param("rubName") String rubName);
    void updateFeedback(@Param("feedback")Feedback feedback);
}
