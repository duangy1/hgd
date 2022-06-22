package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.entity.SpeciesHeatmap;
import cn.ac.cncb.ngdc.syndb.entity.SpeciesInfo;
import cn.ac.cncb.ngdc.syndb.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
public class SpeciesController {
    @Autowired
    SpeciesService speciesService;
    @RequestMapping(value = "/api/species-all", method = RequestMethod.GET)
    @ResponseBody
    public List<SpeciesInfo> selectAllSpecies(){
        return speciesService.selectAllSpecies();
    }

    @RequestMapping(value = "/api/species-by-class", method = RequestMethod.GET)
    @ResponseBody
    public List<SpeciesInfo> selectAllSpecies(@RequestParam String classification){
        return speciesService.selectSpeciesByClass(classification);
    }
//绘制物种间同源基因统计热图时获取所有物种的commonname list接口
    @RequestMapping(value = "/api/speciesname-all", method = RequestMethod.GET)
    @ResponseBody
    public List speciesNameList(){
        List result=new ArrayList();
        List speciesNamelist=speciesService.speciesNameList();
        List<SpeciesHeatmap> speciesHeatmap=speciesService.speciesHeatmap();
        result.add(speciesNamelist);
        result.add(speciesHeatmap);
        return result;
    }

    @RequestMapping(value = "/api/species-var", method = RequestMethod.GET)
    @ResponseBody
    public List speciesNameListforVar(Integer speciesType){
        List speciesNamelist=speciesService.speciesNameListforVar(speciesType);
        return speciesNamelist;
    }
    @RequestMapping(value = "/api/species-trait", method = RequestMethod.GET)
    @ResponseBody
    public List speciesNameListforTrait(Integer speciesType){
        List speciesNamelist=speciesService.speciesNameListforTrait(speciesType);
        return speciesNamelist;
    }
    @RequestMapping(value = "/api/species-go", method = RequestMethod.GET)
    @ResponseBody
    public List speciesNameListforGo(Integer speciesType){
        List speciesNamelist=speciesService.speciesNameListforGo(speciesType);
        return speciesNamelist;
    }
    @RequestMapping(value = "/api/species-exp", method = RequestMethod.GET)
    @ResponseBody
    public List speciesNameListforExp(Integer speciesType){
        List speciesNamelist=speciesService.speciesNameListforExp(speciesType);
        return speciesNamelist;
    }
}
