package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

import java.util.List;
@Data
public class ProductResultDTO {
    private List<Ortho9031> orthoList;

    private List<String> orthoNameList;

}
