package cn.ac.cncb.ngdc.syndb.entity;

import lombok.Data;

import java.util.List;

@Data
public class DataTableResultInfo {
    private List data;
    private int draw;//the NO.of requests
    private int length;
    private long recordsTotal;
    private long recordsFiltered;
}
