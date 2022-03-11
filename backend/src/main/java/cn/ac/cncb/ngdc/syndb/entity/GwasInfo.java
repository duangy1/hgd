package cn.ac.cncb.ngdc.syndb.entity;

import java.util.Date;

public class GwasInfo {
    private Integer id;

    private String varid;

    private String trait;

    private String species;

    private String referenceVersion;

    private String varPosition;

    private String pvalue;

    private String rvalue;

    private String mapGene;

    private String classification;

    private String topTrait;

    private Date createTime;

    private Date lastUpdateTime;

    private String reportGene;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVarid() {
        return varid;
    }

    public void setVarid(String varid) {
        this.varid = varid == null ? null : varid.trim();
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait == null ? null : trait.trim();
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species == null ? null : species.trim();
    }

    public String getReferenceVersion() {
        return referenceVersion;
    }

    public void setReferenceVersion(String referenceVersion) {
        this.referenceVersion = referenceVersion == null ? null : referenceVersion.trim();
    }

    public String getVarPosition() {
        return varPosition;
    }

    public void setVarPosition(String varPosition) {
        this.varPosition = varPosition == null ? null : varPosition.trim();
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue == null ? null : pvalue.trim();
    }

    public String getRvalue() {
        return rvalue;
    }

    public void setRvalue(String rvalue) {
        this.rvalue = rvalue == null ? null : rvalue.trim();
    }

    public String getMapGene() {
        return mapGene;
    }

    public void setMapGene(String mapGene) {
        this.mapGene = mapGene == null ? null : mapGene.trim();
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification == null ? null : classification.trim();
    }

    public String getTopTrait() {
        return topTrait;
    }

    public void setTopTrait(String topTrait) {
        this.topTrait = topTrait == null ? null : topTrait.trim();
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

    public String getReportGene() {
        return reportGene;
    }

    public void setReportGene(String reportGene) {
        this.reportGene = reportGene == null ? null : reportGene.trim();
    }
}