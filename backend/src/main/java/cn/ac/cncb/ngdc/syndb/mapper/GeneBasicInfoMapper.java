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

    List<GeneBasicInfo> selectGene(String hdbId,String taxonid);

    List<GeneBasicInfo> selectGeneGoInfo(String geneName);



    GeneBasicInfo selectGeneByHdbGeneId(String hdbId);

    GeneBasicInfo selectEnsgIdByhdbId(String hdbId);

    String getHdbIdByEnsId(String ensId);

    List<String> getHdbIdByOtherId(String geneid);

    String geneDetailGeneOrthoInfo(String hdbId);

}