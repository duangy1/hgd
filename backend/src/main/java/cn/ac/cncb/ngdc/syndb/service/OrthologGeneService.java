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


    public List<GeneBasicInfo> filterHomologGene(Map param){
        return geneBasicInfoMapper.filterHomologGene(param);
    }


    public List<OrthologGeneInfo> findOrthGeneByGivenGeneAndTaxonUpdate(Map param){
        return orthologGeneInfoMapper.findOrthGeneByGivenGeneAndTaxon(param);
    }

    public List<OrthologGeneInfo> findOrthGeneByGivenGeneAndTaxonModify(Map param){
        return orthologGeneInfoMapper.findOrthGeneByGivenGeneAndTaxonModify(param);
    }

    public List<Ortho9031> findOrthGeneByGivenGeneAndTaxon(Map param){
        return ortho9031Mapper.findOrthGeneByGivenGeneAndTaxon(param);
    }

    public List<SpeciesInfo> selectOtherSpecies(Map param){
        return speciesInfoMapper.selectOtherSpecies(param);
    }

    public SpeciesInfo findSpeciesByTaxon(String taxonId){
        return speciesInfoMapper.findSpeciesByTaxon(taxonId);
    }


    public Integer selectTraitCountByGeneAndTaxon(Map param){
        return trait2gwasMapper.selectTraitCountByGeneAndTaxon(param);
    }

    public int selectVarCountByGeneAndTaxon(Map param){
        return orthologGeneInfoMapper.selectVarCountByGeneAndTaxon(param);
    }

    public int selectExpCountByGeneAndTaxon(Map param){
        return orthologGeneInfoMapper.selectExpCountByGeneAndTaxon(param);
    }

    public int selectGOCountByGeneAndTaxon(Map param){
        return orthologGeneInfoMapper.selectGOCountByGeneAndTaxon(param);
    }


    public List getHomologGene(int speciesType,String hdbgeneid,String showgene,int taxonId,List orthtaxonlist){
        //find other species of this taxon type

        Map other = new HashMap();

       /* other.put("speciesType", speciesType);
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
        }*/

        other.put("geneId",hdbgeneid);
        other.put("taxonId",taxonId);
        if(orthtaxonlist != null && orthtaxonlist.size() >0 ){
            other.put("taxonList",orthtaxonlist);
        }
        TreeMap orthoMap = new TreeMap();
        List<OrthologGeneInfo> orthologGeneInfoList = findOrthGeneByGivenGeneAndTaxonModify(other);
        if(orthologGeneInfoList != null && orthologGeneInfoList.size()>0 ){
            // System.out.println("----find ohtolog==="+ortho9031List.size());
            orthoMap.put(showgene+"|"+taxonId,showgene+"|"+taxonId);
            for(OrthologGeneInfo orthologGeneInfo: orthologGeneInfoList){


                    String gene = "";
                    GeneBasicInfo geneBasicInfo = new GeneBasicInfo();
                    geneBasicInfo.setTaxonId(orthologGeneInfo.getTaxId1());
                    geneBasicInfo.setGeneSymbol(orthologGeneInfo.getGeneSymbol1());
                    geneBasicInfo.setEntrezGene(orthologGeneInfo.getEntrezId1());
                    geneBasicInfo.setEnsemblGeneId(orthologGeneInfo.getEnsemblId1());
                    geneBasicInfo.setLatinName(orthologGeneInfo.getLatinName1());
                    geneBasicInfo.setHdbGeneId(orthologGeneInfo.getHdbGeneId1());
                    if(geneBasicInfo.getGeneSymbol() != null && geneBasicInfo.getGeneSymbol().length()>0){
                        gene = geneBasicInfo.getGeneSymbol();
                    }else if(geneBasicInfo.getEnsemblGeneId() != null && geneBasicInfo.getEnsemblGeneId().length() >0 ){
                        gene = geneBasicInfo.getEnsemblGeneId();
                    }else if(geneBasicInfo.getEntrezGene() != null && geneBasicInfo.getEntrezGene().length()>0){
                        gene = geneBasicInfo.getEntrezGene();
                    }else {
                        gene = geneBasicInfo.getHdbGeneId();
                    }
                    if(gene .length() >0 && gene.equals(showgene) ==false){
                        String key = gene + "|" + orthologGeneInfo.getTaxId1();
                        orthoMap.put(key, geneBasicInfo);
                    }


                //System.out.println(ortho9031.getEnsemblId1() +","+ortho9031.getTax1()+","+ortho9031.getEnsemblId2()+","+ortho9031.getTax2());



                    GeneBasicInfo geneBasicInfo1 = new GeneBasicInfo();
                    geneBasicInfo1.setTaxonId(orthologGeneInfo.getTaxId2());
                    geneBasicInfo1.setGeneSymbol(orthologGeneInfo.getGeneSymbol2());
                    geneBasicInfo1.setEntrezGene(orthologGeneInfo.getEntrezId2());
                    geneBasicInfo1.setEnsemblGeneId(orthologGeneInfo.getEnsembId2());
                    geneBasicInfo1.setLatinName(orthologGeneInfo.getLatinName2());
                    geneBasicInfo1.setHdbGeneId(orthologGeneInfo.getHdbGeneId2());
                    if(geneBasicInfo1.getGeneSymbol() != null && geneBasicInfo1.getGeneSymbol().length()>0){
                        gene = geneBasicInfo1.getGeneSymbol();
                    }else if(geneBasicInfo1.getEnsemblGeneId() != null && geneBasicInfo1.getEnsemblGeneId().length() >0 ){
                        gene = geneBasicInfo1.getEnsemblGeneId();
                    }else if(geneBasicInfo1.getEntrezGene() != null && geneBasicInfo1.getEntrezGene().length()>0){
                        gene = geneBasicInfo1.getEntrezGene();
                    }else {
                        gene = geneBasicInfo1.getHdbGeneId();
                    }

                if(gene .length() >0 && gene.equals(showgene) ==false){
                   String key = gene + "|" + orthologGeneInfo.getTaxId2();
                    orthoMap.put(key, geneBasicInfo1);
                }

            }
            orthoMap.remove(showgene+"|"+taxonId);
        }

        List orthoGeneBeanList = new ArrayList();
        if(orthoMap!=null && orthoMap.size()>0){
            Set<Map.Entry> entries = orthoMap.entrySet();
            List<OrthoGeneBean> orthoGeneEmbedBeanList = new ArrayList<OrthoGeneBean>();
            int i =0 ;
            for(Map.Entry entry: entries){
                String key = entry.getKey().toString();
                //   System.out.println("key ==="+key);
                GeneBasicInfo geneBasicInfo = (GeneBasicInfo) entry.getValue();
                if(key.indexOf("|")>-1){
                    String [] keyarray = key.split("\\|");

                    if(keyarray != null && keyarray.length==2){

                        String gene = keyarray[0];

                        //find basic gene name

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


                      //  String taxon = keyarray[1];

                  //      System.out.println("gene   "+gene+"    "+taxon);


                        OrthoGeneBean orthoGeneBean = new OrthoGeneBean();
                        orthoGeneBean.setHdbGeneId(geneBasicInfo.getHdbGeneId());
                        orthoGeneBean.setGeneName(gene);
                        orthoGeneBean.setTaxonName(geneBasicInfo.getLatinName());
                        orthoGeneBean.setTaxonId(geneBasicInfo.getTaxonId());

                        orthoGeneEmbedBeanList.add(orthoGeneBean);
                        int idex = i+1;
                        if(idex >0 && idex %3 ==0 ){
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
