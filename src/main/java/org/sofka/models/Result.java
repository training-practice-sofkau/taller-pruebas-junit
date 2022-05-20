package org.sofka.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Result {
    private Integer id;
    private String name;
    private String description;
    private Timestamp modified;
    private String resourceURI;
    private Url[] urls;
    private Thumbnail thumbnail;
    private Comics comics;
    private Stories stories;
    private Events events;
    private Series series;
}
