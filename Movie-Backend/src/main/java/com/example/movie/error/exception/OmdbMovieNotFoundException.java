package com.example.movie.error.exception;

import com.example.movie.util.time.CurrentTimeStamp;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.sql.Timestamp;

@Value
@EqualsAndHashCode(callSuper = true)
public class OmdbMovieNotFoundException extends RuntimeException {
    Integer code = 1008;
    String message = "Movie not found in OMDb";
    String description;
    Timestamp timestamp;

    public OmdbMovieNotFoundException(String description) {
        this.description = description;
        this.timestamp = CurrentTimeStamp.timestamp();
    }
}