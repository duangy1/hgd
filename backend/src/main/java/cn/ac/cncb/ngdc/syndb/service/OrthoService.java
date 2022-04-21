package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.GeneBasicInfo;
import cn.ac.cncb.ngdc.syndb.entity.Ortho9031;
import cn.ac.cncb.ngdc.syndb.mapper.GeneBasicInfoMapper;
import cn.ac.cncb.ngdc.syndb.mapper.Ortho9031Mapper;
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
//  把fromdb改成json,注释掉了，因为trait处不再需要显示具体的同源来源信息
//            String fromdb=orthoItem.getFromdb();
//            String newFromdb=fromdb.replaceAll("'","");
//            newFromdb=newFromdb.replaceAll("\\[","");
//            newFromdb=newFromdb.replaceAll("]","");
//            orthoItem.setFromdb(newFromdb);
        }
        System.out.print("ortholist:"+ortholist);
        return ortholist;
    }

    //这里写的不对，应该用同源基因找对应的trait，这个是用本身的基因找的
    public  List<Ortho9031> findOrthByTaxon2(int taxonId, String geneId,int taxonId2){
        List<Ortho9031> ortholist = ortho9031Mapper.findOrthByTaxon2(taxonId,geneId,taxonId2);
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
    public List<Ortho9031> selectGeneOrthoInfo(String geneName){
        List<Ortho9031> orthoList=ortho9031Mapper.selectGeneOrthoInfo(geneName);
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
}
