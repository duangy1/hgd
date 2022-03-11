package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.mapper.Trait2gwasMapper;
//import cn.ac.cncb.ngdc.syndb.mapper.gwasInfoMapper;
//import cn.ac.cncb.ngdc.syndb.entity.gwasInfo;
import cn.ac.cncb.ngdc.syndb.entity.Trait2gwas;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class TraitService {
    @Resource
    private Trait2gwasMapper trait2gwasMapper;

    public Page<Trait2gwas> biomedicalTrait(int pageNo, int pageSize) {

        PageHelper.startPage(pageNo, pageSize,true); //line 1
        return trait2gwasMapper.biomedicalTrait();
    }

    public Page<Trait2gwas> selectTraitBySpecies(int pageNo, int pageSize, String Species) {
        PageHelper.startPage(pageNo, pageSize,true); //line 1
        return trait2gwasMapper.selectTraitBySpecies(Species);
    }

    public Page<Trait2gwas> getInfoByTraitName(Integer pageNo, int pageSize, String traitName) {
        PageHelper.startPage(pageNo, pageSize,true); //line 1
        return trait2gwasMapper.getInfoByTraitName(traitName);
    }
//需要获取返回列表数据中基因的同源基因
//循环去chicken_ortho表逐个查询
    public Page<Trait2gwas> getOrthoInfo(Integer pageNo, int pageSize,int querySpecies, int orthoSpecies, String traitName) {
        PageHelper.startPage(pageNo, pageSize,true); //line 1
        Page<Trait2gwas> OrthoResult= trait2gwasMapper.getOrthoInfo(querySpecies,orthoSpecies,traitName);
//        System.out.print(OrthoResult);
        return OrthoResult;
    }
    public Integer findGwasOrgidByTaxonId(int taxonId){
        return  trait2gwasMapper.findGwasOrgidByTaxonId(taxonId);
    }



}
