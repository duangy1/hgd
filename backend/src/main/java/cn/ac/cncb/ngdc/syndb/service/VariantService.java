package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.Variant;
import cn.ac.cncb.ngdc.syndb.mapper.SpeciesInfoMapper;
import cn.ac.cncb.ngdc.syndb.mapper.VariantMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VariantService {
    @Resource
    private VariantMapper variantMapper;
    @Resource
    private SpeciesInfoMapper speciesInfoMapper;

    public Page<Variant> initPageVariant(String varname,String classification, int pageNo, int pageSize,String speciesName) {
        PageHelper.startPage(pageNo, pageSize,true); //line 1
        Page<Variant> varList=  variantMapper.initPageVariant(varname,classification,speciesName);
        return varList;
    }
    public Page<Variant> getInfoByVarNameAndSpecies(Integer pageNo, int pageSize, String varName,String speciesName) {
        PageHelper.startPage(pageNo, pageSize,true); //line 1
        return variantMapper.getInfoByVarNameAndSpecies(varName,speciesName);
    }

    public String getSnpidByhdbid(String hdbid,String varName){return variantMapper.getSnpidByhdbid(hdbid,varName);};

}
