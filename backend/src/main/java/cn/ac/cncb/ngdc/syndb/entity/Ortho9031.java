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

    private String entrezId1;

    private String refseqId1;

    private String geneId1;

    private String geneSymbol;

    private String tax1;

    private SpeciesInfo species1;

    private String hdbId2;

    private String protein2;

    private String ensemblId2;

    private String entrezId2;

    private String refseqId2;

    private String geneId2;

    private String tax2;

    private SpeciesInfo species2;

    private String protein;

    private String ensemblId;

    private String entrezId;

    private String geneId;

    private String tax;

    private SpeciesInfo species;

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