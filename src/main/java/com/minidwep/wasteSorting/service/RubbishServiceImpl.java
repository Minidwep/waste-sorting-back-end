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
    public Rubbish getRubbishByName(String rubName, int i) {
        Rubbish rubbish = rubbishMapper.getRubbishByName(rubName);
        if(rubbish != null && i ==1){
            System.out.println(i+rubName);
            rubbish.setWeight(rubbish.getWeight()+1);
            rubbishMapper.updateRubbish(rubbish);
        }
        return rubbish ;
    }

    @Override
    public Rubbish getRubbishById(Integer id) {
        return rubbishMapper.getRubbishById(id);
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


}
