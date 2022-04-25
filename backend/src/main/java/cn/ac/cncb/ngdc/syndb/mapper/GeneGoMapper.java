package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.GeneGo;

import java.util.List;

public interface GeneGoMapper {
    GeneGo selectByPrimaryKey(Integer id);

    List<GeneGo> goInfoOfGene(String geneName, String tableName);
}