package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

@Data
public class GeneGoInfo {
    private Integer goId;

    private String geneId;

    private String geneName;

    private String geneSynonym;

    private String goTermAccession;

    private String goTermName;

    private String goEvidenceCode;

    private String goDomain;

    private String goslimGoaAccession;

    private String uniprotGeneName;

    private String uniprotGeneSymbol;

    private String hgncSymbol;

    private String goTermDefinition;

}