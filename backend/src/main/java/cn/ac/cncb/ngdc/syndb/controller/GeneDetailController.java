package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.entity.*;
import cn.ac.cncb.ngdc.syndb.service.GeneDetailService;
import cn.ac.cncb.ngdc.syndb.service.OrthoService;
import cn.ac.cncb.ngdc.syndb.service.OrthologGeneService;
import cn.ac.cncb.ngdc.syndb.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class GeneDetailController {
    @Autowired
    GeneDetailService geneDetailService;
    @Autowired
    OrthoService orthoService;
    @Autowired
    SpeciesService speciesService;
    @Autowired
    OrthologGeneService orthologGeneService;

    @RequestMapping(value = "/api/gene-detail", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public List geneInformation( String hdbId,String taxonid){
//        现在为查entrez gene
        List InfoList = new ArrayList();
        List<GeneBasicInfo> geneList=geneDetailService.selectGene(hdbId,taxonid);
        Map param = new HashMap();
        param.put("geneId",hdbId);
        List<OrthologGeneInfo> orthoList=orthologGeneService.findOrthGeneByGivenGeneAndTaxonModify2(param);
        for (OrthologGeneInfo orthoitem : orthoList) {
            String hdbID1 = orthoitem.getHdbGeneId1();
//            查询的taxonid和第一位相同，则第二位是同源数据
            if (hdbId.equals("" + hdbID1)) {
                orthoitem.setOrthoPosition(2);
                orthoitem.setEnsemblId(orthoitem.getEnsemblId2());
                orthoitem.setGeneSymbol(orthoitem.getGeneSymbol2());
            } else {
                orthoitem.setGeneSymbol(orthoitem.getGeneSymbol1());
                orthoitem.setOrthoPosition(1);
                orthoitem.setEnsemblId(orthoitem.getEnsemblId1());
            }
        }
        InfoList.add(geneList);
        InfoList.add(orthoList);
        return InfoList;
    };
//   根据ensembl id获取对应的go信息
    @RequestMapping(value = "/api/gene-detail-go", method = RequestMethod.GET)
    @ResponseBody
    public List goInformation(@RequestParam String hdbId) {
//        if(classification.equals("others")){classification="animal";}
        List goBasicTermList=geneDetailService.selectBasicGo(hdbId);
        return goBasicTermList;
    }

    @RequestMapping(value = "/api/gene-detail-var", method = RequestMethod.GET)
    @ResponseBody
    public List varInformation(@RequestParam String hdbId) {
        List voInfoList = geneDetailService.voInfoList(hdbId);
        return voInfoList;
    }
    //   根据ensembl id获取对应的go信息
    @RequestMapping(value = "/api/gene-detail-go-1", method = RequestMethod.GET)
    @ResponseBody
    public List goInformation1(@RequestParam String hdbId) {
//        if(classification.equals("others")){classification="animal";}
        List goBasicTermList=geneDetailService.selectBasicGo1(hdbId);
        return goBasicTermList;
    }

    @RequestMapping(value = "/api/gene-detail-var-1", method = RequestMethod.GET)
    @ResponseBody
    public List varInformation1(@RequestParam String hdbId) {
        List voInfoList = geneDetailService.voInfoList1(hdbId);
        return voInfoList;
    }
//    绘制热图所需trait数据
    @RequestMapping(value = "/api/gene-detail-trait", method = RequestMethod.GET)
    @ResponseBody
    public List traitInformation(@RequestParam String hdbId) {
        List traitInfoList = geneDetailService.traitInfoList(hdbId);
        return traitInfoList;
    }
    @RequestMapping(value = "/api/gene-detail-trait-1", method = RequestMethod.GET)
    @ResponseBody
    public List traitInformation1(@RequestParam String hdbId) {
        List traitInfoList = geneDetailService.traitInfoList1(hdbId);
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
    public List expressionInformation(@RequestParam String hdbId,@RequestParam String classification) {
        List<ExpressionTerm> expressionInfoList = geneDetailService.expressionInfoList(hdbId,classification);
        return expressionInfoList;
    }
    @RequestMapping(value = "/api/gene-detail-expression-1", method = RequestMethod.GET)
    @ResponseBody
    public List expressionInformation1(@RequestParam String hdbId,@RequestParam String classification) {
        List<ExpressionTerm> expressionInfoList = geneDetailService.expressionInfoList1(hdbId,classification);
        return expressionInfoList;
    }

    @RequestMapping(value = "/api/gene-detail-ortho", method = RequestMethod.GET)
    @ResponseBody
    public GeneBasicInfo orthoinfoList(@RequestParam String hdbId) {
        GeneBasicInfo orthoList=geneDetailService.selectGeneByHdbGeneId(hdbId);
        return orthoList;
    }

}
