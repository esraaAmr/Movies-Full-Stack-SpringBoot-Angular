package com.example.movie.error.exception;

import com.example.movie.util.time.CurrentTimeStamp;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.sql.Timestamp;

@Value
@EqualsAndHashCode(callSuper = true)
public class OmdbServiceException extends RuntimeException {
    Integer code = 1004;
    String message = "OMDb service error";
    String description;
    Timestamp timestamp;

    public OmdbServiceException(String description) {
        this.description = description;
        this.timestamp = CurrentTimeStamp.timestamp();
    }
}