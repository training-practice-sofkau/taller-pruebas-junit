package org.sofka.models;

import lombok.Data;

@Data
public class Series {
    private Integer available;
    private Integer returned;
    private String collectionURI;
    private Item[] items;
}
