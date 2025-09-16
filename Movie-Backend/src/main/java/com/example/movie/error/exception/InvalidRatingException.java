package com.example.movie.error.exception;

import com.example.movie.util.time.CurrentTimeStamp;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.sql.Timestamp;

@Value
@EqualsAndHashCode(callSuper = true)
public class InvalidRatingException extends RuntimeException {
    Integer code = 1005;
    String message = "Invalid rating";
    String description;
    Timestamp timestamp;

    public InvalidRatingException(String description) {
        this.description = description;
        this.timestamp = CurrentTimeStamp.timestamp();
    }
}