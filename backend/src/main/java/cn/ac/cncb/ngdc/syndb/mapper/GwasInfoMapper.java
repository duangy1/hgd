package cn.ac.cncb.ngdc.syndb.mapper;
import cn.ac.cncb.ngdc.syndb.entity.GwasInfo;
import com.github.pagehelper.Page;

import java.util.List;


public interface GwasInfoMapper {
    Page<GwasInfo> selectTraitBySpecies(String species);

    Page<GwasInfo> biomedicalTrait();

    int insert(GwasInfo record);

    int insertSelective(GwasInfo record);

    Page<GwasInfo> getInfoByTraitName(String traitName);

    Page<GwasInfo> getOrthoInfo(List speciesList, String orthoSpecies, String traitName);
}
