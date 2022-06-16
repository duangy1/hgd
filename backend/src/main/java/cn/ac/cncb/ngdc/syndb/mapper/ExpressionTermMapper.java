package cn.ac.cncb.ngdc.syndb.mapper;

import cn.ac.cncb.ngdc.syndb.entity.ExpressionTerm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpressionTermMapper {
    ExpressionTerm selectByPrimaryKey(Integer hdbEoId);

    List<ExpressionTerm> expressionInfoList(@Param(value="classification") String classification);


}