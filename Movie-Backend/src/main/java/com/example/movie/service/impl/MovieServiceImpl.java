package com.example.movie.service.impl;

import com.example.movie.error.exception.MovieAlreadyExistsException;
import com.example.movie.error.exception.MovieNotFoundException;
import com.example.movie.error.exception.OmdbServiceException;
import com.example.movie.mapper.MovieMapper;
import com.example.movie.model.dto.MovieDto;
import com.example.movie.model.dto.OmdbResponse;
import com.example.movie.model.entity.Movie;
import com.example.movie.repository.MovieRepository;
import com.example.movie.service.MovieService;
import com.example.movie.service.OmdbService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final OmdbService omdbService;
    private final MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, OmdbService omdbService, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.omdbService = omdbService;
        this.movieMapper = movieMapper;
    }

    @Override
    public MovieDto addMovieDto(MovieDto movieDto) {
        Movie movie = movieMapper.toEntity(movieDto);

        if (movie.getImdbId() != null && movieRepository.findByImdbId(movie.getImdbId()).isPresent()) {
            throw new MovieAlreadyExistsException("Movie with IMDb ID " + movie.getImdbId() + " already exists");
        }
        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toDto(savedMovie);
    }

    @Override
    public void removeMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new MovieNotFoundException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }

    @Override
    public List<MovieDto> getAllMoviesDto() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDto getMovieByIdDto(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
        return movieMapper.toDto(movie);
    }

    @Override
    public List<MovieDto> searchMoviesInDatabase(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDto importMovieFromOmdb(String imdbId) {
        OmdbResponse omdbResponse = omdbService.searchMovieByImdbId(imdbId);

        if (omdbResponse == null) {
            throw new OmdbServiceException("OMDb returned null for imdbId: " + imdbId);
        }
        if (!"True".equalsIgnoreCase(omdbResponse.getResponse())) {
            throw new OmdbServiceException("OMDb error for imdbId " + imdbId + ": " + omdbResponse.getError());
        }

        Movie movie = omdbService.convertOmdbResponseToMovie(omdbResponse);

        if (movie.getImdbId() == null) {
            throw new OmdbServiceException("OMDb response missing imdbID for imdbId: " + imdbId);
        }

        if (movieRepository.findByImdbId(movie.getImdbId()).isPresent()) {
            throw new MovieAlreadyExistsException("Movie with IMDb ID " + movie.getImdbId() + " already exists");
        }

        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toDto(savedMovie);
    }
}