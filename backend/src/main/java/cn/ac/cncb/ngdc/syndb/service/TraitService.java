package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.HdbGwas;
import cn.ac.cncb.ngdc.syndb.entity.TraitName;
import cn.ac.cncb.ngdc.syndb.mapper.GeneBasicInfoMapper;
import cn.ac.cncb.ngdc.syndb.mapper.Trait2gwasMapper;
//import cn.ac.cncb.ngdc.syndb.mapper.gwasInfoMapper;
//import cn.ac.cncb.ngdc.syndb.entity.gwasInfo;
import cn.ac.cncb.ngdc.syndb.entity.Trait2gwas;
import cn.ac.cncb.ngdc.syndb.mapper.TraitNameMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class TraitService {
    @Resource
    private Trait2gwasMapper trait2gwasMapper;

    @Resource
    private TraitNameMapper traitNameMapper;

    @Resource
    private GeneBasicInfoMapper geneBasicInfoMapper;

    public Page<Trait2gwas> biomedicalTrait(String traitId, String classification, int pageNo, int pageSize, String speciesName, String geneId,String hdbId,String taxonId) {

        List<String> hdbidList=new ArrayList();
        Page<Trait2gwas> varList = new Page<>();
        if(geneId!=null && geneId.length() >0){
            hdbidList=geneBasicInfoMapper.getHdbIdByOtherId(geneId);
            if(hdbidList.size()>0) {
                PageHelper.startPage(pageNo, pageSize,true); //line 1
                varList= trait2gwasMapper.biomedicalTrait(traitId,classification,speciesName,hdbidList,hdbId,taxonId);
            }
        }else {
            PageHelper.startPage(pageNo, pageSize, true); //line 1
            varList = trait2gwasMapper.biomedicalTrait(traitId, classification, speciesName, hdbidList, hdbId,taxonId);
        }
        return varList;
    }

//    public Page<Trait2gwas> selectTraitBySpecies(int pageNo, int pageSize, String taxonid) {
//        PageHelper.startPage(pageNo, pageSize,true); //line 1
//        return trait2gwasMapper.selectTraitBySpecies(taxonid);
//    }

//    public Page<Trait2gwas> getInfoByTraitName(Integer pageNo, int pageSize, String traitName) {
//        PageHelper.startPage(pageNo, pageSize,true); //line 1
//        return trait2gwasMapper.getInfoByTraitName(traitName);
//    }

//    public Page<Trait2gwas> getInfoByTraitId(Integer pageNo, int pageSize, String traitId) {
//        PageHelper.startPage(pageNo, pageSize,true); //line 1
//        return trait2gwasMapper.getInfoByTraitId(traitId);
//    }
//需要获取返回列表数据中基因的同源基因
//循环去chicken_ortho表逐个查询
//    public Page<Trait2gwas> getOrthoInfo(Integer pageNo, int pageSize,String querySpecies,String traitId) {
//        PageHelper.startPage(pageNo, pageSize,true); //line 1
//        Page<Trait2gwas> OrthoResult= trait2gwasMapper.getOrthoInfo(querySpecies,traitId);
////        System.out.print(OrthoResult);
//        return OrthoResult;
//    }

//    public Integer findGwasOrgidByTaxonId(int taxonId){
//        return  trait2gwasMapper.findGwasOrgidByTaxonId(taxonId);
//    }

    public TraitName traitsItem(String traitID){
        return traitNameMapper.traitsItem(traitID);
    };

    public HdbGwas getGwasInfoByhdbid(String hdbId){
        return trait2gwasMapper.getGwasInfoByhdbid(hdbId);
    };

    public HdbGwas getGwasorgIdByTaxon(String taxonId,String hdbId){
        return trait2gwasMapper.getGwasorgIdByTaxon(taxonId,hdbId);
    };

    public HdbGwas getGwasVarIdByTaxon(String taxonId,String hdbId){
        return trait2gwasMapper.getGwasVarIdByTaxon(taxonId,hdbId);
    };
}
