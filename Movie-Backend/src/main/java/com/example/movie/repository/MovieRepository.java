package com.example.movie.repository;

import com.example.movie.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Movie repository.
 */
public interface MovieRepository extends JpaRepository<Movie,Long> {

    /**
     * Find by title containing ignore case list.
     *
     * @param title the title
     * @return the list
     */
    List<Movie> findByTitleContainingIgnoreCase(String title);

    /**
     * Find by imdb id optional.
     *
     * @param imdbId the imdb id
     * @return the optional
     */
    Optional<Movie> findByImdbId(String imdbId);

}
