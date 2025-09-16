package com.example.movie.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OmdbSearchResult {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("imdbID")
    private String imdbID;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Poster")
    private String poster;
}
