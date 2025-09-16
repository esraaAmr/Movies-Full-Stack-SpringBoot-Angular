package com.example.movie.error.exception;

import com.example.movie.util.time.CurrentTimeStamp;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.sql.Timestamp;

@Value
@EqualsAndHashCode(callSuper = true)
public class AuthenticationFailedException extends RuntimeException {
    Integer code = 1006;
    String message = "Authentication failed";
    String description;
    Timestamp timestamp;

    public AuthenticationFailedException(String description) {
        this.description = description;
        this.timestamp = CurrentTimeStamp.timestamp();
    }
}