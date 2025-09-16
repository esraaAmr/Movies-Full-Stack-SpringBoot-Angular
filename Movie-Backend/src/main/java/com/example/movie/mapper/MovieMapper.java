package com.example.movie.mapper;

import com.example.movie.model.dto.MovieDto;
import com.example.movie.model.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    @Mapping(target = "poster", source = "poster")
    @Mapping(target = "id", source = "id")
    MovieDto toDto(Movie movie);

    @Mapping(target = "poster", source = "poster")
    @Mapping(target = "id", source = "id")
    Movie toEntity(MovieDto dto);
}
