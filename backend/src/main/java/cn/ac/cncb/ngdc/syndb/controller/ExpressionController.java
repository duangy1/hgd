package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.entity.*;
import cn.ac.cncb.ngdc.syndb.service.EoService;
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
public class ExpressionController {

    @Autowired
    EoService eoService;
    @Autowired
    OrthoService orthoService;

    @RequestMapping(value = "/api/expression-data", method = RequestMethod.GET)
    @ResponseBody
    public DataTableResultInfo initPageEo(String expName, String classification, String taxonid,
                                          @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
                                          @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
                                          @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
//        if(pageNo == 1){
//            pageNo = start/length+1;
//        }
        System.out.print("params:"+expName+","+classification+","+taxonid);
        Page<GeneExpression> pageInfo= eoService.initPageEo(expName,classification,pageNo,length,taxonid);
        System.out.print("pageInfo:"+pageInfo);
        for(GeneExpression eodata : pageInfo){
            int taxon = eodata.getTaxonId();
            String hdbId=eodata.getHdbId();
            if(hdbId != null && !hdbId.trim().equals("")) {
                List<OrthoGo> ortholist = orthoService.selectGeneEoOrthoInfo(hdbId, "" + taxon, expName);
                eodata.setOrtholist(ortholist);
            }
        }
        DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
        dataTableResultInfo.setData(pageInfo);
        dataTableResultInfo.setDraw(draw);
        dataTableResultInfo.setLength(length);
        dataTableResultInfo.setRecordsTotal(pageInfo.getTotal());
        dataTableResultInfo.setRecordsFiltered(pageInfo.getTotal());

        return dataTableResultInfo;};

    @RequestMapping(value = "/api/expression-gene", method = RequestMethod.GET)
    @ResponseBody
    public List<GeneExpression> expressionGene(String hdbId){
        return eoService.expressionGene(hdbId);
    }
}
