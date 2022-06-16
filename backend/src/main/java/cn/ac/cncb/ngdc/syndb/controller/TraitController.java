package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.entity.*;
//import cn.ac.cncb.ngdc.syndb.entity.gwasInfo;
import cn.ac.cncb.ngdc.syndb.mapper.GeneBasicInfoMapper;
import cn.ac.cncb.ngdc.syndb.mapper.GeneGoInfoMapper;
import cn.ac.cncb.ngdc.syndb.service.GeneDetailService;
import cn.ac.cncb.ngdc.syndb.service.OrthoService;
import cn.ac.cncb.ngdc.syndb.service.SpeciesService;
import cn.ac.cncb.ngdc.syndb.service.TraitService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class TraitController {
    @Autowired
    TraitService traitService;

    @Autowired
    OrthoService orthoService;

    @Autowired
    GeneDetailService geneDetailService;

    @Autowired
    SpeciesService speciesService;

    @RequestMapping(value = "/api/traits", method = RequestMethod.GET)
    @ResponseBody

    public DataTableResultInfo getBiomedicalTrait(String classification,@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                            @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
                                            @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
                                            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
        System.out.print("classification:"+classification);
        if(pageNo == 1){
            pageNo = start/length+1;
        }
        Page<Trait2gwas> pageInfo = traitService.biomedicalTrait(classification,pageNo,length);
        for(Trait2gwas trait : pageInfo){
            int taxon = trait.getTaxonId();
            String geneid = trait.getGeneId(); //拿到trait里使用的id，大多是ensemblid
//                这hdb都是null
//           根据查到的trait里的ensemblid找到对应的hdbid，然后去同源表里查对应的同源list
            String hdbId= geneDetailService.getHdbIdByEnsId(geneid);
            List<Ortho9031> ortholist =orthoService.selectGeneTraitOrthoInfo(hdbId,""+taxon);
            Integer gwasOrgId=traitService.findGwasOrgidByTaxonId(taxon);
            trait.setOrthoList(ortholist);
            trait.setGwasOrgid(gwasOrgId);
        }

        DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
        dataTableResultInfo.setData(pageInfo);
        dataTableResultInfo.setDraw(draw);
        dataTableResultInfo.setLength(length);
        dataTableResultInfo.setRecordsTotal(pageInfo.getTotal());
        dataTableResultInfo.setRecordsFiltered(pageInfo.getTotal());

        return dataTableResultInfo;
    }

//    @RequestMapping("/api/traits/{Species}")
//    @ResponseBody
//    public DataTableResultInfo getTraitBySpecies(@PathVariable("Species") String Species,
//                                                 @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
//                                                 @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
//                                                 @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
//                                                 @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
//    ){
//        if(pageNo == 1){
//            pageNo = start/length+1;
//        }
//        DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
//        Page<Trait2gwas> pageInfo = traitService.selectTraitBySpecies(pageNo, length,Species);
//
//        dataTableResultInfo.setData(pageInfo);
//        dataTableResultInfo.setDraw(draw);
//        dataTableResultInfo.setLength(length);
//        dataTableResultInfo.setRecordsTotal(pageInfo.getTotal());
//        dataTableResultInfo.setRecordsFiltered(pageInfo.getTotal());
//        return dataTableResultInfo;
//        }
    @RequestMapping("/api/traits/get-by-trait-name/{traitName}")
    @ResponseBody
    public DataTableResultInfo getInfoByTraitName(String classification,@PathVariable("traitName") String traitName,
                                                 @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                                 @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
                                                 @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
                                                 @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
        if(pageNo == 1){
            pageNo = start/length+1;
        }
        DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
        Page<Trait2gwas> pageInfo = traitService.getInfoByTraitName(pageNo, length,traitName);

        if(pageInfo != null ){
            for(Trait2gwas trait : pageInfo){
                int taxon = trait.getTaxonId();
                String geneid = trait.getGeneId(); // gwas gene id
//                List<Ortho9031> ortholist =orthoService.findOrthByTaxonAndGene(taxon,geneid,classification);
                String hdbId= geneDetailService.getHdbIdByEnsId(geneid);
                List<Ortho9031> ortholist =orthoService.selectGeneTraitOrthoInfo(geneid,""+taxon);
                Integer gwasOrgId=traitService.findGwasOrgidByTaxonId(taxon);
                if(ortholist != null ){
                    trait.setOrthoList(ortholist);
                }
                trait.setGwasOrgid(gwasOrgId);
            }
        }

        dataTableResultInfo.setData(pageInfo);
        dataTableResultInfo.setDraw(draw);
        dataTableResultInfo.setLength(length);
        dataTableResultInfo.setRecordsTotal(pageInfo.getTotal());
        dataTableResultInfo.setRecordsFiltered(pageInfo.getTotal());
        return dataTableResultInfo;
    }

//筛选物种后的搜索
    @RequestMapping(value="/api/traits/ortho-data",method = RequestMethod.GET)
    @ResponseBody
    public DataTableResultInfo getOrthoInfo(@RequestParam List orthoSpecies,@RequestParam String querySpecies,@RequestParam String traitId,
                                            @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                            @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
                                            @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
                                            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
//        System.out.print("traitForm:"+traitForm);
        if(pageNo == 1){
            pageNo = start/length+1;
        }
//        String orthoSpecies = traitForm.getOrthoSpecies();
//        String querySpecies = traitForm.getQuerySpecies();
//        String traitName = traitForm.getTraitName();

        DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
        Page<Trait2gwas> pageInfo = traitService.getOrthoInfo(pageNo, length,querySpecies,traitId);

        if(pageInfo != null ){
            for(Trait2gwas trait : pageInfo){
                int taxon = trait.getTaxonId();
                String geneid = trait.getGeneId(); // gwas gene id，是trait表里的geneid
                List<Ortho9031> ortholist =orthoService.findOrthByTaxon2(geneid,orthoSpecies);
                Integer gwasOrgId=traitService.findGwasOrgidByTaxonId(taxon);
                if(ortholist != null ){
                    trait.setOrthoList(ortholist);
                }
                trait.setGwasOrgid(gwasOrgId);
            }
        }

        dataTableResultInfo.setData(pageInfo);
        dataTableResultInfo.setDraw(draw);
        dataTableResultInfo.setLength(length);
        dataTableResultInfo.setRecordsTotal(pageInfo.getTotal());
        dataTableResultInfo.setRecordsFiltered(pageInfo.getTotal());
        return dataTableResultInfo;
    }

//    trait页面提交过滤筛选时返回数据
//        @RequestMapping("/api/traits-filter")
//        @ResponseBody
//        public DataTableResultInfo filterTraitData(String classification,List orthoSpecies,String querySpecies,
//                                                   @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
//                                                   @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
//                                                   @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
//                                                   @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
//       ){
//            DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
//            if(pageNo == 1){
//                pageNo = start/length+1;
//            }
//            Page<Trait2gwas> pageInfo = traitService.getOrthoInfo(pageNo, length,querySpecies,orthoSpecies,traitName);
//            dataTableResultInfo.setData(pageInfo);
//            dataTableResultInfo.setDraw(draw);
//            dataTableResultInfo.setLength(length);
//            dataTableResultInfo.setRecordsTotal(pageInfo.getTotal());
//            dataTableResultInfo.setRecordsFiltered(pageInfo.getTotal());
//            return dataTableResultInfo;
//        }
    @RequestMapping(value = "/api/traits-item", method = RequestMethod.GET)
    @ResponseBody
    public TraitName traitsItem(@RequestParam String classification){

        TraitName traitName = traitService.traitsItem(classification);
        System.out.print("traitName:"+traitName);
        return  traitName;
    }
}
