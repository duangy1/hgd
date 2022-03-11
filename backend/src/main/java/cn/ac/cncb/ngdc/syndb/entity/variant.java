package cn.ac.cncb.ngdc.syndb.entity;

public class variant {
    private Integer gsiId;

    private String geneId;

    private Integer varId;

    private String varName;

    private String speciesCommonName;

    private Integer taxonId;

    private String snpId;

    public Integer getGsiId() {
        return gsiId;
    }

    public void setGsiId(Integer gsiId) {
        this.gsiId = gsiId;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId == null ? null : geneId.trim();
    }

    public Integer getVarId() {
        return varId;
    }

    public void setVarId(Integer varId) {
        this.varId = varId;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName == null ? null : varName.trim();
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

    public String getSnpId() {
        return snpId;
    }

    public void setSnpId(String snpId) {
        this.snpId = snpId == null ? null : snpId.trim();
    }
}