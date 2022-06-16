package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

@Data
public class GeneExpression {
    private Integer geiId;

    private String geneId;

    private String annotation;

    private Integer taxonId;

    private String bioProjectId;

    private String[] prjList;

}