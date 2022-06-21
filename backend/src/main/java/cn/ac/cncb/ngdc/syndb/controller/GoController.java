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
    public DataTableResultInfo initPageGo(String topGoid, String classification,String taxonid,
                                               @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
                                               @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
                                               @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
//        if(pageNo == 1){
//            pageNo = start/length+1;
//        }
        String tableName="gene_go_"+classification;
        System.out.print("params:"+topGoid+","+classification+","+taxonid);
        Page<GeneGo> pageInfo= goService.initPageGo(topGoid,tableName,pageNo,length,taxonid);
        System.out.print("pageInfo:"+pageInfo);
        for(GeneGo godata : pageInfo){
            int taxon = godata.getTaxonId();
            String hdbId=godata.getHdbId();
            List<OrthoGo> ortholist =orthoService.selectGeneGoOrthoInfo(hdbId,""+taxon,tableName);
            godata.setOrtholist(ortholist);
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
}
