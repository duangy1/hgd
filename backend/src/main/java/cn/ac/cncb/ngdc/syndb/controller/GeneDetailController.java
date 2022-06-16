package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.entity.*;
import cn.ac.cncb.ngdc.syndb.service.GeneDetailService;
import cn.ac.cncb.ngdc.syndb.service.OrthoService;
import cn.ac.cncb.ngdc.syndb.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GeneDetailController {
    @Autowired
    GeneDetailService geneDetailService;
    @Autowired
    OrthoService orthoService;
    @Autowired
    SpeciesService speciesService;
    @RequestMapping(value = "/api/gene-detail", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public List geneInformation( String geneName,String taxonid){
//        现在为查entrez gene
        List InfoList = new ArrayList();
        List<GeneBasicInfo> geneList=geneDetailService.selectGene(geneName,taxonid);
        List<Ortho9031> orthoList=orthoService.selectGeneOrthoInfo(geneName);
        if(geneList!=null) {
            for (Ortho9031 orthoitem : orthoList) {
                String tax1 = orthoitem.getTax1();
                SpeciesInfo species1 = speciesService.findSpeciesByTaxon(tax1);
                orthoitem.setSpecies1(species1);
                String tax2 = orthoitem.getTax2();
                SpeciesInfo species2 = speciesService.findSpeciesByTaxon(tax2);
                orthoitem.setSpecies2(species2);
                //            在第一位，取第二位值，否则相反
                if (taxonid.equals("" + tax1)) {
                    orthoitem.setTax(tax2);
                    orthoitem.setProtein(orthoitem.getProtein2());
                    orthoitem.setSpecies(species2);

                    orthoitem.setEntrezId(orthoitem.getEntrezId2());
                    String hdbId = orthoitem.getHdbId2();
                    //            找对应的ensembl gene id
                    GeneBasicInfo gbiInfo = geneDetailService.selectEnsgIdByhdbId(hdbId);
                    orthoitem.setGbiInfo(gbiInfo);
                } else {
                    orthoitem.setTax(tax1);
                    orthoitem.setProtein(orthoitem.getProtein1());
                    orthoitem.setSpecies(species1);
                    orthoitem.setEntrezId(orthoitem.getEntrezId1());
                    String hdbId = orthoitem.getHdbId1();
                    //            找对应的ensembl gene id
                    GeneBasicInfo gbiInfo = geneDetailService.selectEnsgIdByhdbId(hdbId);
                    orthoitem.setGbiInfo(gbiInfo);
                }
            }
        }
        InfoList.add(geneList);
        InfoList.add(orthoList);
        return InfoList;
    };
//   根据ensembl id获取对应的go信息
    @RequestMapping(value = "/api/gene-detail-go", method = RequestMethod.GET)
    @ResponseBody
    public List goInformation(@RequestParam String geneName,@RequestParam String classification) {
        List goBasicTermList=geneDetailService.selectBasicGo(geneName,classification);
        return goBasicTermList;
    }

    @RequestMapping(value = "/api/gene-detail-var", method = RequestMethod.GET)
    @ResponseBody
    public List varInformation(@RequestParam String geneName) {
        List voInfoList = geneDetailService.voInfoList(geneName);
        return voInfoList;
    }
//    绘制热图所需trait数据
    @RequestMapping(value = "/api/gene-detail-trait", method = RequestMethod.GET)
    @ResponseBody
    public List traitInformation(@RequestParam String geneName) {
        List traitInfoList = geneDetailService.traitInfoList(geneName);
        return traitInfoList;
    }
//    filter选项所需要的物种列表获取
//    后续需要添加筛选动植物
    @RequestMapping(value = "/api/species-list", method = RequestMethod.GET)
    @ResponseBody
    public List speciesInfoList(@RequestParam String taxonId) {
        List speciesInfoList = geneDetailService.speciesInfoList(taxonId);
        return speciesInfoList;
    }
//    表达
    @RequestMapping(value = "/api/gene-detail-expression", method = RequestMethod.GET)
    @ResponseBody
    public List expressionInformation(@RequestParam String geneName,@RequestParam String classification) {
        List<ExpressionTerm> expressionInfoList = geneDetailService.expressionInfoList(geneName,classification);
        return expressionInfoList;
    }

}
