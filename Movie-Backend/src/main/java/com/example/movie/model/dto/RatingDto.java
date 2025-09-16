package com.example.movie.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private Long userId;
    private Long movieId;
    private BigDecimal rating;
}
