package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.*;
import cn.ac.cncb.ngdc.syndb.mapper.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class GeneDetailService {
    @Resource
    GeneBasicInfoMapper geneBasicInfoMapper;
    @Resource
    VOBasicTermMapper VOBasicTermMapper;
    @Resource
    GeneGoInfoMapper geneGoInfoMapper;
    @Resource
    SpeciesInfoMapper speciesInfoMapper;
    @Resource
    VariantMapper variantMapper;
    @Resource
    Trait2gwasMapper trait2gwasMapper;
    @Resource
    TraitNameMapper traitNameMapper;
    @Resource
    GeneGoMapper geneGoMapper;
    public List<GeneBasicInfo> selectGene(String geneName){
        List<GeneBasicInfo> geneList=geneBasicInfoMapper.selectGene(geneName);
        GeneBasicInfo geneInfo=geneList.get(0);
        SpeciesInfo speciesName=speciesInfoMapper.findSpeciesByTaxon(geneInfo.getTaxonId());
        geneInfo.setSpeciesName(speciesName);
        String gene=new String();
        if(geneInfo != null ){
            if(geneInfo.getGeneSymbol() != null && geneInfo.getGeneSymbol().length()>0){
                gene = geneInfo.getGeneSymbol();
            }else if(geneInfo.getEnsemblGeneId() != null && geneInfo.getEnsemblGeneId().length() >0 ){
                gene = geneInfo.getEnsemblGeneId();
            }else if(geneInfo.getEntrezGene() != null && geneInfo.getEntrezGene().length()>0){
                gene = geneInfo.getEntrezGene();
            }else {
                gene = geneInfo.getHdbGeneId();
            }
        }
        geneInfo.setGeneid(gene);
        geneList.set(0,geneInfo);
        return  geneList;
    }

//    根据传入hdbID找到对应的gbi信息
    public String selectEnsgIdByhdbId(String hdbId){
        String ensgid=geneBasicInfoMapper.selectEnsgIdByhdbId(hdbId);
        return ensgid;
    }

    public List<GeneBasicInfo> selectGeneGoInfo(String geneName){
        return  geneBasicInfoMapper.selectGeneGoInfo(geneName);
    }

    public List<GeneGoInfo> getGoInfo(String ensid){
        return  geneGoInfoMapper.getGoInfo(ensid);
    }
//    获取基因对应的go注释结果
    public List<String> getGoNumbyGene(String ensid){ return geneGoInfoMapper.getGoNumbyGene(ensid);}

    //获取绘制热图的go Ontology和基因注释信息
    public List selectBasicGo(String geneName,String classification){
        String tableName="gene_go_"+classification;
        List<GeneGo> goGeneInfoList= geneGoMapper.goInfoOfGene(geneName,tableName);

        List<GoBasicTerm> goBasicInfoList= geneGoInfoMapper.selectBasicGo();
        Integer numSum=0;
        if(goGeneInfoList.size()!= 0) {
            for (GeneGo goInfoItem : goGeneInfoList) {
                String topGoId = goInfoItem.getTopGoId();

                if(goInfoItem.getGoId()!= null){
                    String[] goIdList  = goInfoItem.getGoId().split(",");
                    Integer goNum = goIdList.length;
                    numSum += goNum;
                    GoBasicTerm goItem=new GoBasicTerm();
                    if(topGoId == null){
                        goItem=goBasicInfoList.stream().filter(d -> d.getGoId().equals("otho")).findFirst().get();
                    }else {
                        goItem = goBasicInfoList.stream().filter(d -> d.getGoId().equals(topGoId)).findFirst().get();
                    };
                    goItem.setGoNum(goNum);
                    goItem.setGoList(goIdList);
                }
            }
        }
        for(GoBasicTerm basicTerm:goBasicInfoList){
            if(basicTerm.getGoNum() != null){
                float opaNum= (float)basicTerm.getGoNum()/numSum;
                basicTerm.setOpacity(opaNum);
            }else{
                basicTerm.setOpacity(0);
            }
        }
        return  goBasicInfoList;
    }

//获取绘制热图的variant Ontology和基因注释信息
    public List voInfoList(String geneName){
        List<variant> varInfoListOfGene= variantMapper.voInfoOfGene(geneName);
        List<VOBasicTerm> voInfoList= VOBasicTermMapper.voInfoList();
        Integer numSum=0;
        String speciesName=new String();
        for(variant varInfoItem : varInfoListOfGene){
            speciesName=varInfoItem.getSpeciesCommonName();
            String varName=varInfoItem.getVarName();
            String[] snpList=varInfoItem.getSnpId().split(",");
            Integer snpNum=snpList.length;
            numSum += snpNum;
            VOBasicTerm voItem=voInfoList.stream().filter(d->d.getVoAnnotaion().equals(varName)).findFirst().get();
            voItem.setSnpNum(snpNum);
            voItem.setSnpList(snpList);
        }
        for(VOBasicTerm basicTerm:voInfoList){
            basicTerm.setSpecies(speciesName);
//            根据species表得到查询vo具体条目的接口号
            String dataSource=speciesInfoMapper.getDataSource(speciesName);
            basicTerm.setDataSource(dataSource);
            if(basicTerm.getSnpNum() != null){
                float opaNum= (float)basicTerm.getSnpNum()/numSum;
                basicTerm.setOpacity(opaNum);
            }else{
                basicTerm.setOpacity(0);
            }
        }
        return  voInfoList;
    }
//根据 gene id取绘制热图的trait相关信息
    public List<TraitName> traitInfoList(String geneName){
        List<Trait2gwas> traitInfoByGeneList =trait2gwasMapper.traitInfoByGeneList(geneName);
        List<TraitName> traitList= traitNameMapper.traitInfoList();
        Integer numSum=0;
        for(Trait2gwas traitInfoItem : traitInfoByGeneList){
            String traitName=traitInfoItem.getTraitName();
            String[] gwasList=traitInfoItem.getGwasId().split(",");
            Integer gwasNum=gwasList.length;
            numSum += gwasNum;
            TraitName traitItem=traitList.stream().filter(d->d.getTraitName().equals(traitName)).findFirst().get();
            traitItem.setGwasNum(gwasNum);
            traitItem.setGwasList(gwasList);
        }
        for(TraitName basicTerm:traitList){
            if(basicTerm.getGwasNum() != null){
                float opaNum= (float)basicTerm.getGwasNum()/numSum;
                basicTerm.setOpacity(opaNum);
            }else{
                basicTerm.setOpacity(0);
            }
        }
        return  traitList;

    }

    public List<SpeciesOptionItem> speciesInfoList(){
        List<SpeciesInfo> speciesInfoList=speciesInfoMapper.speciesInfoList();
        List<SpeciesOptionItem> optionList=new ArrayList<SpeciesOptionItem>();
        for (SpeciesInfo infoItem:speciesInfoList){
            SpeciesOptionItem optionItem=new SpeciesOptionItem();
            optionItem.setLabel(infoItem.getCommonName());
            optionItem.setValue(infoItem.getTaxonId());
            optionList.add(optionItem);
        }

        return optionList;
    }
}
