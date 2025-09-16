package com.example.movie.controller;

import com.example.movie.model.dto.MovieDto;
import com.example.movie.model.dto.OmdbSearchResult;
import com.example.movie.service.MovieService;
import com.example.movie.service.OmdbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movies", description = "Endpoints for managing movies")
public class MovieController {

    private final MovieService movieService;
    private final OmdbService omdbService;

    public MovieController(MovieService movieService, OmdbService omdbService) {
        this.movieService = movieService;
        this.omdbService = omdbService;
    }

    @Operation(summary = "Add a new movie manually")
    @PostMapping
    public ResponseEntity<MovieDto> addMovie(@RequestBody MovieDto createMovieDto) {
        MovieDto movieDto = MovieDto.builder()
                .title(createMovieDto.getTitle())
                .year(createMovieDto.getYear())
                .imdbId(createMovieDto.getImdbId())
                .poster(createMovieDto.getPoster())
                .build();
        return ResponseEntity.ok(movieService.addMovieDto(movieDto));
    }

    @Operation(summary = "Get all movies (from database)")
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMoviesDto());
    }

    @Operation(summary = "Get a movie by DB id")
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        MovieDto movieDto = movieService.getMovieByIdDto(id);
        return ResponseEntity.ok(movieDto);
    }

    @Operation(summary = "Search movies in the database by title")
    @GetMapping("/search")
    public ResponseEntity<List<MovieDto>> searchMovies(@RequestParam String title) {
        return ResponseEntity.ok(movieService.searchMoviesInDatabase(title));
    }

    @Operation(summary = "Search OMDb by keyword (returns a list)")
    @GetMapping("/omdb/search/list")
    public ResponseEntity<List<OmdbSearchResult>> searchMoviesListFromOmdb(@RequestParam String keyword) {
        return ResponseEntity.ok(omdbService.searchMoviesListFromOmdb(keyword));
    }

    @Operation(summary = "Import a single movie from OMDb using imdbId")
    @PostMapping("/omdb/import")
    public ResponseEntity<MovieDto> importMovieFromOmdb(@RequestParam String imdbId) {
        MovieDto movie = movieService.importMovieFromOmdb(imdbId);
        return ResponseEntity.ok(movie);
    }

    @Operation(summary = "Import multiple movies from OMDb using a list of imdbIds")
    @PostMapping("/omdb/import/list")
    public ResponseEntity<Map<String, Object>> importMoviesFromOmdb(@RequestBody List<String> imdbIds) {
        List<MovieDto> imported = new ArrayList<>();
        List<String> skipped = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (String id : imdbIds) {
            try {
                MovieDto saved = movieService.importMovieFromOmdb(id);
                imported.add(saved);
            } catch (IllegalStateException e) {
                skipped.add(id);
            } catch (RuntimeException e) {
                errors.add(id + " : " + e.getMessage());
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("imported", imported);
        result.put("skipped", skipped);
        result.put("errors", errors);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Delete a movie by DB id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMovie(@PathVariable Long id) {
        movieService.removeMovie(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Batch delete movies by DB ids")
    @DeleteMapping("/batch")
    public ResponseEntity<Void> removeMovies(@RequestBody List<Long> ids) {
        ids.forEach(movieService::removeMovie);
        return ResponseEntity.noContent().build();
    }
}
