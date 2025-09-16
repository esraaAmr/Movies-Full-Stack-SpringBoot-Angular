package com.example.movie.repository;

import com.example.movie.model.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    List<Rating> findByMovieId(Long movieId);

    List<Rating> findByUserId(Long userId);

    boolean existsByUserIdAndMovieId(Long userId, Long movieId);

}
