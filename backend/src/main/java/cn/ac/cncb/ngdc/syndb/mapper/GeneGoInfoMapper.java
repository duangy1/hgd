package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.GeneGoInfo;
import cn.ac.cncb.ngdc.syndb.entity.GoBasicTerm;

import java.util.List;

public interface GeneGoInfoMapper {
    int insert(GeneGoInfo record);

    int insertSelective(GeneGoInfo record);

    GeneGoInfo selectByPrimaryKey(Integer goId);

    int updateByPrimaryKeySelective(GeneGoInfo record);

    int updateByPrimaryKeyWithBLOBs(GeneGoInfo record);

    int updateByPrimaryKey(GeneGoInfo record);

    List<GeneGoInfo> getGoInfo(String ensid);
    List<GoBasicTerm> selectBasicGo();

    List<String> getGoNumbyGene(String ensid);
}