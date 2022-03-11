package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.entity.DataTableResultInfo;
//import cn.ac.cncb.ngdc.syndb.entity.gwasInfo;
import cn.ac.cncb.ngdc.syndb.entity.Ortho9031;
import cn.ac.cncb.ngdc.syndb.entity.Trait2gwas;
import cn.ac.cncb.ngdc.syndb.service.OrthoService;
import cn.ac.cncb.ngdc.syndb.service.TraitService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import cn.ac.cncb.ngdc.syndb.entity.TraitForm;

import java.util.List;

@Controller
public class TraitController {
    @Autowired
    TraitService traitService;

    @Autowired
    OrthoService orthoService;

    @RequestMapping(value = "/api/traits", method = RequestMethod.GET)
    @ResponseBody
    public DataTableResultInfo getBiomedicalTrait(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                            @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
                                            @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
                                            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
        if(pageNo == 1){
            pageNo = start/length+1;
        }
        Page<Trait2gwas> pageInfo = traitService.biomedicalTrait(pageNo,length);
        if(pageInfo != null ){
            for(Trait2gwas trait : pageInfo){
                int taxon = trait.getTaxonId();
                String geneid = trait.getGeneId(); // gwas gene id
                List<Ortho9031> ortholist =orthoService.findOrthByTaxonAndGene(taxon,geneid);
                Integer gwasOrgId=traitService.findGwasOrgidByTaxonId(taxon);
                if(ortholist != null ){
                    trait.setOrthoList(ortholist);
                }
                trait.setGwasOrgid(gwasOrgId);
            }
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
    public DataTableResultInfo getInfoByTraitName(@PathVariable("traitName") String traitName,
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
                List<Ortho9031> ortholist =orthoService.findOrthByTaxonAndGene(taxon,geneid);
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
    @RequestMapping(value="/api/traits/ortho-data",method = RequestMethod.POST)
    @ResponseBody
    public DataTableResultInfo getOrthoInfo(TraitForm traitForm,
                                            @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                            @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
                                            @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
                                            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
        if(pageNo == 1){
            pageNo = start/length+1;
        }
        int orthoSpecies = traitForm.getOrthoSpecies();
        int querySpecies = traitForm.getQuerySpecies();
        String traitName = traitForm.getTraitName();

        DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
        Page<Trait2gwas> pageInfo = traitService.getOrthoInfo(pageNo, length,querySpecies,orthoSpecies,traitName);

        if(pageInfo != null ){
            for(Trait2gwas trait : pageInfo){
                int taxon = trait.getTaxonId();
                String geneid = trait.getGeneId(); // gwas gene id
                List<Ortho9031> ortholist =orthoService.findOrthByTaxon2(taxon,geneid,orthoSpecies);
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
}
