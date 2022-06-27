package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.entity.*;
import cn.ac.cncb.ngdc.syndb.service.GeneDetailService;
import cn.ac.cncb.ngdc.syndb.service.OrthoService;
import cn.ac.cncb.ngdc.syndb.service.SpeciesService;
import cn.ac.cncb.ngdc.syndb.service.TraitService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
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

    public DataTableResultInfo getBiomedicalTrait(String classification,int traitId,String speciesName,@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                            @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
                                            @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
                                            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
        if(pageNo == 1){
            pageNo = start/length+1;
        }
        Page<Trait2gwas> pageInfo = traitService.biomedicalTrait(traitId,classification,pageNo,length,speciesName);
//        HashSet header = new HashSet();
        for(Trait2gwas trait : pageInfo){
            int taxon = trait.getTaxonId();
            String hdbid = trait.getHdbId(); //拿到trait里使用的id，大多是ensemblid
            if (hdbid != null && !hdbid.trim().equals("")) {
//           根据查到的trait里的ensemblid找到对应的hdbid，然后去同源表里查对应的同源list
                ProductResultDTO resultList = orthoService.selectGeneTraitOrthoInfo(hdbid, "" + taxon);
                List ortholist = resultList.getOrthoList();
//            赋给gwas里的物种编号
//            Integer gwasOrgId=traitService.findGwasOrgidByTaxonId(taxon);
                trait.setOrthoList(ortholist);
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



    @RequestMapping(value = "/api/traits-item", method = RequestMethod.GET)
    @ResponseBody
    public TraitName traitsItem(@RequestParam String traitID){

        TraitName traitName = traitService.traitsItem(traitID);
        return  traitName;
    }

//根据hdbid获取gwas接口所需的gwas_orgid和gwas_id
    @RequestMapping(value = "/api/traits-gwas-info", method = RequestMethod.GET)
    @ResponseBody
    public HdbGwas getGwasInfoByhdbid(String hdbId){
        HdbGwas hdbGwas = traitService.getGwasInfoByhdbid(hdbId);
        return  hdbGwas;
    }
}
