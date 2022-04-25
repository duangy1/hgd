package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

@Data
public class GoBasicTerm {
    private Integer hdbGoId;

    private String goAnnotations;

    private Integer depth;

    private String goId;

    private String goTerm;

    private String topClass;

    private String definition;
    private float opacity;

    private Integer goNum;

    private String[] goList;
}