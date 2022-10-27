package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.GeneExpression;
import com.github.pagehelper.Page;

import java.util.List;

public interface GeneExpressionMapper {
    GeneExpression selectByPrimaryKey(Integer geiId);

    List<GeneExpression> expressionInfoByGeneList(String geneName);

    Page<GeneExpression> initPageEo(String expName,String classification, String taxonid,List hdbidList,String hdbId,String taxonId);

    List<GeneExpression> expressionGene(String hdbid);
}