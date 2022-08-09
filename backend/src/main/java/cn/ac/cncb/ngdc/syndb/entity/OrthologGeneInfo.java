package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

import java.util.Date;
@Data
public class OrthologGeneInfo {
    private Integer oid;

    private Integer taxId1;

    private String uniprotId1;

    private String ensemblId1;

    private String ensemblId;

    private String refseqId1;

    private Integer taxId2;

    private String uniprotId2;

    private String ensemblId2;

    private String refseqId2;

    private String fromDb;

    private String dbEvidence;

    private Date createTime;

    private Date lastUpdateTime;

    private String hdbGeneId1;

    private String entrezId1;

    private String hdbGeneId2;

    private String entrezId2;

    private String geneSymbol1;
    private String geneSymbol2;

    private String latinName1;
    private String latinName2;

    private String commonName1;
    private String commonName2;

    private int orthoPosition;


}