package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

import java.util.List;

@Data
public class Variant {
    private Integer gsiId;

    private String geneId;

    private Integer varId;

    private String varName;

    private String hdbid;

    private String speciesCommonName;

    private List orthoList;

    private Integer taxonId;

    private String snpId;

    private String classification;

    private String dataSource;

}