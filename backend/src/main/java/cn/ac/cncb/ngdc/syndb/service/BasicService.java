package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.mapper.BasicMapper;
import cn.ac.cncb.ngdc.syndb.mapper.TraitNameMapper;
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


    public List selectTraitNameList(Object param) {
        return traitNameMapper.selectList((Map) param);
    }
}
