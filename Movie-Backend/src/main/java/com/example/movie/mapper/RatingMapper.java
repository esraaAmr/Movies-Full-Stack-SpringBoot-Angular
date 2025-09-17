package com.example.movie.mapper;

import com.example.movie.model.dto.RatingDto;
import com.example.movie.model.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The interface Rating mapper.
 */
@Mapper(componentModel = "spring")
public interface RatingMapper {

    /**
     * To dto rating dto.
     *
     * @param rating the rating
     * @return the rating dto
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "movieId", source = "movie.id")
    RatingDto toDto(Rating rating);

    /**
     * To entity rating.
     *
     * @param dto the dto
     * @return the rating
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "movie", ignore = true)
    Rating toEntity(RatingDto dto);
}
