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
        List<GeneBasicInfo> geneList=geneDetailService.selectGene(geneName);
        List<Ortho9031> orthoList=orthoService.selectGeneOrthoInfo(geneName);
        for(Ortho9031 orthoitem:orthoList){
            Integer tax1=orthoitem.getTax1();
            SpeciesInfo species1=speciesService.findSpeciesByTaxon(tax1);
            orthoitem.setSpecies1(species1);
            Integer tax2=orthoitem.getTax2();
            SpeciesInfo species2=speciesService.findSpeciesByTaxon(tax2);
            orthoitem.setSpecies2(species2);
//            在第一位，取第二位值，否则相反
            if(taxonid.equals(""+tax1)){
                orthoitem.setTax(tax2);
                orthoitem.setProtein(orthoitem.getProtein2());
                orthoitem.setSpecies(species2);
                orthoitem.setEntrezId(orthoitem.getEntrezId2());
            }else{
                orthoitem.setTax(tax1);
                orthoitem.setProtein(orthoitem.getProtein1());
                orthoitem.setSpecies(species1);
                orthoitem.setEntrezId(orthoitem.getEntrezId1());
            }
        }
//        if(filerList.size() >0){
//            List listFilted=new ArrayList();
//            listFilted=orthoList.stream().filter(item -> filerList.contains(""+item.getTax())).collect(Collectors.toList());
//            orthoList=listFilted;
//        };
//        go必须要用ensembl来查询
//        先给一个所有的go term，用来画样式图
        List goBasicTermList=geneDetailService.selectBasicGo();
        InfoList.add(geneList);
        InfoList.add(orthoList);
        InfoList.add(goBasicTermList);
        return InfoList;
    };
//   根据ensembl id获取对应的go信息
    @RequestMapping(value = "/api/gene-detail-go/{ensid}", method = RequestMethod.GET)
    @ResponseBody
    public List goInformation(@PathVariable("ensid") String ensid) {
        List<GeneGoInfo> goInfoList = geneDetailService.getGoInfo(ensid);
        List<String> numInfo=geneDetailService.getGoNumbyGene(ensid);
        List resultList=new ArrayList();
        resultList.add(goInfoList);
        resultList.add(numInfo);
        return resultList;
    }
    @RequestMapping(value = "/api/gene-detail-var", method = RequestMethod.GET)
    @ResponseBody
    public List varInformation(@RequestParam String geneName) {
        List voInfoList = geneDetailService.voInfoList(geneName);
        return voInfoList;
    }
    @RequestMapping(value = "/api/gene-detail-trait", method = RequestMethod.GET)
    @ResponseBody
    public List traitInformation() {
        List traitInfoList = geneDetailService.traitInfoList();
        return traitInfoList;
    }
//    filter选项所需要的物种列表获取
//    后续需要添加筛选动植物
    @RequestMapping(value = "/api/species-list", method = RequestMethod.GET)
    @ResponseBody
    public List speciesInfoList() {
        List speciesInfoList = geneDetailService.speciesInfoList();
        return speciesInfoList;
    }
//    获取variant热图数据，

}
