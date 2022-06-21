package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
public class Ortho9031 {
    private Integer id;

    private String hdbId1;

    private String protein1;

    private String ensemblId1;

    private String tax1;

    private SpeciesInfo species1;

    private String commonName1;

    private String dataSource1;

    private String classification1;

    private String traitName1;

    private String varName1;

    private String gwasId1;

    private String hdbId2;

    private String protein2;

    private String ensemblId2;

    private String tax2;

    private SpeciesInfo species2;

    private String commonName2;

    private String dataSource2;

    private String classification2;

    private String traitName2;

    private String varName2;

    private String gwasId2;

    private String protein;

    private String ensemblId;

    private String tax;

    private SpeciesInfo species;

    private String commonName;

    private String dataSource;

    private String classification;

    private String traitName;

    private String gwasId;

    private String varName;

    private String fromdb;

    private String dbEvidence;

    private String panther;

    private String treefam;

    private String oma;

    private String eggnog;

    private String hieranoid;

    private String inparanoid;

    private List dbevidenceList;

    private GeneBasicInfo gbiInfo;


    private Date createTime;

    private Date lastUpdateTime;

    private List orthoTraitName;

    private List orthoVarName;
}