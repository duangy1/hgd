package cn.ac.cncb.ngdc.syndb.service;

import cn.ac.cncb.ngdc.syndb.entity.variant;
import cn.ac.cncb.ngdc.syndb.mapper.VariantMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VariantService {
    @Resource
    private VariantMapper variantMapper;
    public Page<variant> initPageVariant(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize,true); //line 1
        return variantMapper.initPageVariant();
    }
}
