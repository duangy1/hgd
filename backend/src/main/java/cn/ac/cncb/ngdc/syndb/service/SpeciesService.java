package cn.ac.cncb.ngdc.syndb.service;


import cn.ac.cncb.ngdc.syndb.entity.SpeciesHeatmap;
import cn.ac.cncb.ngdc.syndb.entity.SpeciesInfo;
import cn.ac.cncb.ngdc.syndb.mapper.SpeciesInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SpeciesService {
    @Resource
    private SpeciesInfoMapper speciesInfoMapper;

    public List<SpeciesInfo> selectAllAnimals(){
        return speciesInfoMapper.selectAllAnimals();
    }

    public List<SpeciesInfo> selectAllPlants(){
        return speciesInfoMapper.selectAllPlants();
    }

    public SpeciesInfo findSpeciesByTaxon(String taxid){
        return speciesInfoMapper.findSpeciesByTaxon(taxid);
    }

//    public List<SpeciesInfo> speciesNameList(String classification){
//        return speciesInfoMapper.speciesNameList(classification);
//    }
//返回所有的物种列表（有同源数据的）
    public List<SpeciesInfo> selectAllSpecies(){
        return speciesInfoMapper.selectAllSpecies();
    }
//传入动植物参数
//按照分类筛选
    public List<SpeciesInfo> selectSpeciesByClass(String classification){
    return speciesInfoMapper.speciesInfoList(classification);
}

    public List<String> speciesNameList(){
        return speciesInfoMapper.speciesNameList();
    }
//绘制物种间同源基因统计热图数据接口
    public List<SpeciesHeatmap> speciesHeatmap(){
        return speciesInfoMapper.speciesHeatmap();
    }

//只返回有trait数据的物种
    public List<SpeciesInfo> speciesNameListforVar(Integer speciesType){
        return speciesInfoMapper.speciesNameListforVar(speciesType);
}
    public List<SpeciesInfo> speciesNameListforTrait(Integer speciesType){
        return speciesInfoMapper.speciesNameListforTrait(speciesType);
    }

    public List<SpeciesInfo> speciesNameListforGo(Integer speciesType){
        return speciesInfoMapper.speciesNameListforGo(speciesType);
    }

    public List<SpeciesInfo> speciesNameListforExp(Integer speciesType){
        return speciesInfoMapper.speciesNameListforExp(speciesType);
    }
}
