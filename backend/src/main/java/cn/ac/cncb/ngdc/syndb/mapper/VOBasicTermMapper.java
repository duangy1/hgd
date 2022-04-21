package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.VOBasicTerm;

import java.util.List;

public interface VOBasicTermMapper {
    int insert(VOBasicTerm record);

    int insertSelective(VOBasicTerm record);

    VOBasicTerm selectByPrimaryKey(Integer hdbVoId);

    int updateByPrimaryKeySelective(VOBasicTerm record);

    int updateByPrimaryKey(VOBasicTerm record);

    List voInfoList();


}