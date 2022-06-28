package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.bean.TreeSelectBasicBean;
import cn.ac.cncb.ngdc.syndb.bean.TreeSelectBean;
import cn.ac.cncb.ngdc.syndb.entity.*;
import cn.ac.cncb.ngdc.syndb.service.BasicService;
import cn.ac.cncb.ngdc.syndb.service.SpeciesService;
import cn.ac.cncb.ngdc.syndb.service.TraitNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("basic")
public class BasicController {

    @Autowired
    private SpeciesService speciesService;

    @Autowired
    private TraitNameService traitNameService;

    @Autowired
    private BasicService basicService;

    @RequestMapping(value="getSpecies", method=RequestMethod.GET)
    @ResponseBody
    public List<SpeciesInfo> getSpecies(Integer speciesType, HttpServletRequest request){
        List<SpeciesInfo> speciesInfos = null;
        if(speciesType != null ){
                if(speciesType == 1){
                    speciesInfos = speciesService.selectAllAnimals();
                }
                else if(speciesType ==2){
                    speciesInfos = speciesService.selectAllPlants();
                }
        }
        return speciesInfos;
    }


    @RequestMapping(value="getSpeciesModify", method=RequestMethod.GET)
    @ResponseBody
    public List<SpeciesInfo> getSpeciesModify(Integer speciesType, HttpServletRequest request){
        List<SpeciesInfo> speciesInfos = new ArrayList<SpeciesInfo>();
        SpeciesInfo speciesInfo = new SpeciesInfo();
        speciesInfo.setTaxonId("-1");
        speciesInfo.setCommonName("----Animals-----");
        speciesInfos.add(speciesInfo);

        List<SpeciesInfo>  tspecies= speciesService.selectAllAnimals();
        if(tspecies != null && tspecies.size() >0){
            for(SpeciesInfo ttsp : tspecies){
                speciesInfos.add(ttsp);
            }
        }

        SpeciesInfo speciesInfo1 = new SpeciesInfo();
        speciesInfo1.setTaxonId("-2");
        speciesInfo1.setCommonName("----Plants-----");
        speciesInfos.add(speciesInfo1);
        tspecies= speciesService.selectAllPlants();
        if(tspecies != null && tspecies.size() >0){
            for(SpeciesInfo ttsp : tspecies){
                speciesInfos.add(ttsp);
            }
        }



        SpeciesInfo speciesInfo2 = new SpeciesInfo();
        speciesInfo2.setTaxonId("-3");
        speciesInfo2.setCommonName("----Other-----");
        speciesInfos.add(speciesInfo2);


        Map map = new HashMap();
        map.put("speciesType","0");
        tspecies= speciesService.selectOtherSpecies(map);
        if(tspecies != null && tspecies.size() >0){
            for(SpeciesInfo ttsp : tspecies){
                speciesInfos.add(ttsp);
            }
        }

        return speciesInfos;
    }


    @RequestMapping(value="getTraitName", method=RequestMethod.GET)
    @ResponseBody
    public List<TreeSelectBean> getAllTraitName(Integer traitType,HttpServletRequest request){
        List<TreeSelectBean> treeSelectBeans = null;
        treeSelectBeans = new ArrayList<TreeSelectBean>();
        Map map = new HashMap();
        if((traitType != null && traitType == 1) || traitType == null) {
            map.put("traitType", 1);

            //<TraitName> list = (List<TraitName> )traitNameService.selectList(map);
            List<TraitName> list = (List<TraitName>) basicService.selectTraitNameList(map);
            if (list != null) {

                List<TreeSelectBasicBean> treeSelectAnimal = new ArrayList<TreeSelectBasicBean>();
                for (TraitName traitName : list) {
                    TreeSelectBasicBean treeSelectBean = new TreeSelectBasicBean();
                    treeSelectBean.setId(traitName.getTraitId() + "");
                    treeSelectBean.setLabel(traitName.getTraitName());

                    treeSelectAnimal.add(treeSelectBean);
                }

                TreeSelectBean treeSelectBean = new TreeSelectBean();
                treeSelectBean.setId("animal");
                treeSelectBean.setLabel("Animal");
                treeSelectBean.setChildren(treeSelectAnimal);
                treeSelectBeans.add(treeSelectBean);
                System.out.println("trait size=" + list.size());
            }

            map.clear();
            map.put("traitType", 2);

            //<TraitName> list = (List<TraitName> )traitNameService.selectList(map);
            List<TraitName>  list1 = (List<TraitName>) basicService.selectTraitNameList(map);
            if (list1 != null) {
                List<TreeSelectBasicBean> treeSelectAnimal = new ArrayList<TreeSelectBasicBean>();
                for (TraitName traitName : list1) {
                    TreeSelectBasicBean treeSelectBean = new TreeSelectBasicBean();
                    treeSelectBean.setId(traitName.getTraitId() + "");
                    treeSelectBean.setLabel(traitName.getTraitName());

                    treeSelectAnimal.add(treeSelectBean);
                }
                TreeSelectBean treeSelectBean = new TreeSelectBean();
                treeSelectBean.setId("plant");
                treeSelectBean.setLabel("Plant");
                treeSelectBean.setChildren(treeSelectAnimal);
                treeSelectBeans.add(treeSelectBean);
            }


        }

       /* if(traitType != null && traitType ==2 ) {
            map.clear();
            map.put("traitType", 2);

            //<TraitName> list = (List<TraitName> )traitNameService.selectList(map);
            List<TraitName>  list = (List<TraitName>) basicService.selectTraitNameList(map);
            if (list != null) {
                List<TreeSelectBasicBean> treeSelectAnimal = new ArrayList<TreeSelectBasicBean>();
                for (TraitName traitName : list) {
                    TreeSelectBasicBean treeSelectBean = new TreeSelectBasicBean();
                    treeSelectBean.setId(traitName.getTraitId() + "");
                    treeSelectBean.setLabel(traitName.getTraitName());

                    treeSelectAnimal.add(treeSelectBean);
                }

                TreeSelectBean treeSelectBean = new TreeSelectBean();
                treeSelectBean.setId("plant");
                treeSelectBean.setLabel("Plant");
                treeSelectBean.setChildren(treeSelectAnimal);
                treeSelectBeans.add(treeSelectBean);
                System.out.println("trait size=" + list.size());
            }

        }*/
        return treeSelectBeans;
    }

    @RequestMapping(value="getVoTerm", method=RequestMethod.GET)
    @ResponseBody
    public List<TreeSelectBean> getAllVariationTerm(HttpServletRequest request){
        List<TreeSelectBean> treeSelectBeans = null;
        treeSelectBeans = new ArrayList<TreeSelectBean>();


         List<VOBasicTerm> voBasicTermList = null;
         voBasicTermList= basicService.selectVariationTermList();
         if(voBasicTermList != null && voBasicTermList.size() >0 ){

             List<TreeSelectBasicBean> treeSelectHigh = new ArrayList<TreeSelectBasicBean>();
             List<TreeSelectBasicBean> treeSelectModerate = new ArrayList<TreeSelectBasicBean>();
             List<TreeSelectBasicBean> treeSelectLow = new ArrayList<TreeSelectBasicBean>();
             List<TreeSelectBasicBean> treeSelectModifier = new ArrayList<TreeSelectBasicBean>();
             for(VOBasicTerm voBasicTerm : voBasicTermList){
                if(voBasicTerm.getVoClassification().toLowerCase().equals("high") == true){
                    TreeSelectBasicBean treeSelectBean = new TreeSelectBasicBean();
                    treeSelectBean.setId(voBasicTerm.getVoAnnotaion() + "");
                    treeSelectBean.setLabel(voBasicTerm.getVoAnnotaion());

                    treeSelectHigh.add(treeSelectBean);
                }else if(voBasicTerm.getVoClassification().toLowerCase().equals("moderate") == true){
                     TreeSelectBasicBean treeSelectBean = new TreeSelectBasicBean();
                     treeSelectBean.setId(voBasicTerm.getVoAnnotaion() + "");
                     treeSelectBean.setLabel(voBasicTerm.getVoAnnotaion());

                     treeSelectModerate.add(treeSelectBean);
                 }else if(voBasicTerm.getVoClassification().toLowerCase().equals("low") == true){
                     TreeSelectBasicBean treeSelectBean = new TreeSelectBasicBean();
                     treeSelectBean.setId(voBasicTerm.getVoAnnotaion() + "");
                     treeSelectBean.setLabel(voBasicTerm.getVoAnnotaion());

                     treeSelectLow.add(treeSelectBean);
                 }else if(voBasicTerm.getVoClassification().toLowerCase().equals("modifier") == true){
                     TreeSelectBasicBean treeSelectBean = new TreeSelectBasicBean();
                     treeSelectBean.setId(voBasicTerm.getVoAnnotaion() + "");
                     treeSelectBean.setLabel(voBasicTerm.getVoAnnotaion());

                     treeSelectModifier.add(treeSelectBean);
                 }


             }

             TreeSelectBean treeSelectBean = new TreeSelectBean();
             treeSelectBean.setId("high");
             treeSelectBean.setLabel("High");
             treeSelectBean.setChildren(treeSelectHigh);
             treeSelectBeans.add(treeSelectBean);

             treeSelectBean = new TreeSelectBean();
             treeSelectBean.setId("moderate");
             treeSelectBean.setLabel("Moderate");
             treeSelectBean.setChildren(treeSelectModerate);
             treeSelectBeans.add(treeSelectBean);

             treeSelectBean = new TreeSelectBean();
             treeSelectBean.setId("low");
             treeSelectBean.setLabel("Low");
             treeSelectBean.setChildren(treeSelectLow);
             treeSelectBeans.add(treeSelectBean);

             treeSelectBean = new TreeSelectBean();
             treeSelectBean.setId("modifier");
             treeSelectBean.setLabel("Modifier");
             treeSelectBean.setChildren(treeSelectModifier);
             treeSelectBeans.add(treeSelectBean);

         }


        return treeSelectBeans;

    }



    @RequestMapping(value="getExpressionTerm", method=RequestMethod.GET)
    @ResponseBody
    public List<TreeSelectBean> getAllExpressionTerm(HttpServletRequest request){
        List<TreeSelectBean> treeSelectBeans = null;
        treeSelectBeans = new ArrayList<TreeSelectBean>();

        List<ExpressionTerm> expressionTermList = basicService.expressionInfoList("");
        if(expressionTermList != null && expressionTermList.size() >0 ){
            List<TreeSelectBasicBean> treeSelectTissue = new ArrayList<TreeSelectBasicBean>();
            List<TreeSelectBasicBean> treeSelectDisease = new ArrayList<TreeSelectBasicBean>();
            List<TreeSelectBasicBean> treeSelectContext = new ArrayList<TreeSelectBasicBean>();


            for(ExpressionTerm expressionTerm : expressionTermList){
                if(expressionTerm.getEoClassification().toLowerCase().equals("animal tissue") == true){
                    TreeSelectBasicBean treeSelectBean = new TreeSelectBasicBean();
                    treeSelectBean.setId(expressionTerm.getEoAnnotation() + "");
                    treeSelectBean.setLabel(expressionTerm.getEoAnnotation());

                    treeSelectTissue.add(treeSelectBean);
                }else if(expressionTerm.getEoClassification().toLowerCase().equals("human disease") == true){
                    TreeSelectBasicBean treeSelectBean = new TreeSelectBasicBean();
                    treeSelectBean.setId(expressionTerm.getEoAnnotation() + "");
                    treeSelectBean.setLabel(expressionTerm.getEoAnnotation());

                    treeSelectDisease.add(treeSelectBean);
                }else if(expressionTerm.getClassification().toLowerCase().equals("1") == true){
                    TreeSelectBasicBean treeSelectBean = new TreeSelectBasicBean();
                    treeSelectBean.setId(expressionTerm.getEoAnnotation() + "");
                    treeSelectBean.setLabel(expressionTerm.getEoAnnotation());

                    treeSelectContext.add(treeSelectBean);
                }

            }

            TreeSelectBean treeSelectBean = new TreeSelectBean();
            treeSelectBean.setId("animal tissue");
            treeSelectBean.setLabel("Animal Tissue");
            treeSelectBean.setChildren(treeSelectTissue);
            treeSelectBeans.add(treeSelectBean);

            treeSelectBean = new TreeSelectBean();
            treeSelectBean.setId("human disease");
            treeSelectBean.setLabel("Human Disease");
            treeSelectBean.setChildren(treeSelectDisease);
            treeSelectBeans.add(treeSelectBean);

            treeSelectBean = new TreeSelectBean();
            treeSelectBean.setId("biological context");
            treeSelectBean.setLabel("Biological Context");
            treeSelectBean.setChildren(treeSelectContext);
            treeSelectBeans.add(treeSelectBean);


        }

        return treeSelectBeans;
    }


    @RequestMapping(value="getGOTerm", method=RequestMethod.GET)
    @ResponseBody
    public List<TreeSelectBasicBean> getAllGOTerm(HttpServletRequest request){
        List<TreeSelectBasicBean> treeSelectBeans = null;
        treeSelectBeans = new ArrayList<TreeSelectBasicBean>();

        List<GoBasicTerm> goTermList = basicService.getGoTerms();
        if(goTermList != null && goTermList.size() >0 ){

            for(GoBasicTerm goBasicTerm : goTermList){
                TreeSelectBasicBean treeSelectBean = new TreeSelectBasicBean();
                treeSelectBean.setId(goBasicTerm.getGoId()+ "");
                treeSelectBean.setLabel(goBasicTerm.getGoAnnotations());

                treeSelectBeans.add(treeSelectBean);
            }
        }

        return treeSelectBeans;
    }


}
