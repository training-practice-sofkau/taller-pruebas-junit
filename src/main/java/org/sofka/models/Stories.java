package org.sofka.models;

import lombok.Data;

@Data
public class Stories {
    private Integer available;
    private Integer returned;
    private String collectionURI;
    private Item[] items;
}
