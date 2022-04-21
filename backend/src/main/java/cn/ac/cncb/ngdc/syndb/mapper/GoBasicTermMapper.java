package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.GoBasicTerm;

public interface GoBasicTermMapper {
    int insert(GoBasicTerm record);

    int insertSelective(GoBasicTerm record);

    GoBasicTerm selectByPrimaryKey(Integer hdbGoId);

    int updateByPrimaryKeySelective(GoBasicTerm record);

    int updateByPrimaryKeyWithBLOBs(GoBasicTerm record);

    int updateByPrimaryKey(GoBasicTerm record);
}