package cn.ac.cncb.ngdc.syndb.mapper;

//import cn.ac.cncb.ngdc.syndb.entity.gwasInfo;
import cn.ac.cncb.ngdc.syndb.entity.Trait2gwas;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface Trait2gwasMapper {
    int insert(Trait2gwas record);

    int insertSelective(Trait2gwas record);

    Page<Trait2gwas> biomedicalTrait();

    Trait2gwas selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Trait2gwas record);

    int updateByPrimaryKeyWithBLOBs(Trait2gwas record);

    int updateByPrimaryKey(Trait2gwas record);

    Page<Trait2gwas> selectTraitBySpecies(String species);

    Page<Trait2gwas> getInfoByTraitName(String traitName);

    Page<Trait2gwas> getOrthoInfo(int querySpecies,int orthoSpecies, String traitName);

    Integer findGwasOrgidByTaxonId(int taxonId);

    Integer selectTraitCountByGeneAndTaxon(Map param);

    List<Trait2gwas> traitInfoByGeneList(String geneName);
}