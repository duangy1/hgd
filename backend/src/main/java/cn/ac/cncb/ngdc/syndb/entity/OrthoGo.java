package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

@Data
public class OrthoGo {

    private String hdbId1;

    private String protein1;

    private String ensemblId1;

    private String tax1;

    private SpeciesInfo species1;

    private String commonName1;

    private String classification1;

    private String goName1;

    private String eoName1;

    private String hdbId2;

    private String protein2;

    private String ensemblId2;

    private String tax2;

    private SpeciesInfo species2;

    private String commonName2;

    private String classification2;

    private String goName2;

    private String eoName2;

    private String protein;

    private String ensemblId;

    private String tax;

    private String hdbId;

    private SpeciesInfo species;

    private String commonName;

    private String classification;

    private GeneBasicInfo gbiInfo;

    private String goName;

    private String eoName;

    private String dbEvidence;

    private String fromdb;

    private String inparanoidID1;

    private String inparanoidID2;
}
