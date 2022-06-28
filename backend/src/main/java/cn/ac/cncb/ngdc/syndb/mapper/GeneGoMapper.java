package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.GeneGo;
import cn.ac.cncb.ngdc.syndb.entity.GoBasicTerm;
import com.github.pagehelper.Page;

import java.util.List;

public interface GeneGoMapper {
    GeneGo selectByPrimaryKey(Integer id);

    List<GeneGo> goInfoOfGene(String hdbId);

    Page<GeneGo> initPageGo(String topGoid,String classification,String hdbId, String taxonid);

    List<GoBasicTerm> getGoByHdbTaxon(String hdbid);
}