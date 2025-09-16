package com.example.movie.error;

import com.example.movie.error.body.ErrorBody;
import com.example.movie.error.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler is a controller advice class that handles various exceptions
 * thrown within the movie application and converts them into standardized HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles MovieAlreadyExistsException by converting it to a well-defined error response.
     */
    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<ErrorBody> handleMovieAlreadyExistsException(MovieAlreadyExistsException ex) {
        ErrorBody errorBody = new ErrorBody(
                ex.getCode(),
                ex.getMessage(),
                ex.getDescription(),
                ex.getTimestamp()
        );
        return new ResponseEntity<>(errorBody, HttpStatus.CONFLICT);
    }

    /**
     * Handles MovieNotFoundException by converting it to a well-defined error response.
     */
    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorBody> handleMovieNotFoundException(MovieNotFoundException ex) {
        ErrorBody errorBody = new ErrorBody(
                ex.getCode(),
                ex.getMessage(),
                ex.getDescription(),
                ex.getTimestamp()
        );
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles UserNotFoundException by converting it to a well-defined error response.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorBody> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorBody errorBody = new ErrorBody(
                ex.getCode(),
                ex.getMessage(),
                ex.getDescription(),
                ex.getTimestamp()
        );
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles OmdbServiceException by converting it to a well-defined error response.
     */
    @ExceptionHandler(OmdbServiceException.class)
    public ResponseEntity<ErrorBody> handleOmdbServiceException(OmdbServiceException ex) {
        ErrorBody errorBody = new ErrorBody(
                ex.getCode(),
                ex.getMessage(),
                ex.getDescription(),
                ex.getTimestamp()
        );
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_GATEWAY);
    }

    /**
     * Handles InvalidRatingException by converting it to a well-defined error response.
     */
    @ExceptionHandler(InvalidRatingException.class)
    public ResponseEntity<ErrorBody> handleInvalidRatingException(InvalidRatingException ex) {
        ErrorBody errorBody = new ErrorBody(
                ex.getCode(),
                ex.getMessage(),
                ex.getDescription(),
                ex.getTimestamp()
        );
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general RuntimeException as a fallback.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorBody> handleRuntimeException(RuntimeException ex) {
        ErrorBody errorBody = new ErrorBody(
                5000,
                "Internal Server Error",
                ex.getMessage(),
                java.sql.Timestamp.from(java.time.Instant.now())
        );
        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles AuthenticationFailedException by converting it to a well-defined error response.
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorBody> handleAuthenticationFailedException(AuthenticationFailedException ex) {
        ErrorBody errorBody = new ErrorBody(
                ex.getCode(),
                ex.getMessage(),
                ex.getDescription(),
                ex.getTimestamp()
        );
        return new ResponseEntity<>(errorBody, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles OmdbMovieNotFoundException by converting it to a well-defined error response.
     */
    @ExceptionHandler(OmdbMovieNotFoundException.class)
    public ResponseEntity<ErrorBody> handleOmdbMovieNotFoundException(OmdbMovieNotFoundException ex) {
        ErrorBody errorBody = new ErrorBody(
                ex.getCode(),
                ex.getMessage(),
                ex.getDescription(),
                ex.getTimestamp()
        );
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles DuplicateRatingException by converting it to a well-defined error response.
     */
    @ExceptionHandler(DuplicateRatingException.class)
    public ResponseEntity<ErrorBody> handleDuplicateRatingException(DuplicateRatingException ex) {
        ErrorBody errorBody = new ErrorBody(
                ex.getCode(),
                ex.getMessage(),
                ex.getDescription(),
                ex.getTimestamp()
        );
        return new ResponseEntity<>(errorBody, HttpStatus.CONFLICT);
    }
}