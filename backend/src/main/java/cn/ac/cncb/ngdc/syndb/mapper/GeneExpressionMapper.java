package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.GeneExpression;

import java.util.List;

public interface GeneExpressionMapper {
    GeneExpression selectByPrimaryKey(Integer geiId);

    List<GeneExpression> expressionInfoByGeneList(String geneName);
}