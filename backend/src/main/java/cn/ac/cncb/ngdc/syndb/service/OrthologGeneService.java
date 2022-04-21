package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.bean.OrthoGeneBean;
import cn.ac.cncb.ngdc.syndb.entity.GeneBasicInfo;
import cn.ac.cncb.ngdc.syndb.entity.Ortho9031;
import cn.ac.cncb.ngdc.syndb.entity.OrthologGeneInfo;
import cn.ac.cncb.ngdc.syndb.entity.SpeciesInfo;
import cn.ac.cncb.ngdc.syndb.mapper.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OrthologGeneService {

    @Resource
    private GeneBasicInfoMapper geneBasicInfoMapper;

    @Resource
    private Ortho9031Mapper ortho9031Mapper;

    @Resource
    private SpeciesInfoMapper speciesInfoMapper;

    @Resource
    private Trait2gwasMapper trait2gwasMapper;

    @Resource
    private OrthologGeneInfoMapper orthologGeneInfoMapper;

    public Page<GeneBasicInfo> filterHomologGene(int pageNo, int pageSize, Map param) {
        PageHelper.startPage(pageNo, pageSize,true); //line 1
        return geneBasicInfoMapper.filterHomologGene(param);
    }


    public List<OrthologGeneInfo> findOrthGeneByGivenGeneAndTaxonUpdate(Map param){
        return orthologGeneInfoMapper.findOrthGeneByGivenGeneAndTaxon(param);
    }

    public List<Ortho9031> findOrthGeneByGivenGeneAndTaxon(Map param){
        return ortho9031Mapper.findOrthGeneByGivenGeneAndTaxon(param);
    }

    public List<SpeciesInfo> selectOtherSpecies(Map param){
        return speciesInfoMapper.selectOtherSpecies(param);
    }

    public SpeciesInfo findSpeciesByTaxon(int taxonId){
        return speciesInfoMapper.findSpeciesByTaxon(taxonId);
    }


    public Integer selectTraitCountByGeneAndTaxon(Map param){
        return trait2gwasMapper.selectTraitCountByGeneAndTaxon(param);
    }


    public List getHomologGene(int speciesType,String geneid,int taxonId){
        //find other species of this taxon type
        Map other = new HashMap();
        other.put("speciesType", speciesType);
        //  other.put("taxon", taxonId);

        List taxonList = new ArrayList();
        List<SpeciesInfo> speciesInfos = selectOtherSpecies(other);
        if(speciesInfos != null ){

            for(SpeciesInfo speciesInfo : speciesInfos ){
                taxonList.add(Integer.parseInt(speciesInfo.getTaxonId()));

            }
        }

        other.clear();

        if(taxonList.size()>0){
            other.put("taxonList",taxonList);
        }

        other.put("geneId",geneid);
        other.put("taxonId",taxonId);
        Map orthoMap = new HashMap();
        List<OrthologGeneInfo> orthologGeneInfoList = findOrthGeneByGivenGeneAndTaxonUpdate(other);
        if(orthologGeneInfoList != null && orthologGeneInfoList.size()>0 ){
            // System.out.println("----find ohtolog==="+ortho9031List.size());
            orthoMap.put(geneid+"|"+taxonId,geneid+"|"+taxonId);
            for(OrthologGeneInfo orthologGeneInfo: orthologGeneInfoList){
                String key = orthologGeneInfo.getHdbGeneId1()+"|"+orthologGeneInfo.getTaxId1();
                orthoMap.put(key,key);
                //System.out.println(ortho9031.getEnsemblId1() +","+ortho9031.getTax1()+","+ortho9031.getEnsemblId2()+","+ortho9031.getTax2());

               /* if(orthologGeneInfo.getEnsemblId1() != null && orthologGeneInfo.getEnsemblId1().length()>0){
                    String key = orthologGeneInfo.getEnsemblId1()+"|"+orthologGeneInfo.getTaxId1();
                    //    String key = ortho9031.getProtein1()+"|"+ortho9031.getTax1();
                    if(orthoMap.containsKey(key) == false){
                        orthoMap.put(key,key);
                    }
                }else if(orthologGeneInfo.getEntrezId1() != null && orthologGeneInfo.getEntrezId1().length() >0 ){
                    String key = orthologGeneInfo.getEntrezId1()+"|"+orthologGeneInfo.getTaxId1();
                    //    String key = ortho9031.getProtein1()+"|"+ortho9031.getTax1();
                    if(orthoMap.containsKey(key) == false){
                        orthoMap.put(key,key);
                    }
                }else if(orthologGeneInfo.getRefseqId1() != null && orthologGeneInfo.getRefseqId1().length() >0 ){
                    String key = orthologGeneInfo.getRefseqId1()+"|"+orthologGeneInfo.getTaxId1();
                    //    String key = ortho9031.getProtein1()+"|"+ortho9031.getTax1();
                    if(orthoMap.containsKey(key) == false){
                        orthoMap.put(key,key);
                    }
                }

                if(orthologGeneInfo.getEnsembId2() != null && orthologGeneInfo.getEnsembId2().length()>0) {
                    String key = orthologGeneInfo.getEnsembId2() + "|" + orthologGeneInfo.getTaxId2();
                    //   System.out.println("===key==="+key);
                    if (orthoMap.containsKey(key) == false) {
                        orthoMap.put(key, key);
                    }
                }else if(orthologGeneInfo.getEntrezId2() != null && orthologGeneInfo.getEntrezId2().length() >0 ){
                    String key = orthologGeneInfo.getEntrezId2()+"|"+orthologGeneInfo.getTaxId1();
                    //    String key = ortho9031.getProtein1()+"|"+ortho9031.getTax1();
                    if(orthoMap.containsKey(key) == false){
                        orthoMap.put(key,key);
                    }
                }else if(orthologGeneInfo.getRefseqId2() != null && orthologGeneInfo.getRefseqId2().length() >0 ){
                    String key = orthologGeneInfo.getRefseqId2()+"|"+orthologGeneInfo.getTaxId1();
                    //    String key = ortho9031.getProtein1()+"|"+ortho9031.getTax1();
                    if(orthoMap.containsKey(key) == false){
                        orthoMap.put(key,key);
                    }
                }*/

                key = orthologGeneInfo.getHdbGeneId2()+"|"+orthologGeneInfo.getTaxId2();
                orthoMap.put(key,key);

            }
            orthoMap.remove(geneid+"|"+taxonId);
        }

        List orthoGeneBeanList = new ArrayList();
        if(orthoMap!=null && orthoMap.size()>0){
            Set<Map.Entry> entries = orthoMap.entrySet();
            List<OrthoGeneBean> orthoGeneEmbedBeanList = new ArrayList<OrthoGeneBean>();
            int i =0 ;
            for(Map.Entry entry: entries){
                String key = entry.getKey().toString();
                //   System.out.println("key ==="+key);

                if(key.indexOf("|")>-1){
                    String [] keyarray = key.split("\\|");

                    if(keyarray != null && keyarray.length==2){

                        String gene = keyarray[0];
                        //find basic gene name
                        GeneBasicInfo geneBasicInfo = geneBasicInfoMapper.selectGeneByHdbGeneId(gene);
                        if(geneBasicInfo != null ){
                            if(geneBasicInfo.getGeneSymbol() != null && geneBasicInfo.getGeneSymbol().length()>0){
                                gene = geneBasicInfo.getGeneSymbol();
                            }else if(geneBasicInfo.getEnsemblGeneId() != null && geneBasicInfo.getEnsemblGeneId().length() >0 ){
                                gene = geneBasicInfo.getEnsemblGeneId();
                            }else if(geneBasicInfo.getEntrezGene() != null && geneBasicInfo.getEntrezGene().length()>0){
                                gene = geneBasicInfo.getEntrezGene();
                            }else {
                                gene = geneBasicInfo.getHdbGeneId();
                            }
                        }


                        String taxon = keyarray[1];

                        System.out.println("gene   "+gene+"    "+taxon);


                        OrthoGeneBean orthoGeneBean = new OrthoGeneBean();
                        orthoGeneBean.setGeneName(gene);

                        SpeciesInfo cursp = findSpeciesByTaxon(Integer.parseInt(taxon));
                        if(cursp != null ){
                            orthoGeneBean.setTaxonName(cursp.getLatinName());
                        }

                        orthoGeneEmbedBeanList.add(orthoGeneBean);
                        int idex = i+1;
                        if(idex >0 && idex %2 ==0 ){
                            orthoGeneBeanList.add(orthoGeneEmbedBeanList);
                            orthoGeneEmbedBeanList = new ArrayList<OrthoGeneBean>();
                        }
                        i++;

                    }
                }

            }
            if(orthoGeneEmbedBeanList.size()>0){
                orthoGeneBeanList.add(orthoGeneEmbedBeanList);
            }

        }

        return orthoGeneBeanList;
    }
}
