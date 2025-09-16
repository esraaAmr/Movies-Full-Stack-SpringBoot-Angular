package com.example.movie.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "title", "year", "imdbId", "poster" })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private Long id;
    private String title;
    private String year;
    private String imdbId;
    private String poster;
}

