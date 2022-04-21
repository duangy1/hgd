package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.variant;
import com.github.pagehelper.Page;

import java.util.List;

public interface VariantMapper {
    int insert(variant record);

    int insertSelective(variant record);

    variant selectByPrimaryKey(Integer gsiId);

    int updateByPrimaryKeySelective(variant record);

    int updateByPrimaryKeyWithBLOBs(variant record);

    int updateByPrimaryKey(variant record);

    Page<variant> initPageVariant();

    List<variant> voInfoOfGene(String geneName);
}