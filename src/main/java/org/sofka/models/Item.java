package org.sofka.models;

import lombok.Data;

@Data
public class Item {
    private String resourceURI;
    private String name;
    private String type;
}
