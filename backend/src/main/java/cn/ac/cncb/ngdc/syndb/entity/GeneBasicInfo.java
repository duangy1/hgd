package cn.ac.cncb.ngdc.syndb.entity;

import cn.ac.cncb.ngdc.syndb.bean.OrthoGeneBean;
import lombok.Data;

import java.util.List;

@Data
public class GeneBasicInfo {
    private Integer gbiId;

    private String geneid;

    private String ensemblGeneId;

    private String ensemblProteinId;

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

    private SpeciesInfo speciesName;

    private int isHomolog;

    private int speciesType;

    private int traitCount;

    private int GoCount;

    private String taxonName;

    private List orthoGeneBeanList;

    private String showGeneName;

    private int showGeneType;

}