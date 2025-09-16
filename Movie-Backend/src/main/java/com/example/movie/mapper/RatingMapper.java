package com.example.movie.mapper;

import com.example.movie.model.dto.RatingDto;
import com.example.movie.model.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "movieId", source = "movie.id")
    RatingDto toDto(Rating rating);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "movie", ignore = true)
    Rating toEntity(RatingDto dto);
}
