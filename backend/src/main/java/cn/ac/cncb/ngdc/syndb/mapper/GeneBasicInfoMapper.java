package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.GeneBasicInfo;
import com.github.pagehelper.Page;

public interface GeneBasicInfoMapper {
    int insert(GeneBasicInfo record);

    int insertSelective(GeneBasicInfo record);

    GeneBasicInfo selectByPrimaryKey(Integer gbiId);

    int updateByPrimaryKeySelective(GeneBasicInfo record);

    int updateByPrimaryKeyWithBLOBs(GeneBasicInfo record);

    int updateByPrimaryKey(GeneBasicInfo record);

    Page<GeneBasicInfo> allOrthoList();
}