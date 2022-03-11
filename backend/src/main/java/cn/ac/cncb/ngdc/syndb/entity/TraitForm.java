package cn.ac.cncb.ngdc.syndb.entity;

import java.util.List;

public class TraitForm {

    private List type;

    private int orthoSpecies;

    private int querySpecies;

    private String traitName;

    public void setQuerySpecies(int querySpecies) {
        this.querySpecies = querySpecies;
    }
    public int getQuerySpecies(){
        return querySpecies;
    }

    public List getType() {
        return type;
    }

    public void setType(List type) {
        this.type = type;
    }

    public int getOrthoSpecies() {
        return orthoSpecies;
    }

    public void setOrthoSpecies(int orthoSpecies) {
        this.orthoSpecies = orthoSpecies;
    }

    public String getTraitName(){ return traitName; }

    public void setTraitName(){
        this.traitName = traitName == null ? null : traitName.trim();
    }

}
