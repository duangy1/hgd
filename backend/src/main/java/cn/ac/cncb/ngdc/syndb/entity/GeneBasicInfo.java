package cn.ac.cncb.ngdc.syndb.entity;

import cn.ac.cncb.ngdc.syndb.bean.OrthoGeneBean;
import lombok.Data;

import java.util.List;

@Data
public class GeneBasicInfo {
    private Integer gbiId;

    private String ensemblGeneId;

    private String refseqId;

    private String geneSynonym;

    private String geneType;

    private String geneSymbol;

    private String entrezGene;

    private String chromosom;

    private Integer geneStart;

    private Integer geneEnd;

    private String geneSource;

    private String gwasGeneId;

    private String jgiGeneId;

    private String geneDescription;

    private String hdbGeneId;

    private int taxonId;

    private int isHomolog;

    private int speciesType;

    private int traitCount;

    private int GoCount;

    private String taxonName;

    private List<OrthoGeneBean> orthoGeneBeanList;

    public Integer getGbiId() {
        return gbiId;
    }

    public void setGbiId(Integer gbiId) {
        this.gbiId = gbiId;
    }

    public String getEnsemblGeneId() {
        return ensemblGeneId;
    }

    public void setEnsemblGeneId(String ensemblGeneId) {
        this.ensemblGeneId = ensemblGeneId == null ? null : ensemblGeneId.trim();
    }

    public String getGeneSynonym() {
        return geneSynonym;
    }

    public void setGeneSynonym(String geneSynonym) {
        this.geneSynonym = geneSynonym == null ? null : geneSynonym.trim();
    }

    public String getGeneType() {
        return geneType;
    }

    public void setGeneType(String geneType) {
        this.geneType = geneType == null ? null : geneType.trim();
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol == null ? null : geneSymbol.trim();
    }

    public String getEntrezGene() {
        return entrezGene;
    }

    public void setEntrezGene(String entrezGene) {
        this.entrezGene = entrezGene == null ? null : entrezGene.trim();
    }

    public String getChromosom() {
        return chromosom;
    }

    public void setChromosom(String chromosom) {
        this.chromosom = chromosom == null ? null : chromosom.trim();
    }

    public Integer getGeneStart() {
        return geneStart;
    }

    public void setGeneStart(Integer geneStart) {
        this.geneStart = geneStart;
    }

    public Integer getGeneEnd() {
        return geneEnd;
    }

    public void setGeneEnd(Integer geneEnd) {
        this.geneEnd = geneEnd;
    }

    public String getGeneSource() {
        return geneSource;
    }

    public void setGeneSource(String geneSource) {
        this.geneSource = geneSource == null ? null : geneSource.trim();
    }

    public String getGwasGeneId() {
        return gwasGeneId;
    }

    public void setGwasGeneId(String gwasGeneId) {
        this.gwasGeneId = gwasGeneId == null ? null : gwasGeneId.trim();
    }

    public String getJgiGeneId() {
        return jgiGeneId;
    }

    public void setJgiGeneId(String jgiGeneId) {
        this.jgiGeneId = jgiGeneId == null ? null : jgiGeneId.trim();
    }

    public String getGeneDescription() {
        return geneDescription;
    }

    public void setGeneDescription(String geneDescription) {
        this.geneDescription = geneDescription == null ? null : geneDescription.trim();
    }
}