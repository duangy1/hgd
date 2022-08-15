package cn.ac.cncb.ngdc.syndb.service;


import cn.ac.cncb.ngdc.syndb.entity.SpeciesHeatmap;
import cn.ac.cncb.ngdc.syndb.entity.SpeciesInfo;
import cn.ac.cncb.ngdc.syndb.mapper.SpeciesInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SpeciesService {
    @Resource
    private SpeciesInfoMapper speciesInfoMapper;

    public List<SpeciesInfo> selectAllAnimals(){
        return speciesInfoMapper.selectAllAnimals();
    }

//    public String getGwasorgIdByTaxon(String taxonid){return speciesInfoMapper.getGwasorgIdByTaxon(taxonid);}

    public List<SpeciesInfo> selectAllHomologOrganism(){
        return speciesInfoMapper.selectAllHomologOrganism(null);
    }

    public List<SpeciesInfo> selectOtherSpecies(Map param){
        return speciesInfoMapper.selectOtherSpecies(param);
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
    public List<SpeciesInfo> speciesNameListforVar(String varName,String speciesType){
        return speciesInfoMapper.speciesNameListforVar(varName,speciesType);
}
    public List<SpeciesInfo> speciesNameListforTrait(String traitID,String speciesType){
        return speciesInfoMapper.speciesNameListforTrait(traitID,speciesType);
    }

    public List<SpeciesInfo> speciesNameListforGo(String topGoId,String speciesType){
        return speciesInfoMapper.speciesNameListforGo(topGoId,speciesType);
    }

    public List<SpeciesInfo> speciesNameListforExp(String expName,String speciesType){
        return speciesInfoMapper.speciesNameListforExp(expName,speciesType);
    }


}
