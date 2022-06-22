package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.ExpressionTerm;
import cn.ac.cncb.ngdc.syndb.entity.VOBasicTerm;
import cn.ac.cncb.ngdc.syndb.mapper.BasicMapper;
import cn.ac.cncb.ngdc.syndb.mapper.ExpressionTermMapper;
import cn.ac.cncb.ngdc.syndb.mapper.TraitNameMapper;
import cn.ac.cncb.ngdc.syndb.mapper.VOBasicTermMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BasicService  {

    @Resource
    BasicMapper basicMapper;

    @Resource
    TraitNameMapper traitNameMapper;

    @Resource
    VOBasicTermMapper voBasicTermMapper;


    @Resource
    ExpressionTermMapper expressionTermMapper;

    public List selectTraitNameList(Object param) {
        return traitNameMapper.selectList((Map) param);
    }

    public List selectVariationTermList(){
       return  voBasicTermMapper.voInfoList();
    }

    public List<ExpressionTerm> expressionInfoList(@Param(value="classification") String classification){
        return expressionTermMapper.expressionInfoList(classification);
    }
}
