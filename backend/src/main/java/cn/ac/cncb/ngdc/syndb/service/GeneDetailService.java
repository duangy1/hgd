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
    @Resource
    ExpressionTermMapper expressionTermMapper;
    @Resource
    GeneExpressionMapper geneExpressionMapper;


    public List<GeneBasicInfo> selectGene(String hdbId,String taxonid){
        List<GeneBasicInfo> geneList=geneBasicInfoMapper.selectGene(hdbId,taxonid);
        GeneBasicInfo geneInfo=geneList.get(0);
        SpeciesInfo speciesName=speciesInfoMapper.findSpeciesByTaxon(taxonid);
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
    public GeneBasicInfo selectEnsgIdByhdbId(String hdbId){
        GeneBasicInfo gbiInfo=geneBasicInfoMapper.selectEnsgIdByhdbId(hdbId);
        return gbiInfo;
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
    public List selectBasicGo(String hdbId){
//        if(classification.equals("others")){classification="animal";}
//        String tableName="gene_go_"+classification;
        List<GeneGo> goGeneInfoList= geneGoMapper.goInfoOfGene(hdbId);

        List<GoBasicTerm> goBasicInfoList= geneGoInfoMapper.selectBasicGo();
        Integer numSum=0;
        if(goGeneInfoList.size() > 0) {
//            循环查到的gene_go_info
            for (GeneGo goInfoItem : goGeneInfoList) {
                String topGoId = goInfoItem.getTopGoId();
                String goId=goInfoItem.getGoId();
                if(topGoId != null && topGoId.length() != 0){
                    String[] goIdList  = goId.split(",");
                    Integer goSumNum=goInfoItem.getGoSumNum();
                    numSum += goSumNum;
                    GoBasicTerm goItem = goBasicInfoList.stream().filter(d -> d.getGoId().equals(topGoId)).findFirst().get();
                    if(goItem==null){
                        System.out.print("goItem:"+goItem);
                    }
                    goItem.setGoNum(goSumNum);
                    goItem.setGoList(goIdList);
                }
            }

        System.out.print("numSum:"+numSum+","+"hdbId:"+hdbId);
        if(numSum>0) {
            for (GoBasicTerm basicTerm : goBasicInfoList) {
                if (basicTerm.getGoNum() != null) {
                    float opaNum = (float) basicTerm.getGoNum() / numSum;
                    basicTerm.setOpacity(opaNum);
                } else {
                    basicTerm.setOpacity(0);
                }
            }
            return goBasicInfoList;
        }else{
            return null;
        }
    }else{
        return null;
    }
}

//获取绘制热图的variant Ontology和基因注释信息
    public List voInfoList(String hdbId){
        List<Variant> varInfoListOfGene= variantMapper.voInfoOfGene(hdbId);
        List<VOBasicTerm> voInfoList= VOBasicTermMapper.voInfoList();
        Integer numSum=0;
        if(varInfoListOfGene.size()>0){
            String speciesName=varInfoListOfGene.get(0).getSpeciesCommonName();
            String dataSource = varInfoListOfGene.get(0).getDataSource();

            for(Variant varInfoItem : varInfoListOfGene){
                String varName=varInfoItem.getVarName();
                String[] snpList=varInfoItem.getSnpId().split(",");
                Integer snpNum=snpList.length;
                numSum += snpNum;
                VOBasicTerm voItem=voInfoList.stream().filter(d->d.getVoAnnotaion().equals(varName)).findFirst().get();
                voItem.setSnpNum(snpNum);
                voItem.setSnpList(snpList);
            }
            if(numSum>0) {
                for (VOBasicTerm basicTerm : voInfoList) {
                    basicTerm.setSpecies(speciesName);
                    //            根据species表得到查询vo具体条目的接口号
                    basicTerm.setDataSource(dataSource);
                    if (basicTerm.getSnpNum() != null) {
                        float opaNum = (float) basicTerm.getSnpNum() / numSum;
                        basicTerm.setOpacity(opaNum);
                    } else {
                        basicTerm.setOpacity(0);
                    }
                }
                return  voInfoList;
            }else{
                return  null;
            }

        }else{
            return  null;
        }
    }

    //获取绘制热图的go Ontology和基因注释信息
    public List selectBasicGo1(String hdbId){
//        if(classification.equals("others")){classification="animal";}
//        String tableName="gene_go_"+classification;
        List<GeneGo> goGeneInfoList= geneGoMapper.goInfoOfGene(hdbId);

        List<GoBasicTerm> goBasicInfoList= geneGoInfoMapper.selectBasicGo();
        Integer numSum=0;

//            循环查到的gene_go_info
        for (GeneGo goInfoItem : goGeneInfoList) {
            String topGoId = goInfoItem.getTopGoId();
            String goId=goInfoItem.getGoId();
            String[] goIdList  = goId.split(",");
            Integer goSumNum=goInfoItem.getGoSumNum();
            numSum += goSumNum;
            GoBasicTerm goItem = goBasicInfoList.stream().filter(d -> d.getGoId().equals(topGoId)).findFirst().get();
            if(goItem==null){
                System.out.print("goItem:"+goItem);
            }
            goItem.setGoNum(goSumNum);
            goItem.setGoList(goIdList);
        }

        for (GoBasicTerm basicTerm : goBasicInfoList) {
            if (basicTerm.getGoNum() != null) {
                float opaNum = (float) basicTerm.getGoNum() / numSum;
                basicTerm.setOpacity(opaNum);
            } else {
                basicTerm.setOpacity(0);
            }
        }
        return goBasicInfoList;

    }

    //获取绘制热图的variant Ontology和基因注释信息
    public List voInfoList1(String hdbId){
        List<Variant> varInfoListOfGene= variantMapper.voInfoOfGene(hdbId);
        List<VOBasicTerm> voInfoList= VOBasicTermMapper.voInfoList();
        Integer numSum=0;
        String speciesName="";
        String dataSource ="";

        for(Variant varInfoItem : varInfoListOfGene){
//            speciesName=varInfoItem.getSpeciesCommonName();
            String varName=varInfoItem.getVarName();
            dataSource=varInfoItem.getDataSource();
            String[] snpList=varInfoItem.getSnpId().split(",");
            Integer snpNum=snpList.length;
            numSum += snpNum;
            VOBasicTerm voItem=voInfoList.stream().filter(d->d.getVoAnnotaion().equals(varName)).findFirst().get();
            voItem.setSnpNum(snpNum);
            voItem.setSnpList(snpList);
        }

        for (VOBasicTerm basicTerm : voInfoList) {
            basicTerm.setSpecies(speciesName);
            //            根据species表得到查询vo具体条目的接口号
            basicTerm.setDataSource(dataSource);
            if (basicTerm.getSnpNum() != null) {
                float opaNum = (float) basicTerm.getSnpNum() / numSum;
                basicTerm.setOpacity(opaNum);
            } else {
                basicTerm.setOpacity(0);
            }
        }
        return  voInfoList;

    }


//根据 gene id取绘制热图的trait相关信息
    public List<TraitName> traitInfoList1(String hdbId){
        List<Trait2gwas> traitInfoByGeneList =trait2gwasMapper.traitInfoByGeneList(hdbId);
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
    public List<TraitName> traitInfoList(String hdbId){
        List<Trait2gwas> traitInfoByGeneList =trait2gwasMapper.traitInfoByGeneList(hdbId);
        List<TraitName> traitList= traitNameMapper.traitInfoList();
        Integer numSum=0;
        if(traitInfoByGeneList.size()>0) {
            for (Trait2gwas traitInfoItem : traitInfoByGeneList) {
                String traitName = traitInfoItem.getTraitName();
                String[] gwasList = traitInfoItem.getGwasId().split(",");
                Integer gwasNum = gwasList.length;
                numSum += gwasNum;
                TraitName traitItem = traitList.stream().filter(d -> d.getTraitName().equals(traitName)).findFirst().get();
                traitItem.setGwasNum(gwasNum);
                traitItem.setGwasList(gwasList);
            }
            if(numSum>0){
            for (TraitName basicTerm : traitList) {
                if (basicTerm.getGwasNum() != null) {
                    float opaNum = (float) basicTerm.getGwasNum() / numSum;
                    basicTerm.setOpacity(opaNum);
                } else {
                    basicTerm.setOpacity(0);
                }
            }
            return traitList;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public List<SpeciesOptionItem> speciesInfoList(String taxonId){
        String classification=speciesInfoMapper.getClassificatoinByTax(taxonId);
        System.out.print("classification"+classification);
        List<SpeciesInfo> speciesInfoList=speciesInfoMapper.speciesInfoList(classification);
        List<SpeciesOptionItem> optionList=new ArrayList<SpeciesOptionItem>();
//        返回一个包含commonname和taxid的list,整理成了前端需要的格式，属性label，value
        for (SpeciesInfo infoItem:speciesInfoList){
            SpeciesOptionItem optionItem=new SpeciesOptionItem();
            optionItem.setLabel(infoItem.getCommonName());
            optionItem.setValue(infoItem.getTaxonId());
            optionList.add(optionItem);
        }

        return optionList;
    }
    public String getHdbIdByEnsId(String ensid){
        return geneBasicInfoMapper.getHdbIdByEnsId(ensid);
    }

    //根据 gene id取绘制热图的trait相关信息
    public List<ExpressionTerm> expressionInfoList(String hdbId,String classification){
        List<GeneExpression> expressionInfoByGeneList =geneExpressionMapper.expressionInfoByGeneList(hdbId);
        List<ExpressionTerm> expressionList= expressionTermMapper.expressionInfoList(classification);
        Integer numSum=0;
        Integer genOrgid;
        if(expressionInfoByGeneList.size()>0) {
            genOrgid=expressionInfoByGeneList.get(0).getGenOrgId();
            System.out.print("genOrgid:"+genOrgid);
            for (GeneExpression expressionInfoItem : expressionInfoByGeneList) {
                String traitName = expressionInfoItem.getAnnotation();
                String[] prjList = expressionInfoItem.getBioProjectId().split(",");
                Integer gwasNum = prjList.length;
                numSum += gwasNum;

                ExpressionTerm expressionItem = expressionList.stream().filter(d -> d.getEoAnnotation().equals(traitName)).findFirst().get();
                expressionItem.setPrjNum(gwasNum);
                expressionItem.setPrjList(prjList);
                expressionItem.setGeneId(expressionInfoItem.getGeneId());
                expressionItem.setTaxonId(expressionInfoItem.getTaxonId());
            }
//            if(numSum>0) {
            for (ExpressionTerm basicTerm : expressionList) {
                basicTerm.setGenOrgid(genOrgid);
                if (basicTerm.getPrjNum() != null) {
                    float opaNum = (float) basicTerm.getPrjNum() / numSum;
                    basicTerm.setOpacity(opaNum);
                } else {
                    basicTerm.setOpacity(0);
                }

            }
            return expressionList;
//            }else{
//                return null;
//            }

        }else{
            return  null;
        }

    }

    public List<ExpressionTerm> expressionInfoList1(String hdbId,String classification){
        List<GeneExpression> expressionInfoByGeneList =geneExpressionMapper.expressionInfoByGeneList(hdbId);
        List<ExpressionTerm> expressionList= expressionTermMapper.expressionInfoList(classification);
        Integer numSum=0;
        for(GeneExpression expressionInfoItem : expressionInfoByGeneList){
            String traitName=expressionInfoItem.getAnnotation();
            String[] prjList=expressionInfoItem.getBioProjectId().split(",");
            Integer gwasNum=prjList.length;
            numSum += gwasNum;
            ExpressionTerm expressionItem=expressionList.stream().filter(d->d.getEoAnnotation().equals(traitName)).findFirst().get();
            expressionItem.setPrjNum(gwasNum);
            expressionItem.setPrjList(prjList);
            expressionItem.setGeneId(expressionInfoItem.getGeneId());
            expressionItem.setTaxonId(expressionInfoItem.getTaxonId());
        }
        for(ExpressionTerm basicTerm:expressionList){
            if(basicTerm.getPrjNum() != null){
                float opaNum= (float)basicTerm.getPrjNum()/numSum;
                basicTerm.setOpacity(opaNum);
            }else{
                basicTerm.setOpacity(0);
            }
        }
        return  expressionList;

    }

    public GeneBasicInfo selectGeneByHdbGeneId(String hdbId){
        return geneBasicInfoMapper.selectGeneByHdbGeneId(hdbId);
    }
}
