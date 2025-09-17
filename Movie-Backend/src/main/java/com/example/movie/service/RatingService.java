package com.example.movie.service;

import com.example.movie.model.dto.RatingDto;

import java.util.List;

/**
 * The interface Rating service.
 */
public interface RatingService {
    /**
     * Add rating dto rating dto.
     *
     * @param ratingDto the rating dto
     * @return the rating dto
     */
    RatingDto addRatingDto(RatingDto ratingDto);

    /**
     * Gets ratings by movie dto.
     *
     * @param movieId the movie id
     * @return the ratings by movie dto
     */
    List<RatingDto> getRatingsByMovieDto(Long movieId);

    /**
     * Gets ratings by user dto.
     *
     * @param userId the user id
     * @return the ratings by user dto
     */
    List<RatingDto> getRatingsByUserDto(Long userId);
}