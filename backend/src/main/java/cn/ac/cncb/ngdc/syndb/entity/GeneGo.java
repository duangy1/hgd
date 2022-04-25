package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

@Data
public class GeneGo {
    private Integer id;

    private String geneId;

    private String topGoId;

    private Integer goSumNum;

    private String speciesCommonName;

    private Integer taxonId;

    private String goId;

}