package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

import java.util.List;

@Data
public class GeneGo {
    private Integer id;

    private String geneId;

    private String topGoId;

    private Integer goSumNum;

    private String speciesCommonName;

    private Integer taxonId;

    private List ortholist;

    private String goId;

    private String goName;

    private String hdbId;

    private String classification;

}