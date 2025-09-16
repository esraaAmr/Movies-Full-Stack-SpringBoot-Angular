package com.example.movie.error.exception;

import com.example.movie.util.time.CurrentTimeStamp;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.sql.Timestamp;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserNotFoundException extends RuntimeException {
    Integer code = 1003;
    String message = "User not found";
    String description;
    Timestamp timestamp;

    public UserNotFoundException(String description) {
        this.description = description;
        this.timestamp = CurrentTimeStamp.timestamp();
    }
}