package com.example.movie.error.body;


import java.sql.Timestamp;

/**
 * Represents the body of an error response in the movie application.
 * This record holds information such as the error code, message, detailed description,
 * and the timestamp when the error occurred.
 *
 * @param code the status code representing the error type
 * @param message a brief message describing the error
 * @param detail a detailed description of the error
 * @param timestamp the time when the error occurred
 */
public record ErrorBody(Integer code, String message, String detail, Timestamp timestamp) {
}
