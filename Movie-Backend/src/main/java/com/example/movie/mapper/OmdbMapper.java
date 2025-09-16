package com.example.movie.mapper;

import com.example.movie.model.dto.OmdbResponse;
import com.example.movie.model.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OmdbMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "year", source = "year")
    @Mapping(target = "imdbId", source = "imdbID")
    @Mapping(target = "poster", source = "poster")
    Movie toEntity(OmdbResponse omdbResponse);

    default Movie mapIfValid(OmdbResponse omdbResponse) {
        if (omdbResponse == null || !"True".equals(omdbResponse.getResponse())) {
            return null;
        }
        return toEntity(omdbResponse);
    }
}
