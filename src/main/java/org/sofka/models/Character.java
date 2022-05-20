package org.sofka.models;

import lombok.Data;

@Data
public class Character {
    private String code;
    private String status;
    private String copyright;
    private String attributionText;
    private String attributionHTML;
    private String etag;
    private org.sofka.models.Data data;
    private String message;
}
