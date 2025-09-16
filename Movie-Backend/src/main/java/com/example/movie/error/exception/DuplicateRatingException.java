package com.example.movie.error.exception;

import com.example.movie.util.time.CurrentTimeStamp;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.sql.Timestamp;

@Value
@EqualsAndHashCode(callSuper = true)
public class DuplicateRatingException extends RuntimeException {
    Integer code = 1009;
    String message = "Duplicate rating not allowed";
    String description;
    Timestamp timestamp;

    public DuplicateRatingException(String description) {
        this.description = description;
        this.timestamp = CurrentTimeStamp.timestamp();
    }
}