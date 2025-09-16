package com.example.movie.error.exception;

import com.example.movie.util.time.CurrentTimeStamp;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.sql.Timestamp;

@Value
@EqualsAndHashCode(callSuper = true)
public class MovieNotFoundException extends RuntimeException {
    Integer code = 1002;
    String message = "Movie not found";
    String description;
    Timestamp timestamp;

    public MovieNotFoundException(String description) {
        this.description = description;
        this.timestamp = CurrentTimeStamp.timestamp();
    }
}
