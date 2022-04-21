package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

@Data
public class variant {
    private Integer gsiId;

    private String geneId;

    private Integer varId;

    private String varName;

    private String speciesCommonName;

    private Integer taxonId;

    private String snpId;

}