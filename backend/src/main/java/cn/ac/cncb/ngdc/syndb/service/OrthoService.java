package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.*;
import cn.ac.cncb.ngdc.syndb.mapper.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class OrthoService {

    @Resource
    private Ortho9031Mapper ortho9031Mapper;
    @Resource
    private GeneBasicInfoMapper geneBasicInfoMapper;
    @Resource
    private SpeciesInfoMapper speciesInfoMapper;
    @Resource
    private Trait2gwasMapper trait2gwasMapper;
    @Resource
    private VariantMapper variantMapper;

    public Page<GeneBasicInfo> allOrthoList(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize,true); //line 1
        return geneBasicInfoMapper.allOrthoList();
    }

//这里写的不对，应该用同源基因找对应的trait，这个是用本身的基因找的
    public  List<Ortho9031> findOrthByTaxonAndGene(int taxonId, String geneId){
        List<Ortho9031> ortholist = ortho9031Mapper.findOrthByTaxonAndGene(taxonId,geneId);
        for(Ortho9031 orthoItem : ortholist){
            String ensGene1=orthoItem.getEnsemblId1();
            String ensGene2=orthoItem.getEnsemblId2();
            System.out.print("ensGene1:"+ensGene1+"ensGene2:"+ensGene2);
            String ensGene=ensGene1==geneId?ensGene2:ensGene1;
            List traitName = ortho9031Mapper.findTraitNameByGene(ensGene);
            LinkedHashSet<String> traitNameSet = new LinkedHashSet<>(traitName);
            ArrayList<String> newTraitList = new ArrayList<>(traitNameSet);
            orthoItem.setOrthoTraitName(newTraitList);

        }
        System.out.print("ortholist:"+ortholist);
        return ortholist;
    }

    //这里写的不对，应该用同源基因找对应的trait，这个是用本身的基因找的
//    trait模块中filter限制同源物种后返回数据
    public  List<Ortho9031> findOrthByTaxon2(String geneId,List orthoSpeciestax){
        List<Ortho9031> ortholist = ortho9031Mapper.findOrthByTaxon2(geneId,orthoSpeciestax);
        for(Ortho9031 orthoItem : ortholist){
            List traitName = ortho9031Mapper.findTraitNameByGene(geneId);
            LinkedHashSet<String> traitNameSet = new LinkedHashSet<>(traitName);
            ArrayList<String> newTraitList = new ArrayList<>(traitNameSet);
            orthoItem.setOrthoTraitName(newTraitList);
//            把fromdb改成json
            String fromdb=orthoItem.getFromdb();
            String newFromdb=fromdb.replaceAll("'","");
            newFromdb=newFromdb.replaceAll("\\[","");
            newFromdb=newFromdb.replaceAll("]","");
            orthoItem.setFromdb(newFromdb);
        }
        return ortholist;
    }
//trait页面
//    从trait_all表返回的trait数据
//    循环trait数据，找到对应的同源list
//    循环同源list，找到同源基因对应的trait
    public List<Ortho9031> selectGeneOrthoInfo(String hdbid){
        List<Ortho9031> orthoList=ortho9031Mapper.selectGeneOrthoInfo(hdbid);
        for(Ortho9031 orthoItem : orthoList){
            String dbevidence=orthoItem.getDbEvidence();
            String fromdbinfo=orthoItem.getFromdb();

            if(fromdbinfo != null && fromdbinfo.length() > 0){
                if (fromdbinfo.contains("inparanoid")) {
                    orthoItem.setInparanoid("1");
                }
            }
            if(dbevidence != null && dbevidence.length() > 0) {
                List<String> dbevidenceList = Arrays.asList(dbevidence.replaceAll("'","").split(","));
                for(String item : dbevidenceList){
                    String[] ltemlis=item.split(":");
                    String dbName=ltemlis[0];
                    String dbInfo=ltemlis[1];
                    if(dbName.equals("panther")){
                        orthoItem.setPanther(dbInfo);
                    }else if(dbName.equals("eggnog")){
                        orthoItem.setEggnog(dbInfo);
                    }else if(dbName.equals("oma")){
                        orthoItem.setOma(dbInfo);
                    }else if(dbName.equals("hieranoid")){
                        orthoItem.setHieranoid(dbInfo);
                    }else if(dbName.equals("treefam")){
                        orthoItem.setTreefam(dbInfo);
                    }
                }
                orthoItem.setDbevidenceList(dbevidenceList);
            }
        }

        return orthoList;
    }

    //trait页面表格里取同源，不需要用到库信息，所以这个方法里不设置库信息
//    同源返回信息taxid\speciesinfo
    public ProductResultDTO selectGeneTraitOrthoInfo(String hdbid,String taxonid) {
        System.out.print("hdbid:"+hdbid);
//        if (hdbid != null && !hdbid.trim().equals("")) {
            List<Ortho9031> orthoList = ortho9031Mapper.selectGeneOrthoInfo(hdbid);
//            List<String> orthoNameList = new ArrayList<>();
            ProductResultDTO resultList = new ProductResultDTO();
            for (Ortho9031 orthoitem : orthoList) {
                String tax1 = orthoitem.getTax1();
//            在第一位，取第二位值，否则相反
                String hdbid1 = "";
                if (taxonid.equals(tax1)) {
                    String tax2 = orthoitem.getTax2();
                    //SpeciesInfo species2 = speciesInfoMapper.findSpeciesByTaxon(tax2);
                    SpeciesInfo species2 = new SpeciesInfo();
                    species2.setCommonName(orthoitem.getCommonName2());
                    species2.setDataSource(orthoitem.getDataSource2());
                    species2.setClassification(orthoitem.getClassification2());

                    orthoitem.setTax(tax2);
                    orthoitem.setSpecies(species2);
//                orthoitem.setCommonName(orthoitem.getCommonName2());
                    orthoitem.setTraitName(orthoitem.getTraitName2());

                    orthoitem.setEnsemblId(orthoitem.getEnsemblId2());
//                    orthoNameList.add(species2.getCommonName());
                    hdbid1 = orthoitem.getHdbId2();
                    orthoitem.setGwasId(orthoitem.getGwasId2());
                } else {
                    //  SpeciesInfo species1 = speciesInfoMapper.findSpeciesByTaxon(tax1);
                    SpeciesInfo species1 = new SpeciesInfo();
                    species1.setCommonName(orthoitem.getCommonName1());
                    species1.setDataSource(orthoitem.getDataSource1());
                    species1.setClassification(orthoitem.getClassification1());
                    orthoitem.setTax(tax1);
                    orthoitem.setSpecies(species1);

                    orthoitem.setEnsemblId(orthoitem.getEnsemblId1());
                    orthoitem.setCommonName(orthoitem.getCommonName1());
                    orthoitem.setTraitName(orthoitem.getTraitName1());
                    orthoitem.setGwasId(orthoitem.getGwasId1());
//                    orthoNameList.add(species1.getCommonName());
                    hdbid1 = orthoitem.getHdbId1();
                }
//            String enspid=orthoitem.getEnsemblId();
//            根据同源的pid找到对应的gid才行
//            用同源的ensemblid查找对应的trait
//            String ensgid=geneBasicInfoMapper.getEnsGidByEnspId(enspid);
//            String gwasid=geneBasicInfoMapper.getEnsGidByEnspId(enspid);
//            这里待修改，目前用同源的pid查找trait找不到
//            等gbi_copy表格补全gwas_id以及hdbid时，用hdbpid找到对应的gwasid，再进行查询
                //   List trait=trait2gwasMapper.traitInfoByGeneList(hdbid1);
//            List variantType=variantMapper.varTypeByGene(ensgid);
//            List trait = new ArrayList();
//            orthoitem.setOrthoTraitName(trait);
//            orthoitem.setOrthoVarName(variantType);
            }
            resultList.setOrthoList(orthoList);
//            resultList.setOrthoNameList(orthoNameList);
            return resultList;

    }

    //variant页面表格里取同源
    public List<Ortho9031> selectGeneVarOrthoInfo(String hdbid,String taxonid) {
        List<Ortho9031> orthoList = ortho9031Mapper.selectGeneVarOrthoInfo(hdbid);
        for (Ortho9031 orthoitem : orthoList) {

//            SpeciesInfo species1 = speciesInfoMapper.findSpeciesByTaxon(tax1);
            String tax2 = orthoitem.getTax2();
            String tax1 = orthoitem.getTax1();
//            SpeciesInfo species2 = speciesInfoMapper.findSpeciesByTaxon(tax2);
//            orthoitem.setSpecies2(species2);
//            在第一位，取第二位值，否则相反
            if (taxonid.equals(tax1)) {
                SpeciesInfo species2 = new SpeciesInfo();
                species2.setCommonName(orthoitem.getCommonName2());
                species2.setDataSource(orthoitem.getDataSource2());
                species2.setClassification(orthoitem.getClassification2());
                orthoitem.setTax(tax2);
                orthoitem.setVarName(orthoitem.getVarName2());
                orthoitem.setSpecies(species2);
                orthoitem.setEnsemblId(orthoitem.getEnsemblId2());
            } else {

                SpeciesInfo species1 = new SpeciesInfo();
                species1.setCommonName(orthoitem.getCommonName1());
                species1.setDataSource(orthoitem.getDataSource1());
                species1.setClassification(orthoitem.getClassification1());
                orthoitem.setTax(tax1);
                orthoitem.setVarName(orthoitem.getVarName1());
                orthoitem.setSpecies(species1);
                orthoitem.setEnsemblId(orthoitem.getEnsemblId1());
            }
//            String enspid=orthoitem.getEnsemblId();
//            根据同源的pid找到对应的gid才行
//            用同源的ensemblid查找对应的variant
//            String ensgid=geneBasicInfoMapper.getEnsGidByEnspId(enspid);
//            String gwasid=geneBasicInfoMapper.getEnsGidByEnspId(enspid);
//            这里待修改，目前用同源的pid查找trait找不到
//            等gbi_copy表格补全gwas_id以及hdbid时，用hdbpid找到对应的gwasid，再进行查询
//            List variantType=variantMapper.varTypeByGene(ensgid);
//            orthoitem.setOrthoVarName(variantType);
        }
        return orthoList;
    }
    //Go页面表格里取同源
    public List<OrthoGo> selectGeneGoOrthoInfo(String hdbid,String taxonid,String tableName) {
        List<OrthoGo> orthoList = ortho9031Mapper.selectGeneGoOrthoInfo(hdbid,tableName);
        for (OrthoGo orthoitem : orthoList) {

//            SpeciesInfo species1 = speciesInfoMapper.findSpeciesByTaxon(tax1);
            String tax2 = orthoitem.getTax2();
            String tax1 = orthoitem.getTax1();
//            SpeciesInfo species2 = speciesInfoMapper.findSpeciesByTaxon(tax2);
//            orthoitem.setSpecies2(species2);
//            在第一位，取第二位值，否则相反
            if (taxonid.equals(tax1)) {
                SpeciesInfo species2 = new SpeciesInfo();
                species2.setCommonName(orthoitem.getCommonName2());
                species2.setClassification(orthoitem.getClassification2());
                orthoitem.setTax(tax2);
                orthoitem.setSpecies(species2);
                orthoitem.setEnsemblId(orthoitem.getEnsemblId2());
            } else {

                SpeciesInfo species1 = new SpeciesInfo();
                species1.setCommonName(orthoitem.getCommonName1());
                species1.setClassification(orthoitem.getClassification1());
                orthoitem.setTax(tax1);
                orthoitem.setSpecies(species1);
                orthoitem.setEnsemblId(orthoitem.getEnsemblId1());
            }
            String enspid=orthoitem.getEnsemblId();
//            根据同源的pid找到对应的gid才行
//            用同源的ensemblid查找对应的variant
//            String ensgid=geneBasicInfoMapper.getEnsGidByEnspId(enspid);
//            String gwasid=geneBasicInfoMapper.getEnsGidByEnspId(enspid);
//            这里待修改，目前用同源的pid查找trait找不到
//            等gbi_copy表格补全gwas_id以及hdbid时，用hdbpid找到对应的gwasid，再进行查询
//            List variantType=variantMapper.varTypeByGene(ensgid);
//            orthoitem.setOrthoVarName(variantType);
        }
        return orthoList;
    }
    //Go页面表格里取同源
    public List<Ortho9031> selectGeneEoOrthoInfo(String hdbid,String taxonid,String tableName) {
        List<Ortho9031> orthoList = ortho9031Mapper.selectGeneEoOrthoInfo(hdbid,tableName);
        for (Ortho9031 orthoitem : orthoList) {

            String tax2 = orthoitem.getTax2();
            String tax1 = orthoitem.getTax1();
//            在第一位，取第二位值，否则相反
            if (taxonid.equals(tax1)) {
                SpeciesInfo species2 = new SpeciesInfo();
                species2.setCommonName(orthoitem.getCommonName2());
                species2.setDataSource(orthoitem.getDataSource2());
                species2.setClassification(orthoitem.getClassification2());
                orthoitem.setTax(tax2);
                orthoitem.setVarName(orthoitem.getVarName2());
                orthoitem.setSpecies(species2);
                orthoitem.setEnsemblId(orthoitem.getEnsemblId2());
            } else {

                SpeciesInfo species1 = new SpeciesInfo();
                species1.setCommonName(orthoitem.getCommonName1());
                species1.setDataSource(orthoitem.getDataSource1());
                species1.setClassification(orthoitem.getClassification1());
                orthoitem.setTax(tax1);
                orthoitem.setVarName(orthoitem.getVarName1());
                orthoitem.setSpecies(species1);
                orthoitem.setEnsemblId(orthoitem.getEnsemblId1());
            }
            String enspid=orthoitem.getEnsemblId();
        }
        return orthoList;
    }

}


