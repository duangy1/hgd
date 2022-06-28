package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.entity.*;
import cn.ac.cncb.ngdc.syndb.service.GoService;
import cn.ac.cncb.ngdc.syndb.service.OrthoService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GoController {
    @Autowired
    OrthoService orthoService;
    @Autowired
    GoService goService;
    @RequestMapping(value = "/api/godata", method = RequestMethod.GET)
    @ResponseBody
    public DataTableResultInfo initPageGo(String topGoid, String classification,String taxonId,String hdbId,
                                               @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
                                               @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
                                               @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
//        if(pageNo == 1){
//            pageNo = start/length+1;
//        }
        System.out.print("params:"+hdbId+","+classification+","+taxonId);
        Page<GeneGo> pageInfo= goService.initPageGo(topGoid,classification,hdbId,pageNo,length,taxonId);
        for(GeneGo godata : pageInfo){
            int taxon = godata.getTaxonId();
            String hdbid=godata.getHdbId();
            if(hdbid != null && !hdbid.trim().equals("")) {
                List<OrthoGo> ortholist = orthoService.selectGeneGoOrthoInfo(hdbid, "" + taxon);
                godata.setOrtholist(ortholist);
            }
        }
        DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
        dataTableResultInfo.setData(pageInfo);
        dataTableResultInfo.setDraw(draw);
        dataTableResultInfo.setLength(length);
        dataTableResultInfo.setRecordsTotal(pageInfo.getTotal());
        dataTableResultInfo.setRecordsFiltered(pageInfo.getTotal());

        return dataTableResultInfo;};
    @RequestMapping(value = "/api/go-terms", method = RequestMethod.GET)
    @ResponseBody
    public List<GoBasicTerm> initPageGoTerms(){
        return goService.initPageGoTerms();
    }

    @RequestMapping(value = "/api/gene-go", method = RequestMethod.GET)
    @ResponseBody
    public List<GoBasicTerm> getGoByHdbTaxon(String hdbid){
        return goService.getGoByHdbTaxon(hdbid);
    }
}
