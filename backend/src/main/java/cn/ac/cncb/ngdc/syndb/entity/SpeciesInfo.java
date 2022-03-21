package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

@Data
public class SpeciesInfo {
    private int id;
    private String taxonId;
    private String commonName;
    private String latinName;
    private int gwasOrgid;
    private int speciesType;
}
