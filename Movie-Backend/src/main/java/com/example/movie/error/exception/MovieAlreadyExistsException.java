package com.example.movie.error.exception;


import com.example.movie.util.time.CurrentTimeStamp;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.sql.Timestamp;

@Value
@EqualsAndHashCode(callSuper = true)
public class MovieAlreadyExistsException extends RuntimeException {
    Integer code = 1001;
    String message = "Movie already exists";
    String description;
    Timestamp timestamp;

    public MovieAlreadyExistsException(String description) {
        this.description = description;
        this.timestamp = CurrentTimeStamp.timestamp();
    }
}
