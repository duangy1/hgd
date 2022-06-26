package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

@Data
public class ExpressionTerm {
    private Integer hdbEoId;

    private String geneId;

    private Integer taxonId;

    private String eoAnnotation;

    private String eoClassification;

    private float opacity;

    private Integer prjNum;

    private String[] prjList;

    private Integer genOrgid;

    private String classification;

}