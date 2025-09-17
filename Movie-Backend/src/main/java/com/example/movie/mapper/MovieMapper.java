package com.example.movie.mapper;

import com.example.movie.model.dto.MovieDto;
import com.example.movie.model.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The interface Movie mapper.
 */
@Mapper(componentModel = "spring")
public interface MovieMapper {
    /**
     * To dto movie dto.
     *
     * @param movie the movie
     * @return the movie dto
     */
    @Mapping(target = "poster", source = "poster")
    @Mapping(target = "id", source = "id")
    MovieDto toDto(Movie movie);

    /**
     * To entity movie.
     *
     * @param dto the dto
     * @return the movie
     */
    @Mapping(target = "poster", source = "poster")
    @Mapping(target = "id", source = "id")
    Movie toEntity(MovieDto dto);
}
