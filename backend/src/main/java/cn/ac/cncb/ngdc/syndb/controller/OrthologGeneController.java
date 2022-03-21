package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.bean.OrthoGeneBean;
import cn.ac.cncb.ngdc.syndb.entity.DataTableResultInfo;
import cn.ac.cncb.ngdc.syndb.entity.GeneBasicInfo;
import cn.ac.cncb.ngdc.syndb.entity.Ortho9031;
import cn.ac.cncb.ngdc.syndb.entity.SpeciesInfo;
import cn.ac.cncb.ngdc.syndb.service.OrthologGeneService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Array;
import java.util.*;

/********************************************
 * this is used to browse ortholog gene
 */

@Controller
@RequestMapping("gene")
public class OrthologGeneController {

    @Autowired
    private OrthologGeneService orthologGeneService;


    @RequestMapping(value="filterHomolog", method= RequestMethod.GET)
    @ResponseBody
    public DataTableResultInfo browseOrthologGene(String taxonids, String traitids, HttpServletRequest request,@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                                  @RequestParam(value = "length", required = false, defaultValue = "5") Integer length,
                                                  @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
                                                  @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo){
        if(pageNo == 1){
            pageNo = start/length+1;
        }
        DataTableResultInfo result = null;

        ArrayList talist = new ArrayList();
        if(taxonids != null && taxonids.length()>0){
            if(taxonids.indexOf(",")>-1){
                String [] taxons = taxonids.split("\\,");
                if(taxons != null && taxons.length>0){
                    for(String str : taxons){
                        talist.add(Integer.parseInt(str));
                    }
                }
            }else{
                System.out.println("taxon------------"+taxonids);
                talist.add(Integer.parseInt(taxonids));
            }

        }


        ArrayList traitlist = new ArrayList();
        if(traitids != null && traitids.length()>0){
            if(traitids.indexOf(",")>-1){
                String [] taxons = traitids.split("\\,");
                if(taxons != null && taxons.length>0){
                    for(String str : taxons){
                        traitlist.add(Integer.parseInt(str));
                    }
                }
            }else{
                System.out.println("taxonids--------"+taxonids);
                traitlist.add(Integer.parseInt(taxonids));
            }

            System.out.println(taxonids);
        }

        Map param = new HashMap();
        if(talist.size()>0){
            param.put("taxonList",talist);
        }

        if(traitlist.size()>0){
            param.put("traitList",traitlist);
        }

        Page<GeneBasicInfo> pageInfo = orthologGeneService.filterHomologGene(pageNo,length,param);
        if(pageInfo != null && pageInfo.size()>0){

            //here , select ortholog gene for this gene
            for(GeneBasicInfo geneBasicInfo : pageInfo){
                String geneid = geneBasicInfo.getHdbGeneId();
                int taxonId = geneBasicInfo.getTaxonId();
                int speciesType = geneBasicInfo.getSpeciesType();

                List<OrthoGeneBean> orthoGeneBeanList = orthologGeneService.getHomologGene(speciesType,geneid,taxonId);
                if(orthoGeneBeanList != null) {
                    geneBasicInfo.setOrthoGeneBeanList(orthoGeneBeanList);
                }

                //here, select trait count, GO count for this gene
                Map other = new HashMap();
                other.put("geneId",geneid);
                other.put("taxonId",taxonId);

                int traitcount =  orthologGeneService.selectTraitCountByGeneAndTaxon(other);
                geneBasicInfo.setTraitCount(traitcount);

            }



        }


        result = new DataTableResultInfo();
        result.setData(pageInfo);
        result.setDraw(draw);
        result.setLength(length);
        result.setRecordsTotal(pageInfo.getTotal());
        result.setRecordsFiltered(pageInfo.getTotal());
        return result;

    }

}
