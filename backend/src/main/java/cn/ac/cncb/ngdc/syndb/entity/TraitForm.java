package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

import java.util.List;
@Data
public class TraitForm {

    private List type;

    private int orthoSpecies;

    private int querySpecies;

    private String traitName;

}
