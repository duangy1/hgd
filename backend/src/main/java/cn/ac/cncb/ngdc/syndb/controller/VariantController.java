package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.entity.DataTableResultInfo;
import cn.ac.cncb.ngdc.syndb.entity.variant;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import cn.ac.cncb.ngdc.syndb.service.VariantService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VariantController {
    @Autowired
    VariantService variantService;
    @RequestMapping(value = "/api/variants", method = RequestMethod.GET)
    @ResponseBody
    public DataTableResultInfo initPageVariant(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
    @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
    @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw,
    @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
    ){
//        if(pageNo == 1){
//            pageNo = start/length+1;
//        }
        Page<variant> pageInfo=variantService.initPageVariant(pageNo,length);

        DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
        dataTableResultInfo.setData(pageInfo);
        dataTableResultInfo.setDraw(draw);
        dataTableResultInfo.setLength(length);
        dataTableResultInfo.setRecordsTotal(pageInfo.getTotal());
        dataTableResultInfo.setRecordsFiltered(pageInfo.getTotal());

        return dataTableResultInfo;};

}
