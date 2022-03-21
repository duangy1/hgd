package cn.ac.cncb.ngdc.syndb.bean;

import lombok.Data;

import java.util.List;

@Data
public class TreeSelectBean {
    private String id;
    private String label;
    private List<TreeSelectBasicBean> children;
}
