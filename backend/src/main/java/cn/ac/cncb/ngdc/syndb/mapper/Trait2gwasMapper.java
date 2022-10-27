package cn.ac.cncb.ngdc.syndb.mapper;

//import cn.ac.cncb.ngdc.syndb.entity.gwasInfo;
import cn.ac.cncb.ngdc.syndb.entity.HdbGwas;
import cn.ac.cncb.ngdc.syndb.entity.Trait2gwas;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface Trait2gwasMapper {
    int insert(Trait2gwas record);

    int insertSelective(Trait2gwas record);

    Page<Trait2gwas> biomedicalTrait(String traitId,String classification,String speciesName,List hdbidList,String hdbId,String taxonId);

    Trait2gwas selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Trait2gwas record);

    int updateByPrimaryKeyWithBLOBs(Trait2gwas record);

    int updateByPrimaryKey(Trait2gwas record);

    Page<Trait2gwas> selectTraitBySpecies(String taxonid);

    Page<Trait2gwas> getInfoByTraitName(String traitName);

    Page<Trait2gwas> getInfoByTraitId(String traitId);

    Page<Trait2gwas> getOrthoInfo(String querySpecies,String traitId);

    Integer findGwasOrgidByTaxonId(int taxonId);

    Integer selectTraitCountByGeneAndTaxon(Map param);

    List traitInfoByGeneList(String hdbId);

    HdbGwas getGwasInfoByhdbid(String hdbId);

    HdbGwas getGwasorgIdByTaxon(String taxonId,String hdbId);

    HdbGwas getGwasVarIdByTaxon(String taxonId,String hdbId);
}