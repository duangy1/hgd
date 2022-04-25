package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.GeneBasicInfo;
import cn.ac.cncb.ngdc.syndb.entity.Ortho9031;
import cn.ac.cncb.ngdc.syndb.entity.SpeciesInfo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface GeneBasicInfoMapper {
    int insert(GeneBasicInfo record);

    int insertSelective(GeneBasicInfo record);

    GeneBasicInfo selectByPrimaryKey(Integer gbiId);

    int updateByPrimaryKeySelective(GeneBasicInfo record);

    int updateByPrimaryKeyWithBLOBs(GeneBasicInfo record);

    int updateByPrimaryKey(GeneBasicInfo record);

    Page<GeneBasicInfo> allOrthoList();

    Page<GeneBasicInfo> filterHomologGene(Map param);

    List<GeneBasicInfo> selectGene(String geneName);

    List<GeneBasicInfo> selectGeneGoInfo(String geneName);



    GeneBasicInfo selectGeneByHdbGeneId(String gene);

    String selectEnsgIdByhdbId(String hdbId);
}