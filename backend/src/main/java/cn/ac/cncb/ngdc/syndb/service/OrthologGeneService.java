package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.bean.OrthoGeneBean;
import cn.ac.cncb.ngdc.syndb.entity.GeneBasicInfo;
import cn.ac.cncb.ngdc.syndb.entity.Ortho9031;
import cn.ac.cncb.ngdc.syndb.entity.SpeciesInfo;
import cn.ac.cncb.ngdc.syndb.mapper.GeneBasicInfoMapper;
import cn.ac.cncb.ngdc.syndb.mapper.Ortho9031Mapper;
import cn.ac.cncb.ngdc.syndb.mapper.SpeciesInfoMapper;
import cn.ac.cncb.ngdc.syndb.mapper.Trait2gwasMapper;
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

    public Page<GeneBasicInfo> filterHomologGene(int pageNo, int pageSize, Map param) {
        PageHelper.startPage(pageNo, pageSize,true); //line 1
        return geneBasicInfoMapper.filterHomologGene(param);
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


    public List<OrthoGeneBean> getHomologGene(int speciesType,String geneid,int taxonId){
        //find other species of this taxon type
        Map other = new HashMap();
        other.put("speciesType", speciesType);
        //  other.put("taxon", taxonId);

        List taxonList = new ArrayList();
        List<SpeciesInfo> speciesInfos = selectOtherSpecies(other);
        if(speciesInfos != null ){

            for(SpeciesInfo speciesInfo : speciesInfos ){
                taxonList.add(speciesInfo.getTaxonId());

            }
        }

        other.clear();

        if(taxonList.size()>0){
            other.put("taxonList",taxonList);
        }

        other.put("geneId",geneid);
        other.put("taxonId",taxonId);
        Map orthoMap = new HashMap();
        List<Ortho9031> ortho9031List = findOrthGeneByGivenGeneAndTaxon(other);
        if(ortho9031List != null && ortho9031List.size()>0 ){
            // System.out.println("----find ohtolog==="+ortho9031List.size());
            orthoMap.put(geneid+"|"+taxonId,geneid+"|"+taxonId);
            for(Ortho9031 ortho9031: ortho9031List){

                //System.out.println(ortho9031.getEnsemblId1() +","+ortho9031.getTax1()+","+ortho9031.getEnsemblId2()+","+ortho9031.getTax2());

                if(ortho9031.getEnsemblId1() != null && ortho9031.getEnsemblId1().length()>0){
                    String key = ortho9031.getEnsemblId1()+"|"+ortho9031.getTax1();
                    //    String key = ortho9031.getProtein1()+"|"+ortho9031.getTax1();
                    if(orthoMap.containsKey(key) == false){
                        orthoMap.put(key,key);
                    }
                }

                if(ortho9031.getEnsemblId2() != null && ortho9031.getEnsemblId2().length()>0) {
                    String key = ortho9031.getEnsemblId2() + "|" + ortho9031.getTax2();
                    //   System.out.println("===key==="+key);
                    if (orthoMap.containsKey(key) == false) {
                        orthoMap.put(key, key);
                    }
                }
            }
            orthoMap.remove(geneid+"|"+taxonId);
        }

        List<OrthoGeneBean> orthoGeneBeanList = new ArrayList<OrthoGeneBean>();
        if(orthoMap!=null && orthoMap.size()>0){
            Set<Map.Entry> entries = orthoMap.entrySet();
            for(Map.Entry entry: entries){
                String key = entry.getKey().toString();
                //   System.out.println("key ==="+key);

                if(key.indexOf("|")>-1){
                    String [] keyarray = key.split("\\|");

                    if(keyarray != null && keyarray.length==2){

                        String gene = keyarray[0];
                        String taxon = keyarray[1];

                        System.out.println("gene   "+gene+"    "+taxon);


                        OrthoGeneBean orthoGeneBean = new OrthoGeneBean();
                        orthoGeneBean.setGeneName(gene);

                        SpeciesInfo cursp = findSpeciesByTaxon(Integer.parseInt(taxon));
                        if(cursp != null ){
                            orthoGeneBean.setTaxonName(cursp.getLatinName());
                        }

                        orthoGeneBeanList.add(orthoGeneBean);

                    }
                }

            }
        }

        return orthoGeneBeanList;
    }
}
