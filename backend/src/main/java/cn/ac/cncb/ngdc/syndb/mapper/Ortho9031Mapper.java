package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.Ortho9031;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;


public interface Ortho9031Mapper {
    int insert(Ortho9031 record);

    int insertSelective(Ortho9031 record);

    Ortho9031 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ortho9031 record);

    int updateByPrimaryKey(Ortho9031 record);


    List<Ortho9031> findOrthByTaxonAndGene(int taxonId, String geneId);

    List<Ortho9031> findOrthByTaxon2(int taxonId, String geneId,int taxonId2);

    List findTraitNameByGene(String geneid);

    List<Ortho9031> findOrthGeneByGivenGeneAndTaxon(Map param);

    List<Ortho9031> selectGeneOrthoInfo(String geneName);
}