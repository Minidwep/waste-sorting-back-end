package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.Rubbish;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface RubbishService {
    Rubbish rubbishByRubNameWithMaxWeight(String rubName, int i);
    Rubbish getRubbishById(Integer id);
    void  updateRubbish(Rubbish rubbish);
    String getStringResultByAipImageClassify(MultipartFile file, String localPath, String originalFilename);

    void addRubbish(Rubbish rubbish);

    Rubbish getRubbishByRubNameAndType(String rubName, Integer rubType);
}
