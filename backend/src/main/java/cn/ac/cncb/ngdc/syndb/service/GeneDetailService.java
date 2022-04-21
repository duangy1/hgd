package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.*;
import cn.ac.cncb.ngdc.syndb.mapper.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.DecimalFormat;
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
    public List<GeneBasicInfo> selectGene(String geneName){
        List<GeneBasicInfo> geneList=geneBasicInfoMapper.selectGene(geneName);
        return  geneList;
    }

    public List<GeneBasicInfo> selectGeneGoInfo(String geneName){
        return  geneBasicInfoMapper.selectGeneGoInfo(geneName);
    }

    public List<GeneGoInfo> getGoInfo(String ensid){
        return  geneGoInfoMapper.getGoInfo(ensid);
    }
//    获取基因对应的go注释结果
    public List<String> getGoNumbyGene(String ensid){ return geneGoInfoMapper.getGoNumbyGene(ensid);}

    public List selectBasicGo(){return geneGoInfoMapper.selectBasicGo();}

//获取绘制热图的variant Ontology和基因注释信息
    public List voInfoList(String geneName){
        List<variant> varInfoListOfGene= variantMapper.voInfoOfGene(geneName);
        System.out.print("varInfoListOfGene:"+varInfoListOfGene);

        List<VOBasicTerm> voInfoList= VOBasicTermMapper.voInfoList();
        Integer numSum=0;
        for(variant varInfoItem : varInfoListOfGene){
            String varName=varInfoItem.getVarName();
            String[] snpList=varInfoItem.getSnpId().split(",");
            Integer snpNum=snpList.length;
            numSum += snpNum;
            VOBasicTerm voItem=voInfoList.stream().filter(d->d.getVoAnnotaion().equals(varName)).findFirst().get();
            voItem.setSnpNum(snpNum);
            voItem.setSnpList(snpList);
        }
        for(VOBasicTerm basicTerm:voInfoList){
            if(basicTerm.getSnpNum() != null){
                float opaNum= (float)basicTerm.getSnpNum()/numSum;
                basicTerm.setOpacity(opaNum);
            }else{
                basicTerm.setOpacity(0);
            }
        }
        return  voInfoList;
    }

    public List<TraitBasicInfo> traitInfoList(){
        return  geneBasicInfoMapper.traitInfoList();
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
