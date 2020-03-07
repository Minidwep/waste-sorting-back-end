package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.RankBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public interface RankService {
    ArrayList<RankBean> getRank();
}
