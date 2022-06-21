package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
@Data
public class Trait2gwas {
    private Integer id;

    private String geneId;

    private String hdbId;

    private Integer traitId;

    private String traitName;

    private String speciesCommonName;

    private Integer taxonId;

    private Integer entrezID;

    private String geneSymbol;

    private String classification;

    private Date createTime;

    private Date lastUpdateTime;

    private String gwasId;

    private List orthoList;

    private List orthoNameList;

    private Integer gwasOrgid;

    private List headerList;

}