package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.Variant;
import cn.ac.cncb.ngdc.syndb.mapper.GeneBasicInfoMapper;
import cn.ac.cncb.ngdc.syndb.mapper.SpeciesInfoMapper;
import cn.ac.cncb.ngdc.syndb.mapper.VariantMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class VariantService {
    @Resource
    private VariantMapper variantMapper;
    @Resource
    private GeneBasicInfoMapper geneBasicInfoMapper;

    public Page<Variant> initPageVariant(String varname,String classification, int pageNo, int pageSize,String speciesName,String geneid,String hdbId,String taxonId) {
        List<String> hdbidList=new ArrayList();
        Page<Variant> varList = new Page<>();
        if(geneid!=null && geneid.length() >0){
//            如果传入了geneid参数，则需要hdbidlist有值
            hdbidList=geneBasicInfoMapper.getHdbIdByOtherId(geneid);
            if(hdbidList.size()>0) {
                PageHelper.startPage(pageNo, pageSize, true); //line 1
                varList = variantMapper.initPageVariant(varname, classification, speciesName, hdbidList,hdbId,taxonId);
            }
        }else{
//            如果没传入geneid，直接查询
            PageHelper.startPage(pageNo, pageSize, true); //line 1
            varList = variantMapper.initPageVariant(varname, classification, speciesName, hdbidList,hdbId,taxonId);
        }
        return varList;
    }
    public Page<Variant> getInfoByVarNameAndSpecies(Integer pageNo, int pageSize, String varName,String speciesName) {
        PageHelper.startPage(pageNo, pageSize,true); //line 1
        return variantMapper.getInfoByVarNameAndSpecies(varName,speciesName);
    }

    public String getSnpidByhdbid(String hdbid, List varList){return variantMapper.getSnpidByhdbid(hdbid,varList);};

}
