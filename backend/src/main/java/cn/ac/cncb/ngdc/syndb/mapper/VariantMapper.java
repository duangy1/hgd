package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.Variant;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VariantMapper {
    int insert(Variant record);

    int insertSelective(Variant record);

    Variant selectByPrimaryKey(Integer gsiId);

    int updateByPrimaryKeySelective(Variant record);

    int updateByPrimaryKeyWithBLOBs(Variant record);

    int updateByPrimaryKey(Variant record);

    Page<Variant> initPageVariant(String varname,String classification,String speciesName);

    List<Variant> voInfoOfGene(String geneName);

    List varTypeByGene(String geneName);

    Page<Variant> getInfoByVarNameAndSpecies(String varName,String  speciesName);

}