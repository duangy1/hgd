package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.GeneGo;
import cn.ac.cncb.ngdc.syndb.entity.GoBasicTerm;
import cn.ac.cncb.ngdc.syndb.mapper.GeneGoInfoMapper;
import cn.ac.cncb.ngdc.syndb.mapper.GeneGoMapper;
import cn.ac.cncb.ngdc.syndb.mapper.GoBasicTermMapper;
import cn.ac.cncb.ngdc.syndb.mapper.Trait2gwasMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoService {

    @Resource
    private GeneGoMapper geneGoMapper;
    @Resource
    private GoBasicTermMapper goBasicTermMapper;

    public Page<GeneGo> initPageGo(String topGoid, int pageNo, int pageSize, String taxonid) {

        PageHelper.startPage(pageNo, pageSize,true); //line 1
        Page<GeneGo> resultList=geneGoMapper.initPageGo(topGoid,taxonid);
        return resultList;
    }

    public List<GoBasicTerm> initPageGoTerms(){
        return goBasicTermMapper.initPageGoTerms();
    }

}
