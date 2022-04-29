package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

@Data
public class SpeciesInfo {
    private int id;

    private String taxonId;

    private String commonName;

    private String shortName;

    private String latinName;

    private String classification;

    private String dataSource;

    private int gwasOrgid;

    private int speciesType;
}
