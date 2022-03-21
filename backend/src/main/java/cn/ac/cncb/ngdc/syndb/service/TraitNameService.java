package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.TraitName;
import cn.ac.cncb.ngdc.syndb.mapper.TraitNameMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TraitNameService {

    @Resource
    TraitNameMapper traitNameMapper;

    public List<TraitName> selectList(Map map){
        return traitNameMapper.selectList(map);
    }
}
