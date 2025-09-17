package com.example.movie.repository;

import com.example.movie.model.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Rating repository.
 */
public interface RatingRepository extends JpaRepository<Rating,Long> {

    /**
     * Find by movie id list.
     *
     * @param movieId the movie id
     * @return the list
     */
    List<Rating> findByMovieId(Long movieId);

    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Rating> findByUserId(Long userId);

    /**
     * Exists by user id and movie id boolean.
     *
     * @param userId  the user id
     * @param movieId the movie id
     * @return the boolean
     */
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);

}
