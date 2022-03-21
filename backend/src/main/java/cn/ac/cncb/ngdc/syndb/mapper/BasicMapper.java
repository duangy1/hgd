package cn.ac.cncb.ngdc.syndb.mapper;

import java.util.List;

public interface BasicMapper {
    List<Object> selectList(Object param,String mapperName);
}
