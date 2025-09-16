package com.example.movie.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class OmdbSearchResponse {
    @JsonProperty("Search")
    private List<OmdbSearchResult> search;

    @JsonProperty("totalResults")
    private String totalResults;

    @JsonProperty("Response")
    private String response;

    @JsonProperty("Error")
    private String error;
}