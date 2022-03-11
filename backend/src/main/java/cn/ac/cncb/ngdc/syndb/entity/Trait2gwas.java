package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class Trait2gwas {
    private Integer id;

    private String geneId;

    private Integer traitId;

    private String traitName;

    private String speciesCommonName;

    private Integer taxonId;

    private String classification;

    private Date createTime;

    private Date lastUpdateTime;

    private String gwasId;

    private List orthoList;

    private Integer gwasOrgid;

//    添加ortho相关属性


//    以上是ortho相关属性

    public Integer getGwasOrgid(){return gwasOrgid; }

    public void setGwasOrgid(Integer gwasOrgid){this.gwasOrgid = gwasOrgid;}

    public List getOrthoList() {
        return orthoList;
    }

    public void setOrthoList(List orthoList) {
        this.orthoList = orthoList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId == null ? null : geneId.trim();
    }

    public Integer getTraitId() {
        return traitId;
    }

    public void setTraitId(Integer traitId) {
        this.traitId = traitId;
    }

    public String getTraitName() {
        return traitName;
    }

    public void setTraitName(String traitName) {
        this.traitName = traitName == null ? null : traitName.trim();
    }

    public String getSpeciesCommonName() {
        return speciesCommonName;
    }

    public void setSpeciesCommonName(String speciesCommonName) {
        this.speciesCommonName = speciesCommonName == null ? null : speciesCommonName.trim();
    }

    public Integer getTaxonId() {
        return taxonId;
    }

    public void setTaxonId(Integer taxonId) {
        this.taxonId = taxonId;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification == null ? null : classification.trim();
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

    public String getGwasId() {
        return gwasId;
    }

    public void setGwasId(String gwasId) {
        this.gwasId = gwasId == null ? null : gwasId.trim();
    }
}