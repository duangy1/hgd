package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.ExpressionTerm;
import cn.ac.cncb.ngdc.syndb.entity.GeneExpression;
import cn.ac.cncb.ngdc.syndb.mapper.ExpressionTermMapper;
import cn.ac.cncb.ngdc.syndb.mapper.GeneBasicInfoMapper;
import cn.ac.cncb.ngdc.syndb.mapper.GeneExpressionMapper;
import cn.ac.cncb.ngdc.syndb.mapper.GoBasicTermMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EoService {
    @Resource
    private GeneExpressionMapper geneExpressionMapper;
    @Resource
    private ExpressionTermMapper expressionTermMapper;
    @Resource
    private GeneBasicInfoMapper geneBasicInfoMapper;

    public Page<GeneExpression> initPageEo(String expName,String classification, int pageNo, int pageSize, String taxonid,String geneid,String hdbId) {

        List<String> hdbidList=new ArrayList();
        Page<GeneExpression> varList = new Page<>();
        if(geneid!=null && geneid.length() >0){
//            如果传入了geneid参数，则需要hdbidlist有值
            hdbidList=geneBasicInfoMapper.getHdbIdByOtherId(geneid);
            if(hdbidList.size()>0) {
                PageHelper.startPage(pageNo, pageSize, true); //line 1
                varList = geneExpressionMapper.initPageEo(expName, classification, taxonid, hdbidList,hdbId);
            }
        }else{
//            如果没传入geneid，直接查询
            PageHelper.startPage(pageNo, pageSize, true); //line 1
            varList = geneExpressionMapper.initPageEo(expName, classification, taxonid, hdbidList,hdbId);
        }
        return varList;


    }

    public List<ExpressionTerm> initPageEoTerms(){
        return expressionTermMapper.initPageEoTerms();
    }

    public List<GeneExpression> expressionGene(String hdbid){
        return geneExpressionMapper.expressionGene(hdbid);
    }

}
