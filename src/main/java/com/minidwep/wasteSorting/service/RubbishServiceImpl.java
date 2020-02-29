package com.minidwep.wasteSorting.service;

import com.minidwep.wasteSorting.bean.Rubbish;
import com.minidwep.wasteSorting.mapper.RubbishMapper;
import com.minidwep.wasteSorting.utils.GetAipImageClassify;
import com.minidwep.wasteSorting.utils.UploadFile;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Component
public class RubbishServiceImpl implements RubbishService{
    GetAipImageClassify instance = GetAipImageClassify.getInit();
    @Resource
    RubbishMapper rubbishMapper;
    @Override
    public Rubbish rubbishByRubNameWithMaxWeight(String rubName, int i) {

        Rubbish rubbishByRubNameWithMaxWeight = rubbishMapper.getRubbishByRubNameWithMaxWeight(rubName);
        return rubbishByRubNameWithMaxWeight ;
    }

    @Override
    public Rubbish getRubbishById(Integer id) {
        return rubbishMapper.getRubbishById(id);
    }

    @Override
    public void updateRubbish(Rubbish rubbish) {
        rubbishMapper.updateRubbish(rubbish);
    }

    @Override
    public String getStringResultByAipImageClassify(MultipartFile file, String localPath, String originalFilename) {
        if (UploadFile.upload(file, localPath, file.getOriginalFilename())){
            String image = localPath+"/"+file.getOriginalFilename();
            JSONObject res = instance.client.advancedGeneral(image,null);
            String jsonString =JSONObject.valueToString(res);
            return jsonString;
        } else {
            return null;
        }

    }

    @Override
    public void addRubbish(Rubbish rubbish) {
        rubbishMapper.addRubbish(rubbish);
    }

    @Override
    public Rubbish getRubbishByRubNameAndType(String rubName, Integer rubType) {
        return rubbishMapper.getRubbishByRubNameAndType(rubName,rubType);
    }


}
