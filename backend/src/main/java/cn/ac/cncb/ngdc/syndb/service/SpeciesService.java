package cn.ac.cncb.ngdc.syndb.service;


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

    public SpeciesInfo findSpeciesByTaxon(int taxid){
        return speciesInfoMapper.findSpeciesByTaxon(taxid);
    }
}
