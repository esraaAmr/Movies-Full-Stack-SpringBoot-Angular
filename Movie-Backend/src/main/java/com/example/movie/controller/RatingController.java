package com.example.movie.controller;

import com.example.movie.model.dto.RatingDto;
import com.example.movie.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "Ratings", description = "Endpoints for managing ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Operation(summary = "Add a rating")
    @PostMapping
    public ResponseEntity<RatingDto> addRating(@RequestBody RatingDto ratingDto) {
        return ResponseEntity.ok(ratingService.addRatingDto(ratingDto));
    }

    @Operation(summary = "Get all ratings for a movie")
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<RatingDto>> getRatingsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(ratingService.getRatingsByMovieDto(movieId));
    }

    @Operation(summary = "Get all ratings by a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RatingDto>> getRatingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ratingService.getRatingsByUserDto(userId));
    }
}
