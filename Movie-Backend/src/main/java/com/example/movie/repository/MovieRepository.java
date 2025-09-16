package com.example.movie.repository;

import com.example.movie.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie> findByTitleContainingIgnoreCase(String title);

    Optional<Movie> findByImdbId(String imdbId);

}
