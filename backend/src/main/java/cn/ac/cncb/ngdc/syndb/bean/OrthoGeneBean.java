package cn.ac.cncb.ngdc.syndb.bean;

import lombok.Data;

@Data
public class OrthoGeneBean {
    private int ogId;
    private String geneName;
    private String taxonName;
    private String hdbGeneId;
    private int taxonId;
}
