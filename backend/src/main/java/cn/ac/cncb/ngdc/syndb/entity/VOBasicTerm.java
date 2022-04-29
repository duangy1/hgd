package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

@Data
public class VOBasicTerm {
    private Integer hdbVoId;

    private String voAnnotaion;

    private String voClassification;

    private float opacity;

    private Integer snpNum;

    private String[] snpList;

    private String species;

    private String dataSource;
}