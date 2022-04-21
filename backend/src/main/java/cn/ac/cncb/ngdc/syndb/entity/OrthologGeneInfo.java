package cn.ac.cncb.ngdc.syndb.entity;

import java.util.Date;

public class OrthologGeneInfo {
    private Integer oid;

    private Integer taxId1;

    private String uniprotId1;

    private String ensemblId1;

    private String refseqId1;

    private Integer taxId2;

    private String uniprotId2;

    private String ensembId2;

    private String refseqId2;

    private String fromDb;

    private String dbEvidence;

    private Date createTime;

    private Date lastUpdateTime;

    private String hdbGeneId1;

    private String entrezId1;

    private String hdbGeneId2;

    private String entrezId2;

    public String getHdbGeneId1() {
        return hdbGeneId1;
    }

    public void setHdbGeneId1(String hdbGeneId1) {
        this.hdbGeneId1 = hdbGeneId1 == null ? null : hdbGeneId1.trim();
    }

    public String getEntrezId1() {
        return entrezId1;
    }

    public void setEntrezId1(String entrezId1) {
        this.entrezId1 = entrezId1 == null ? null : entrezId1.trim();
    }

    public String getHdbGeneId2() {
        return hdbGeneId2;
    }

    public void setHdbGeneId2(String hdbGeneId2) {
        this.hdbGeneId2 = hdbGeneId2 == null ? null : hdbGeneId2.trim();
    }

    public String getEntrezId2() {
        return entrezId2;
    }

    public void setEntrezId2(String entrezId2) {
        this.entrezId2 = entrezId2 == null ? null : entrezId2.trim();
    }


    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getTaxId1() {
        return taxId1;
    }

    public void setTaxId1(Integer taxId1) {
        this.taxId1 = taxId1;
    }

    public String getUniprotId1() {
        return uniprotId1;
    }

    public void setUniprotId1(String uniprotId1) {
        this.uniprotId1 = uniprotId1 == null ? null : uniprotId1.trim();
    }

    public String getEnsemblId1() {
        return ensemblId1;
    }

    public void setEnsemblId1(String ensemblId1) {
        this.ensemblId1 = ensemblId1 == null ? null : ensemblId1.trim();
    }

    public String getRefseqId1() {
        return refseqId1;
    }

    public void setRefseqId1(String refseqId1) {
        this.refseqId1 = refseqId1 == null ? null : refseqId1.trim();
    }

    public Integer getTaxId2() {
        return taxId2;
    }

    public void setTaxId2(Integer taxId2) {
        this.taxId2 = taxId2;
    }

    public String getUniprotId2() {
        return uniprotId2;
    }

    public void setUniprotId2(String uniprotId2) {
        this.uniprotId2 = uniprotId2 == null ? null : uniprotId2.trim();
    }

    public String getEnsembId2() {
        return ensembId2;
    }

    public void setEnsembId2(String ensembId2) {
        this.ensembId2 = ensembId2 == null ? null : ensembId2.trim();
    }

    public String getRefseqId2() {
        return refseqId2;
    }

    public void setRefseqId2(String refseqId2) {
        this.refseqId2 = refseqId2 == null ? null : refseqId2.trim();
    }

    public String getFromDb() {
        return fromDb;
    }

    public void setFromDb(String fromDb) {
        this.fromDb = fromDb == null ? null : fromDb.trim();
    }

    public String getDbEvidence() {
        return dbEvidence;
    }

    public void setDbEvidence(String dbEvidence) {
        this.dbEvidence = dbEvidence == null ? null : dbEvidence.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}