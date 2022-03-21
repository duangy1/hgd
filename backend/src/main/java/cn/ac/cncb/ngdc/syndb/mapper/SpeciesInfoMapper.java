package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.SpeciesInfo;

import java.util.List;
import java.util.Map;

public interface SpeciesInfoMapper {
    public List<SpeciesInfo> selectAllAnimals();
    public List<SpeciesInfo> selectAllPlants();

    public List<SpeciesInfo> selectOtherSpecies(Map param);

    public SpeciesInfo findSpeciesByTaxon (int taxonId);
}
