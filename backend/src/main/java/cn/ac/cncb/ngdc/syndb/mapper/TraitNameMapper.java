package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.TraitName;

import java.util.List;
import java.util.Map;

public interface TraitNameMapper {
    List<TraitName> selectList(Map map);

    List<TraitName> traitInfoList();

    TraitName traitsItem(String classification);
}
