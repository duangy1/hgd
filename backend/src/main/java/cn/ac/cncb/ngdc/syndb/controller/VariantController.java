package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.entity.DataTableResultInfo;
import cn.ac.cncb.ngdc.syndb.entity.Ortho9031;
import cn.ac.cncb.ngdc.syndb.entity.ProductResultDTO;
import cn.ac.cncb.ngdc.syndb.entity.Variant;
import cn.ac.cncb.ngdc.syndb.mapper.GeneBasicInfoMapper;
import cn.ac.cncb.ngdc.syndb.service.GeneDetailService;
import cn.ac.cncb.ngdc.syndb.service.OrthoService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import cn.ac.cncb.ngdc.syndb.service.VariantService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VariantController {
    @Autowired
    VariantService variantService;
    @Autowired
    GeneDetailService geneDetailService;
    @Autowired
    OrthoService orthoService;
    @RequestMapping(value = "/api/variants", method = RequestMethod.GET)
    @ResponseBody
    public DataTableResultInfo initPageVariant(String varname,String classification,String speciesName,
    @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
    @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
    @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
        Page<Variant> pageInfo=variantService.initPageVariant(varname,classification,pageNo,length,speciesName);
        for(Variant variant : pageInfo){
            int taxon = variant.getTaxonId();
            String hdbId=variant.getHdbid();
            List<Ortho9031> ortholist =orthoService.selectGeneVarOrthoInfo(hdbId,""+taxon);
            variant.setOrthoList(ortholist);
        }
        DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
        dataTableResultInfo.setData(pageInfo);
        dataTableResultInfo.setDraw(draw);
        dataTableResultInfo.setLength(length);
        dataTableResultInfo.setRecordsTotal(pageInfo.getTotal());
        dataTableResultInfo.setRecordsFiltered(pageInfo.getTotal());

        return dataTableResultInfo;};

    @RequestMapping("/api/get-by-varName")
    @ResponseBody
    public DataTableResultInfo getInfoByTraitName(@RequestParam String varName,@RequestParam String species,
                                                  @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                                  @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
                                                  @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
                                                  @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
        if(pageNo == 1){
            pageNo = start/length+1;
        }
        DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
        Page<Variant> pageInfo = variantService.getInfoByVarNameAndSpecies(pageNo, length,varName,species);

        if(pageInfo != null ){
            for(Variant variant : pageInfo){
                int taxon = variant.getTaxonId();
                String geneid = variant.getGeneId(); // gwas gene id
//                List<Ortho9031> ortholist =orthoService.findOrthByTaxonAndGene(taxon,geneid,classification);
                String hdbId= geneDetailService.getHdbIdByEnsId(geneid);
                if(hdbId != null && !hdbId.trim().equals("")) {
                    ProductResultDTO resultList = orthoService.selectGeneTraitOrthoInfo(hdbId, "" + taxon);
                    List<Ortho9031> ortholist = resultList.getOrthoList();
                    variant.setOrthoList(ortholist);
                }
            }
        }

        dataTableResultInfo.setData(pageInfo);
        dataTableResultInfo.setDraw(draw);
        dataTableResultInfo.setLength(length);
        dataTableResultInfo.setRecordsTotal(pageInfo.getTotal());
        dataTableResultInfo.setRecordsFiltered(pageInfo.getTotal());
        return dataTableResultInfo;
    }

    //根据hdbid获取var接口所需的snpid
    @RequestMapping(value = "/api/var-snpid", method = RequestMethod.GET)
    @ResponseBody
    public String getGwasInfoByhdbid(String hdbId,String varName){
        List variantlist = new ArrayList();
        if(varName != null && varName.length()>0){
            if(varName.indexOf(",")>-1){
                String [] taxons = varName.split("\\,");
                if(taxons != null && taxons.length>0){
                    for(String str : taxons){
                        variantlist.add(str);
                    }
                }
            }
            else{
                System.out.println("variantlist --------"+varName);
                variantlist.add(varName);
            }

            System.out.println(varName);
        }
        String hdbGwas = variantService.getSnpidByhdbid(hdbId,variantlist);
        return  hdbGwas;
    }
}
