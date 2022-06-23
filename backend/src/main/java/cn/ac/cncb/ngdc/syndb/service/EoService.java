package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.ExpressionTerm;
import cn.ac.cncb.ngdc.syndb.entity.GeneExpression;
import cn.ac.cncb.ngdc.syndb.mapper.ExpressionTermMapper;
import cn.ac.cncb.ngdc.syndb.mapper.GeneExpressionMapper;
import cn.ac.cncb.ngdc.syndb.mapper.GoBasicTermMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EoService {
    @Resource
    private GeneExpressionMapper geneExpressionMapper;
    @Resource
    private ExpressionTermMapper expressionTermMapper;

    public Page<GeneExpression> initPageEo(String expName,String classification, int pageNo, int pageSize, String taxonid) {

        PageHelper.startPage(pageNo, pageSize,true); //line 1
        Page<GeneExpression> resultList=geneExpressionMapper.initPageEo(expName,classification,taxonid);
        return resultList;
    }

    public List<ExpressionTerm> initPageEoTerms(){
        return expressionTermMapper.initPageEoTerms();
    }

    public List<GeneExpression> expressionGene(String hdbid){
        return geneExpressionMapper.expressionGene(hdbid);
    }

}
