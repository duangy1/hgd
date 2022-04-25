package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

@Data
public class TraitName {
    private int traitId;

    private String traitName;

    private int traitType;

    private float opacity;

    private Integer gwasNum;

    private String[] gwasList;

}
