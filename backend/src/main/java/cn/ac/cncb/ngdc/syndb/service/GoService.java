package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.GeneGo;
import cn.ac.cncb.ngdc.syndb.entity.GoBasicTerm;
import cn.ac.cncb.ngdc.syndb.mapper.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoService {

    @Resource
    private GeneGoMapper geneGoMapper;
    @Resource
    private GoBasicTermMapper goBasicTermMapper;
    @Resource
    private GeneBasicInfoMapper geneBasicInfoMapper;

    public Page<GeneGo> initPageGo(String topGoid,String classification, int pageNo, int pageSize, String taxonId,String geneid,String hdbId) {

//        PageHelper.startPage(pageNo, pageSize,true); //line 1
//        Page<GeneGo> resultList=geneGoMapper.initPageGo(topGoid,classification,hdbId,taxonId);
//        return resultList;

        List<String> hdbIdList=new ArrayList();
        Page<GeneGo> varList = new Page<>();
        if(geneid!=null && geneid.length() >0){
//            如果传入了geneid参数，则需要hdbidlist有值
            hdbIdList=geneBasicInfoMapper.getHdbIdByOtherId(geneid);
            if(hdbIdList.size()>0) {
                PageHelper.startPage(pageNo, pageSize, true); //line 1
                varList = geneGoMapper.initPageGo(topGoid, classification , hdbIdList,taxonId,hdbId);
            }
        }else{
//            如果没传入geneid，直接查询
            PageHelper.startPage(pageNo, pageSize, true); //line 1
            varList = geneGoMapper.initPageGo(topGoid, classification, hdbIdList, taxonId,hdbId);
        }
        return varList;
    }

    public List<GoBasicTerm> initPageGoTerms(){
        return goBasicTermMapper.initPageGoTerms();
    }

    public List<GoBasicTerm> getGoByHdbTaxon(String hdbid){
        return geneGoMapper.getGoByHdbTaxon(hdbid);
    }

}
