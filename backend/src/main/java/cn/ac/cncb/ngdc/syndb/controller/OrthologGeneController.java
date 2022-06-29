package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.bean.OrthoGeneBean;
import cn.ac.cncb.ngdc.syndb.entity.DataTableResultInfo;
import cn.ac.cncb.ngdc.syndb.entity.GeneBasicInfo;
import cn.ac.cncb.ngdc.syndb.entity.Ortho9031;
import cn.ac.cncb.ngdc.syndb.entity.SpeciesInfo;
import cn.ac.cncb.ngdc.syndb.service.OrthologGeneService;
import cn.ac.cncb.ngdc.syndb.service.SpeciesService;
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

    @Autowired
    private SpeciesService speciesService;





    @RequestMapping(value="filterHomolog", method= RequestMethod.GET)
    @ResponseBody
    public DataTableResultInfo browseOrthologGene(int type,String keyword,String taxonids, String traitids,String goids,String variantids,String expids,String orthtaxids, HttpServletRequest request,@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
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
            }
            else {
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
            }
            else{
                System.out.println("traitids --------"+traitids);
                traitlist.add(Integer.parseInt(traitids));
            }

            System.out.println(traitids);
        }

        ArrayList variantlist = new ArrayList();
        if(variantids != null && variantids.length()>0){
            if(variantids.indexOf(",")>-1){
                String [] taxons = variantids.split("\\,");
                if(taxons != null && taxons.length>0){
                    for(String str : taxons){
                        variantlist.add(str);
                    }
                }
            }
            else{
                System.out.println("variantlist --------"+variantids);
                variantlist.add(variantids);
            }

            System.out.println(variantids);
        }

        ArrayList explist = new ArrayList();
        if(expids != null && expids.length()>0){
            if(expids.indexOf(",")>-1){
                String [] taxons = expids.split("\\,");
                if(taxons != null && taxons.length>0){
                    for(String str : taxons){
                        explist.add(str);
                    }
                }
            }
            else{
                System.out.println("expids --------"+expids);
                explist.add(expids);
            }

            System.out.println(variantids);
        }

        ArrayList golist = new ArrayList();
        if(goids != null && goids.length()>0){
            if(goids.indexOf(",")>-1){
                String [] taxons = goids.split("\\,");
                if(taxons != null && taxons.length>0){
                    for(String str : taxons){
                        golist.add(str);
                    }
                }
            }
            else{
                System.out.println("goids --------"+goids);
                golist.add(goids);
            }

            System.out.println(goids);
        }


        List orthtaxonlist = new ArrayList();
        if(orthtaxids != null && orthtaxids.length() >0 ){
            if(orthtaxids.indexOf(",")>-1){
                String [] taxons = orthtaxids.split("\\,");
                if(taxons != null && taxons.length>0){
                    for(String str : taxons){
                        orthtaxonlist.add(str);
                    }
                }
            }
            else{
                orthtaxonlist.add(orthtaxids);
            }
            System.out.println("orthtaxids --------"+orthtaxids);
        }


        Map param = new HashMap();
        if(talist.size()>0){
            param.put("taxonList",talist);
        }

        if(traitlist.size()>0){
            param.put("traitList",traitlist);
        }

        if(variantlist.size() > 0 ){
            param.put("variantlist",variantlist);
        }

        if(explist.size() >0 ){
            param.put("explist",explist);
        }

        if(golist.size() >0 ){
            param.put("golist",golist);
        }
        System.out.println("=====type======="+type);
        if(type>0){
            if(type==1){
                if(keyword != null && keyword.length() >0 ) {
                    param.put("species", keyword);
                }
            }else if(type==2){//gene symbol
                if(keyword != null && keyword.length() >0 ) {
                    param.put("symbol", keyword);
                }
            }else if(type==9){//gene symbol
                if(keyword != null && keyword.length() >0 ) {
                    param.put("commonname", keyword);
                }
            }

            else if(type==3){
                if(keyword != null && keyword.length() >0 ) {
                    param.put("ensgene", keyword);
                }
            }else if(type==4){
                if(keyword != null && keyword.length() >0 ) {
                    param.put("enspro", keyword);
                }
            }else if(type==5){
                if(keyword != null && keyword.length() >0 ) {
                    param.put("entrez", keyword);
                }
            }else if(type==6){
                if(keyword != null && keyword.length() >0 ) {
                    param.put("uniprot", keyword);
                }
            }else if(type==7){
                if(keyword != null && keyword.length() >0 ) {
                    param.put("biotype", keyword);
                }
            }else if(type==8){
                if(keyword != null && keyword.length() >0 ) {
                    param.put("allfield", keyword);
                }
            }else if(type==10){
                if(keyword != null && keyword.length() >0 ) {
                    param.put("commonandlatin", keyword);
                }
            }


        }

        if(orthtaxonlist!= null && orthtaxonlist.size() >0 ){
            param.put("orthtaxonlist",orthtaxonlist);
        }

        Page<GeneBasicInfo> pageInfo = orthologGeneService.filterHomologGene(pageNo,length,param);
        if(pageInfo != null && pageInfo.size()>0){

            //here , select ortholog gene for this gene
            for(GeneBasicInfo geneBasicInfo : pageInfo){
                String geneid = geneBasicInfo.getHdbGeneId();
                int taxonId = geneBasicInfo.getTaxonId();
                int speciesType = geneBasicInfo.getSpeciesType();

                String gene="";
                if(geneBasicInfo.getGeneSymbol() != null && geneBasicInfo.getGeneSymbol().length()>0){
                    gene = geneBasicInfo.getGeneSymbol();
                    geneBasicInfo.setShowGeneType(1);
                }else if(geneBasicInfo.getEnsemblGeneId() != null && geneBasicInfo.getEnsemblGeneId().length() >0 ){
                    gene = geneBasicInfo.getEnsemblGeneId();
                    geneBasicInfo.setShowGeneType(2);
                }else if(geneBasicInfo.getEntrezGene() != null && geneBasicInfo.getEntrezGene().length()>0){
                    gene = geneBasicInfo.getEntrezGene();
                    geneBasicInfo.setShowGeneType(3);
                }else {
                    gene = geneBasicInfo.getHdbGeneId();
                    geneBasicInfo.setShowGeneType(4);
                }

                List orthoGeneBeanList = orthologGeneService.getHomologGene(speciesType,geneid,gene,taxonId,orthtaxonlist);
                if(orthoGeneBeanList != null) {
                    geneBasicInfo.setOrthoGeneBeanList(orthoGeneBeanList);
                }

                //here, select trait count, GO count for this gene
                Map other = new HashMap();
                String gwasgeneid = geneBasicInfo.getHdbGeneId();
                other.put("geneId",gwasgeneid);
                other.put("taxonId",taxonId);

                int traitcount =  orthologGeneService.selectTraitCountByGeneAndTaxon(other);
                geneBasicInfo.setTraitCount(traitcount);

                //here, select expression count
                int expcount = orthologGeneService.selectExpCountByGeneAndTaxon(other);
                geneBasicInfo.setExpCount(expcount);
                //
                int varcount = orthologGeneService.selectVarCountByGeneAndTaxon(other);
                geneBasicInfo.setVarCount(varcount);

                //
                int goCount = orthologGeneService.selectGOCountByGeneAndTaxon(other);
                geneBasicInfo.setGoCount(goCount);



                geneBasicInfo.setShowGeneName(gene);

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
