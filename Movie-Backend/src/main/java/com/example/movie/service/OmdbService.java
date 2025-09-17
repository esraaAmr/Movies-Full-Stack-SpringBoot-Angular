package com.example.movie.service;

import com.example.movie.model.dto.OmdbResponse;
import com.example.movie.model.dto.OmdbSearchResult;
import com.example.movie.model.entity.Movie;

import java.util.List;

/**
 * The interface Omdb service.
 */
public interface OmdbService {
    /**
     * Search movie by imdb id omdb response.
     *
     * @param imdbId the imdb id
     * @return the omdb response
     */
    OmdbResponse searchMovieByImdbId(String imdbId);

    /**
     * Search movies list from omdb list.
     *
     * @param keyword the keyword
     * @return the list
     */
    List<OmdbSearchResult> searchMoviesListFromOmdb(String keyword);

    /**
     * Convert omdb response to movie movie.
     *
     * @param omdbResponse the omdb response
     * @return the movie
     */
    Movie convertOmdbResponseToMovie(OmdbResponse omdbResponse);
}