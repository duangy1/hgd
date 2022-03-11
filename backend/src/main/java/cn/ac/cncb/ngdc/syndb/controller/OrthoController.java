package cn.ac.cncb.ngdc.syndb.controller;

import cn.ac.cncb.ngdc.syndb.entity.DataTableResultInfo;
import cn.ac.cncb.ngdc.syndb.entity.GeneBasicInfo;
import cn.ac.cncb.ngdc.syndb.entity.Ortho9031;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import cn.ac.cncb.ngdc.syndb.service.OrthoService;
import org.springframework.web.bind.annotation.*;


@Controller
//@CrossOrigin(origins = {"http://localhost:8080", "null"})
public class OrthoController {
    @Autowired
    OrthoService orthoService;

    @RequestMapping(value = "/api/gene", method = RequestMethod.GET)
    @ResponseBody
    public DataTableResultInfo getAllOrthoList(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                            @RequestParam(value = "length", required = false, defaultValue = "10") Integer length,
                                            @RequestParam(value = "draw", required = false, defaultValue = "0") Integer draw
    ){
        int pageNo = start/length+1;

        Page<GeneBasicInfo> pageInfo = orthoService.allOrthoList(pageNo,length);

        DataTableResultInfo dataTableResultInfo = new DataTableResultInfo();
        dataTableResultInfo.setData(pageInfo);
        dataTableResultInfo.setDraw(draw);
        dataTableResultInfo.setLength(length);
        dataTableResultInfo.setRecordsTotal(pageInfo.getTotal());
        dataTableResultInfo.setRecordsFiltered(pageInfo.getTotal());
        return dataTableResultInfo;
    }
}
