package org.sofka.models;

@lombok.Data
public class Data {
    private Integer offset;
    private Integer limit;
    private Integer total;
    private Integer count;
    private Result[] results;
}
