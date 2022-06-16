package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.SpeciesHeatmap;
import cn.ac.cncb.ngdc.syndb.entity.SpeciesInfo;

import java.util.List;
import java.util.Map;

public interface SpeciesInfoMapper {
    public List<SpeciesInfo> selectAllAnimals();
    public List<SpeciesInfo> selectAllPlants();

    public List<SpeciesInfo> selectOtherSpecies(Map param);

    public SpeciesInfo findSpeciesByTaxon (String taxonId);

    List<SpeciesInfo> speciesInfoList(String classification);

    List<String> speciesNameList();

    List<SpeciesHeatmap> speciesHeatmap();

    List<SpeciesInfo> selectAllSpecies();

//    List<String> speciesNameList(String classification);
    List<SpeciesInfo> speciesNameListforTrait(Integer speciesType);

    String getDataSource(Integer txonid);

    String getDataSourceByName(String speciesName);

    String getClassificatoinByTax(String taxid);
}
