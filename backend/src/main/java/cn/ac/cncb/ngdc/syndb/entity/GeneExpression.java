package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

import java.util.List;

@Data
public class GeneExpression {
    private Integer geiId;

    private String geneId;

    private String annotation;

    private Integer taxonId;

    private String bioProjectId;

    private String[] prjList;

    private List ortholist;

    private String hdbId;

    private int genOrgId;

    private String commonName;

    private String classification;

}