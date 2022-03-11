package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
public class Ortho9031 {
    private Integer id;

    private String protein1;

    private String ensemblId1;

    private String ensemblId2;

    private String refseqId1;

    private String geneId1;

    private Integer tax1;

    private String protein2;

    private String refseqId2;

    private String geneId2;

    private Integer tax2;

    private String fromdb;

    private String dbEvidence;

    private Date createTime;

    private Date lastUpdateTime;

    private List orthoTraitName;

    public String getEnsemblId1() {
        return ensemblId1;
    }
    public void setEnsemblId1(String ensemblId1){
        this.ensemblId1 = ensemblId1;
    }

    public String getEnsemblId2() {
        return ensemblId2;
    }
    public void setEnsemblId2(String ensemblId2){
        this.ensemblId2 = ensemblId2;
    }

    public void setOrthoTraitName(List orthoTraitName) {
        this.orthoTraitName = orthoTraitName;
    }
    public List getOrthoTraitName() {
        return orthoTraitName;
    }
    public String getFromdb() {
        return fromdb;
    }

    public void setDbEvidence(String db_evidence){this.dbEvidence= db_evidence;}

    public String getDbEvidence() {
        return dbEvidence;
    }

    public void setFromdb(String fromdb){this.fromdb=fromdb;}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProtein1() {
        return protein1;
    }

    public void setProtein1(String protein1) {
        this.protein1 = protein1 == null ? null : protein1.trim();
    }

    public String getRefseqId1() {
        return refseqId1;
    }

    public void setRefseqId1(String refseqId1) {
        this.refseqId1 = refseqId1 == null ? null : refseqId1.trim();
    }

    public String getGeneId1() {
        return geneId1;
    }

    public void setGeneId1(String geneId1) {
        this.geneId1 = geneId1 == null ? null : geneId1.trim();
    }

    public Integer getTax1() {
        return tax1;
    }

    public void setTax1(Integer tax1) {
        this.tax1 = tax1;
    }

    public String getProtein2() {
        return protein2;
    }

    public void setProtein2(String protein2) {
        this.protein2 = protein2 == null ? null : protein2.trim();
    }

    public String getRefseqId2() {
        return refseqId2;
    }

    public void setRefseqId2(String refseqId2) {
        this.refseqId2 = refseqId2 == null ? null : refseqId2.trim();
    }

    public String getGeneId2() {
        return geneId2;
    }

    public void setGeneId2(String geneId2) {
        this.geneId2 = geneId2 == null ? null : geneId2.trim();
    }

    public Integer getTax2() {
        return tax2;
    }

    public void setTax2(Integer tax2) {
        this.tax2 = tax2;
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