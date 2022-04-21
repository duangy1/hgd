package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.OrthologGeneInfo;


import java.util.List;
import java.util.Map;

public interface OrthologGeneInfoMapper {
    OrthologGeneInfo selectByPrimaryKey(Integer oid);

    List<OrthologGeneInfo> findOrthGeneByGivenGeneAndTaxon(Map param);
}