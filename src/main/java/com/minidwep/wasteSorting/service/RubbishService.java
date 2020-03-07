package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.Rubbish;
import com.minidwep.wasteSorting.utils.ResultItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface RubbishService {
//    根据垃圾的名字查询垃圾(类型权重最大)
    Rubbish rubbishByRubNameWithMaxWeight(String rubName);

//   根据垃圾的名id查询垃圾
    Rubbish getRubbishById(Integer id);

//    更新垃圾信息
    void  updateRubbish(Rubbish rubbish);

//    获得图像识别信息
    String getStringResultByAipImageClassify(MultipartFile file, String localPath, String originalFilename);

//    添加一个垃圾
    void addRubbish(Rubbish rubbish);

//    获得通过垃圾名字和类型获得垃圾
    Rubbish getRubbishByRubNameAndType(String rubName, Integer rubType);

//    得到垃圾识别信息
    List<Rubbish> getRubbishListByString(String jsonString);

//    得到图像识别信息
    List<ResultItem> getResultListByString(String jsonString);
}
