package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.bean.TreeSelectBasicBean;
import cn.ac.cncb.ngdc.syndb.bean.TreeSelectBean;
import cn.ac.cncb.ngdc.syndb.entity.SpeciesInfo;
import cn.ac.cncb.ngdc.syndb.entity.TraitName;
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
        }

        if(traitType != null && traitType ==2 ) {
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

        }
        return treeSelectBeans;
    }
}
