package com.example.movie.service;

import com.example.movie.model.dto.MovieDto;

import java.util.List;

/**
 * The interface Movie service.
 */
public interface MovieService {
    /**
     * Add movie dto movie dto.
     *
     * @param movieDto the movie dto
     * @return the movie dto
     */
    MovieDto addMovieDto(MovieDto movieDto);

    /**
     * Remove movie.
     *
     * @param id the id
     */
    void removeMovie(Long id);

    /**
     * Gets all movies dto.
     *
     * @return the all movies dto
     */
    List<MovieDto> getAllMoviesDto();

    /**
     * Gets movie by id dto.
     *
     * @param id the id
     * @return the movie by id dto
     */
    MovieDto getMovieByIdDto(Long id);

    /**
     * Search movies in database list.
     *
     * @param title the title
     * @return the list
     */
    List<MovieDto> searchMoviesInDatabase(String title);

    /**
     * Import movie from omdb movie dto.
     *
     * @param imdbId the imdb id
     * @return the movie dto
     */
    MovieDto importMovieFromOmdb(String imdbId);
}